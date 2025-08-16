package com.konkuk.medicarecall.ui.homedetail.statemental

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.homedetail.statemental.data.MentalRepository
import com.konkuk.medicarecall.ui.homedetail.statemental.model.MentalUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MentalViewModel @Inject constructor(
    private val mentalRepository: MentalRepository
) : ViewModel() {


    private companion object {
        const val ELDER_ID = 1 // 테스트용
        const val TAG = "MENTAL_API"
    }

    private val _mental = MutableStateFlow(MentalUiState.EMPTY)
    val mental: StateFlow<MentalUiState> = _mental

    fun loadMentalDataForDate(date: LocalDate) {
        viewModelScope.launch {
            val formatted = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            Log.d(TAG, "Request elderId=$ELDER_ID, date=$formatted")

            try {
                _mental.value = mentalRepository.getMentalUiState(
                    elderId = ELDER_ID,
                    date = date
                )
                Log.i(TAG, "Success elderId=$ELDER_ID, date=$formatted")
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        when (e.code()) {
                            404 -> {
                                // 미기록
                                Log.i(TAG, "No data (404) elderId=$ELDER_ID, date=$formatted")
                                _mental.value = MentalUiState(
                                    mentalSummary = listOf("건강징후 기록 전이에요."),
                                    isRecorded = false
                                )
                            }
                            400 -> {
                                // 잘못된 요청
                                Log.w(TAG, "Bad request (400) elderId=$ELDER_ID, date=$formatted, msg=${e.message()}")
                                _mental.value = MentalUiState(
                                    mentalSummary = listOf("요청이 올바르지 않아요."),
                                    isRecorded = false
                                )
                            }
                            else -> {
                                // 기타 HTTP 에러
                                Log.e(TAG, "API error code=${e.code()} elderId=$ELDER_ID, date=$formatted", e)
                                _mental.value = MentalUiState(
                                    mentalSummary = listOf("데이터 로딩 실패: ${e.message()}"),
                                    isRecorded = false
                                )
                            }
                        }
                    }
                    else -> {
                        // 네트워크/파싱 등 예상치 못한 오류
                        Log.e(TAG, "Unexpected error elderId=$ELDER_ID, date=$formatted", e)
                        _mental.value = MentalUiState(
                            mentalSummary = listOf("데이터 로딩 실패: ${e.message}"),
                            isRecorded = false
                        )
                    }
                }
            }
        }
    }
}