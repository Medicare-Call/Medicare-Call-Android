package com.konkuk.medicarecall.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.data.repository.EldersHealthInfoRepository
import com.konkuk.medicarecall.data.repository.EldersInfoRepository
import com.konkuk.medicarecall.ui.home.data.HomeRepository
import com.konkuk.medicarecall.ui.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


data class ElderInfo(val id: Int, val name: String,  val phone: String?)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val eldersInfoRepository: EldersInfoRepository,
    private val homeRepository: HomeRepository,
    private val savedStateHandle: SavedStateHandle,
    private val eldersHealthInfoRepository: EldersHealthInfoRepository
) : ViewModel() {

    var isLoading by mutableStateOf(true)

    fun callCareCallImmediate(
        onSuccess: () -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val res = homeRepository.requestImmediateCareCall()
                if (res.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure("요청 실패 (${res.code()})")
                }
            } catch (e: Exception) {
                onFailure(e.message ?: "알 수 없는 에러")
            }
        }
    }
    private companion object {
        const val TAG = "HomeViewModel"
        const val KEY_SELECTED_ELDER_ID = "selectedElderId"
    }

    // 홈 화면 상태
    private val _homeUiState = MutableStateFlow(HomeUiState.EMPTY)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    // 어르신 전체 목록
    private val _elderInfoList = MutableStateFlow<List<ElderInfo>>(emptyList())
    val elderInfoList: StateFlow<List<ElderInfo>> = _elderInfoList

    // 드롭다운에 표시할 어르신 이름 목록
    val elderNameList: StateFlow<List<String>> = _elderInfoList.mapState { list ->
        list.map { it.name }
    }

    // 현재 선택된 어르신 ID
    private val _selectedElderId = MutableStateFlow<Int?>(
        savedStateHandle.get<Int?>(KEY_SELECTED_ELDER_ID)
    )
    val selectedElderId: StateFlow<Int?> = _selectedElderId


    init {
        fetchElderList()

        // 선택된 ID가 바뀌면 해당 어르신의 홈 데이터를 불러옴 + 저장소에 저장
        viewModelScope.launch {
            _selectedElderId.collect { elderId ->
                if (elderId != null) {
                    savedStateHandle[KEY_SELECTED_ELDER_ID] = elderId
                    fetchHomeSummaryForToday(elderId)
                } else {
                    _homeUiState.value = HomeUiState.EMPTY
                }
            }
        }
    }

    // 서버에서 어르신 전체 목록을 불러옴
    private fun fetchElderList() {
        viewModelScope.launch {
            eldersInfoRepository.getElders()
                .onSuccess { elders ->

                    Log.d(TAG, "elders raw=$elders")

                    _elderInfoList.value = elders.map {
                        ElderInfo(id = it.elderId, name = it.name, phone = it.phone)
                    }
                    Log.d(TAG, "어르신 정보 로딩 성공: ${_elderInfoList.value}")

                    // 복구된 ID가 있으면 그걸 우선 적용하고, 없으면 첫 번째로 초기화
                    val restoredId = savedStateHandle.get<Int?>(KEY_SELECTED_ELDER_ID)
                    if (restoredId != null && _elderInfoList.value.any { it.id == restoredId }) {
                        _selectedElderId.value = restoredId
                        val restoredName = _elderInfoList.value.first { it.id == restoredId }.name
                        _homeUiState.update { it.copy(elderName = restoredName) }
                    } else if (_selectedElderId.value == null && _elderInfoList.value.isNotEmpty()) {
                        val first = _elderInfoList.value.first()
                        _selectedElderId.value = first.id
                        _homeUiState.update { it.copy(elderName = first.name) }
                    }
                }
                .onFailure { error -> Log.e(TAG, "어르신 목록 로딩 실패", error) }
        }
    }


    /**
     * 특정 어르신 ID를 받아서 홈 화면 데이터를 서버에 요청합니다.
     */
    private fun fetchHomeSummaryForToday(elderId: Int) {
        viewModelScope.launch {
            isLoading = true
            val today = LocalDate.now()
            try {
                // 서버에서 홈 데이터 가져오기
                val ui = homeRepository.getHomeUiState(elderId, today)

                // 등록된 약 정보에서 '올바른 순서'를 가져옵니다.
                val correctMedicationOrder = eldersHealthInfoRepository.getEldersHealthInfo()
                    .getOrNull()
                    ?.firstOrNull { it.elderId == elderId }
                    ?.medications
                    ?.flatMap { it.value } // 모든 약 이름을 하나의 리스트로 만듦
                    ?.distinct() ?: emptyList()

                // 서버에서 받은 약 목록을 '올바른 순서'에 맞춰 재정렬
                val sortedMedicines = ui.medicines.sortedBy { medUiState ->
                    correctMedicationOrder.indexOf(medUiState.medicineName)
                        .let { if (it == -1) Int.MAX_VALUE else it }
                }

                // 정렬된 목록으로 UI 상태를 업데이트
                _homeUiState.value = ui.copy(medicines = sortedMedicines)

            } catch (e: Exception) {
                Log.e(TAG, "getHomeSummary failed elderId=$elderId", e)
                _homeUiState.value = HomeUiState.EMPTY
            }
            isLoading = false
        }
    }

    // 드롭다운에서 어르신을 선택했을 때 호출
    fun selectElder(name: String) {
        // 타이틀용 이름 즉시 반영
        _homeUiState.update { it.copy(elderName = name) }

        // 이름으로 ID 찾기
        val id: Int = elderIdByName.value[name] ?: return

        // 선택된 ID 갱신 → collect 블록이 fetchHomeSummaryForToday(id) 호출
        _selectedElderId.value = id
    }
    // 이름 → ID 매핑
    private val elderIdByName: StateFlow<Map<String, Int>> =
        _elderInfoList.mapState { list -> list.associate { it.name to it.id } }
}

// StateFlow 변환용 확장 함수
fun <T, R> StateFlow<T>.mapState(
    scope: CoroutineScope = GlobalScope,
    transform: (T) -> R
): StateFlow<R> {
    return map(transform).stateIn(scope, SharingStarted.Eagerly, transform(value))
}