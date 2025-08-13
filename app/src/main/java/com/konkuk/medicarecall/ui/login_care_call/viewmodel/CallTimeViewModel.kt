package com.konkuk.medicarecall.ui.login_care_call.viewmodel

import android.R.attr.name
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.data.dto.request.SetCallTimeRequestDto
import com.konkuk.medicarecall.data.repository.SetCallRepository
import com.konkuk.medicarecall.ui.login_care_call.screen.CallTimes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CallTimeViewModel @Inject constructor (
    private val repo : SetCallRepository
) : ViewModel() {
    val timeMap = mutableStateMapOf<String, CallTimes>()

    fun setTimes(name: String, times: CallTimes) {
        timeMap[name] = times
    }
    fun getTimes(name: String): CallTimes? = timeMap[name]

//    private fun Triple<Int, Int, Int>.toHHmm(): String {
//        val (amPm, h12, minute) = this
//        val h24 = when {
//            amPm == 0 && h12 == 12 -> 0 // 12 Am -> 00
//            amPm == 1 && h12 < 12 -> h12 + 12 // PM and not 12 -> +12
//            else -> h12 % 24
//        }
//        return "%02d:%02d".format(h24, minute)
//    }

    fun isCompleteFor(name: String): Boolean {
        val t = timeMap[name] ?: return false
        return t.first != null && t.second != null && t.third != null
    }

    fun isAllComplete(names: List<String>): Boolean =
        names.isNotEmpty() && names.all { isCompleteFor(it) }


}