package com.konkuk.medicarecall.ui.statistics

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.statistics.data.StatisticsRepository
import com.konkuk.medicarecall.ui.statistics.model.StatisticsResponseDto
import com.konkuk.medicarecall.ui.statistics.model.WeeklyGlucoseUiState
import com.konkuk.medicarecall.ui.statistics.model.WeeklyMealUiState
import com.konkuk.medicarecall.ui.statistics.model.WeeklyMedicineUiState
import com.konkuk.medicarecall.ui.statistics.model.WeeklyMentalUiState
import com.konkuk.medicarecall.ui.statistics.model.WeeklySummaryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class StatisticsUiState(
    val isLoading: Boolean = false,
    val summary: WeeklySummaryUiState? = null,
    val error: String? = null
)

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: StatisticsRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(StatisticsUiState())
    val uiState: State<StatisticsUiState> = _uiState

    private val guardianId = 1
    fun getWeeklyStatistics(elderId: Int, startDate: LocalDate) {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.value = StatisticsUiState(isLoading = true)

            // API 호출 전에 요청 정보를 로그로 출력
            val formattedDate = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
            Log.d("API_DEBUG", "--- Requesting Weekly Statistics ---")
            Log.d("API_DEBUG", "elderId: $elderId")
            Log.d("API_DEBUG", "startDate: $formattedDate")
            // ---------------------------------------------

            try {
                val responseDto = repository.getStatistics(
                    elderId = 1,
                    startDate = "2025-07-15"
                )
                val summaryUiState = responseDto.toWeeklySummaryUiState()
                _uiState.value = StatisticsUiState(isLoading = false, summary = summaryUiState)

                Log.i("API_SUCCESS", "Successfully fetched weekly statistics for elderId: $elderId")

            } catch (e: Exception) {
                //오류 발생시 로그출력
                Log.e("API_ERROR", "API call failed for elderId: $elderId", e)
                _uiState.value = StatisticsUiState(isLoading = false, error = "데이터 로딩 실패: ${e.message}")
            }
        }
    }
}

private fun StatisticsResponseDto.toWeeklySummaryUiState(): WeeklySummaryUiState {
    return WeeklySummaryUiState(
        weeklyMealRate = this.summaryStats.mealRate,
        weeklyMedicineRate = this.summaryStats.medicationRate,
        weeklyHealthIssueCount = this.summaryStats.healthSignals,
        weeklyUnansweredCount = this.summaryStats.missedCalls,

        weeklyMeals = listOf(
            WeeklyMealUiState("아침", this.mealStats.breakfast, 7),
            WeeklyMealUiState("점심", this.mealStats.lunch, 7),
            WeeklyMealUiState("저녁", this.mealStats.dinner, 7)
        ),

        weeklyMedicines = this.medicationStats.map { (name, stats) ->
            WeeklyMedicineUiState(
                medicineName = name,
                takenCount = stats.takenCount,
                totalCount = stats.totalCount
            )
        },

        weeklyHealthNote = this.healthSummary,
        weeklySleepHours = this.averageSleep.hours,
        weeklySleepMinutes = this.averageSleep.minutes,

        weeklyMental = WeeklyMentalUiState(
            good = this.psychSummary.good,
            normal = this.psychSummary.normal,
            bad = this.psychSummary.bad
        ),

        weeklyGlucose = WeeklyGlucoseUiState(
            beforeMealNormal = this.bloodSugar.beforeMeal.normal,
            beforeMealHigh = this.bloodSugar.beforeMeal.high,
            beforeMealLow = this.bloodSugar.beforeMeal.low,
            afterMealNormal = this.bloodSugar.afterMeal.normal,
            afterMealHigh = this.bloodSugar.afterMeal.high,
            afterMealLow = this.bloodSugar.afterMeal.low
        )
    )
}