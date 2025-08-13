package com.konkuk.medicarecall.ui.login.login_care_call.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.data.dto.request.SetCallTimeRequestDto
import com.konkuk.medicarecall.data.repository.ElderIdRepository
import com.konkuk.medicarecall.data.repository.SetCallRepository
import com.konkuk.medicarecall.ui.model.CallTimes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CallTimeViewModel @Inject constructor (
    private val setCallRepo : SetCallRepository,
    private val elderIdRepo : ElderIdRepository
) : ViewModel() {
    val timeMap = mutableStateMapOf<String, CallTimes>()

    fun setTimes(name: String, times: CallTimes) {
        timeMap[name] = times
    }
    fun getTimes(name: String): CallTimes? = timeMap[name]

    fun isCompleteFor(name: String): Boolean {
        val t = timeMap[name] ?: return false
        return t.first != null && t.second != null && t.third != null
    }

    fun isAllComplete(names: List<String>): Boolean =
        names.isNotEmpty() && names.all { isCompleteFor(it) }


    // SetCallScreen에서 한 번에 저장: seniorNames의 순서 == elderIdRepo 순서
    fun submitAllUsingRepoOrder(
        seniorNamesInOrder: List<String>,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            Log.d("CallTimeViewModel", "submitAllUsingRepoOrder: $seniorNamesInOrder")
            try {
                val ids = elderIdRepo.getElderIds()
                require(ids.size >= seniorNamesInOrder.size) {
                    "elderId 개수(${ids.size})가 어르신 수(${seniorNamesInOrder.size})보다 적습니다."
                }

                val jobs = seniorNamesInOrder.mapIndexed { index, name ->
                    val times = timeMap[name]
                        ?: error("'$name'의 시간이 비어있습니다.")
                    val elderId = ids[index]
                    async { setCallRepo.saveForElder(elderId,times).getOrThrow() }
                }
                jobs.awaitAll()
                onSuccess()
            } catch (t: Throwable) {
                onError(t)
            }
        }
    }

    // 선택된 한 명만 저장하고 싶을 때 (index로 elderId 매칭)
    fun submitOneByIndex(
        seniorNamesInOrder: List<String>,
        selectedIndex: Int,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val ids = elderIdRepo.getElderIds()
                require(selectedIndex in seniorNamesInOrder.indices) { "잘못된 인덱스" }
                require(selectedIndex in ids.indices) { "elderId가 등록되지 않았습니다." }

                val name = seniorNamesInOrder[selectedIndex]
                val times = timeMap[name] ?: error("'$name'의 시간이 비어있습니다.")
                val elderId = ids[selectedIndex]

                setCallRepo.saveForElder(elderId, times).getOrThrow()
                onSuccess()
            } catch (t: Throwable) {
                onError(t)
            }
        }
    }
}