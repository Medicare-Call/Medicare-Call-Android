package com.konkuk.medicarecall.ui.homedetail.sleep

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.homedetail.sleep.data.SleepRepository
import com.konkuk.medicarecall.ui.homedetail.sleep.model.SleepUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class SleepViewModel @Inject constructor(
    private val sleepRepository: SleepRepository
) : ViewModel() {

    private companion object {
        const val TAG = "SLEEP_API"
        const val ELDER_ID = 1 // 테스트용 하드코딩. 실제 로그인 연동 시 세션에서 가져오세요.
    }

    private val _sleepState = MutableStateFlow(SleepUiState.EMPTY)
    val sleep: StateFlow<SleepUiState> = _sleepState

    fun loadSleepDataForDate(date: LocalDate) {
        viewModelScope.launch {
            val formatted = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            Log.d(TAG, "Request elderId=$ELDER_ID, date=$formatted")

            try {
                _sleepState.value = sleepRepository.getSleepUiState(
                    elderId = ELDER_ID,
                    date = date
                )
                Log.i(TAG, "Success elderId=$ELDER_ID, date=$formatted")
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        when (e.code()) {
                            404 -> {
                                // ✅ 미기록: 서버에서 데이터 없음
                                Log.i(TAG, "No data (404) elderId=$ELDER_ID, date=$formatted")
                                _sleepState.value = SleepUiState.EMPTY
                            }
                            400 -> {
                                Log.w(TAG, "Bad request (400) elderId=$ELDER_ID, date=$formatted, msg=${e.message()}")
                                _sleepState.value = SleepUiState.EMPTY
                            }
                            401, 403 -> {
                                Log.w(TAG, "Unauthorized (${e.code()}) elderId=$ELDER_ID")
                                _sleepState.value = SleepUiState.EMPTY
                            }
                            else -> {
                                Log.e(TAG, "API error code=${e.code()} elderId=$ELDER_ID, date=$formatted", e)
                                _sleepState.value = SleepUiState.EMPTY
                            }
                        }
                    }
                    else -> {
                        Log.e(TAG, "Unexpected error elderId=$ELDER_ID, date=$formatted", e)
                        _sleepState.value = SleepUiState.EMPTY
                    }
                }
            }
        }
    }
}