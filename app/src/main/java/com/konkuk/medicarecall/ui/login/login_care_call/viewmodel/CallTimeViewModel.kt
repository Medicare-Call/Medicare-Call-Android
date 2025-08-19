package com.konkuk.medicarecall.ui.login.login_care_call.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.data.repository.ElderIdRepository
import com.konkuk.medicarecall.data.repository.SetCallRepository
import com.konkuk.medicarecall.ui.model.CallTimes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CallTimeViewModel @Inject constructor(
    private val setCallRepo: SetCallRepository,
    private val elderIdRepo: ElderIdRepository
) : ViewModel() {
    val timeMap = mutableStateMapOf<Int, CallTimes>()
    val isLoading = mutableStateOf(false)
    val lastError = mutableStateOf<Throwable?>(null)

    fun setTimes(id: Int, times: CallTimes) {
        timeMap[id] = times
    }

    fun isCompleteFor(id: Int): Boolean {
        val t = timeMap[id] ?: return false
        return t.first != null && t.second != null && t.third != null
    }

    fun isAllComplete(ids: List<Int>): Boolean =
        ids.isNotEmpty() && ids.all { isCompleteFor(it) }


    /** SetCallScreen에서 '확인' 눌렀을 때: 이름 기준으로 안전 저장 (순서 의존 X) */
    fun submitAllByIds(
        elderIds: List<Int>,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = true
            lastError.value = null
            try {
                require(elderIds.isNotEmpty()) { "어르신 목록이 비어 있습니다." }
                val jobs = elderIds.map { id ->
                    val times = timeMap[id] ?: error("'$id'의 시간이 비어있습니다.")
                    async {
                        setCallRepo.saveForElder(id, times).getOrThrow()
                        Log.d("CallTimeViewModel", "Saved call times for id:$id")
                    }
                }
                jobs.awaitAll()
                onSuccess()
            } catch (t: Throwable) {
                Log.e("CallTimeViewModel", "submitAllByName failed", t)
                lastError.value = t
                onError(t)
            } finally {
                isLoading.value = false
            }
        }
    }
}

    /** 특정 인덱스 선택 저장이 필요하면: 인덱스 -> 이름 -> ID 로 안전 매핑 */
//    fun submitOneByIndex(
//        elderNamesInOrder: List<String>,
//        selectedIndex: Int,
//        onSuccess: () -> Unit,
//        onError: (Throwable) -> Unit
//    ) {
//        viewModelScope.launch {
//            isLoading.value = true
//            lastError.value = null
//            try {
//                require(selectedIndex in elderNamesInOrder.indices) { "잘못된 인덱스" }
//
//                val name = elderNamesInOrder[selectedIndex]
//                val times = timeMap[name] ?: error("'$name'의 시간이 비어있습니다.")
//                val elderId = buildNameToId()[name]
//                    ?: error("'$name'에 해당하는 elderId를 찾을 수 없습니다.")
//
//                setCallRepo.saveForElder(elderId, times).getOrThrow()
//                Log.d("CallTimeViewModel", "Saved call times for $name(id=$elderId)")
//                onSuccess()
//            } catch (t: Throwable) {
//                Log.e("CallTimeViewModel", "submitOneByIndex failed", t)
//                lastError.value = t
//                onError(t)
//            } finally {
//                isLoading.value = false
//            }
//        }
//
//        fun submitAllWithPairs(
//            pairs: List<Pair<String, Int>>, // (displayName, elderId)
//            onSuccess: () -> Unit,
//            onError: (Throwable) -> Unit
//        ) {
//            viewModelScope.launch {
//                isLoading.value = true; lastError.value = null
//                try {
//                    val jobs = pairs.map { (name, id) ->
//                        val times = timeMap[name] ?: error("'$name'의 시간이 비어있습니다.")
//                        async { setCallRepo.saveForElder(id, times).getOrThrow() }
//                    }
//                    jobs.awaitAll()
//                    onSuccess()
//                } catch (t: Throwable) {
//                    Log.e("CallTimeViewModel", "submitAllWithPairs failed", t)
//                    lastError.value = t
//                    onError(t)
//                } finally { isLoading.value = false }
//            }
//        }

//    }
