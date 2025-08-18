package com.konkuk.medicarecall.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val homeRepository: HomeRepository
) : ViewModel() {

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
    }

    // 홈 화면 상태
    private val _homeUiState = MutableStateFlow(HomeUiState.EMPTY)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    // 어르신 전체 목록
    private val _elderInfoList = MutableStateFlow<List<ElderInfo>>(emptyList())

    // 드롭다운에 표시할 어르신 이름 목록
    val elderNameList: StateFlow<List<String>> = _elderInfoList.mapState { list ->
        list.map { it.name }
    }

    // 현재 선택된 어르신 ID
    private val _selectedElderId = MutableStateFlow<Int?>(null)

    init {
        fetchElderList()

        // 선택된 ID가 바뀌면 해당 어르신의 홈 데이터를 불러옴
        viewModelScope.launch {
            _selectedElderId.collect { elderId ->
                if (elderId != null) {
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
                    _elderInfoList.value = elders.map {
                        ElderInfo(id = it.elderId, name = it.name, phone = it.phone)
                    }
                    Log.d(TAG, "어르신 정보 로딩 성공: ${_elderInfoList.value}")
                    // 아직 선택된 ID가 없으면 첫 번째 어르신을 기본 선택
                    if (_selectedElderId.value == null && _elderInfoList.value.isNotEmpty()) {
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
            val today = LocalDate.now()
            try {

                val ui = homeRepository.getHomeUiState(elderId, today)
                Log.d(TAG, "med size=${ui.medicines.size}, data=${ui.medicines}")
                _homeUiState.value = ui
            } catch (e: Exception) {
                Log.e(TAG, "getHomeSummary failed elderId=$elderId", e)
                _homeUiState.value = HomeUiState.EMPTY
            }
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