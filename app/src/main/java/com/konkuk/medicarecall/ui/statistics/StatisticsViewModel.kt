package com.konkuk.medicarecall.ui.statistics

import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import javax.inject.Inject

data class StatisticsUiState(
    val isLoading: Boolean = false,
    val summary: WeeklySummaryUiState? = null,
    val error: String? = null
)

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: StatisticsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()

    private val _currentWeek = MutableStateFlow(getWeekRange(LocalDate.now()))
    val currentWeek: StateFlow<Pair<LocalDate, LocalDate>> = _currentWeek

    private val _isLatestWeek = MutableStateFlow(true)
    val isLatestWeek: StateFlow<Boolean> = _isLatestWeek

    private val _isEarliestWeek = MutableStateFlow(true)
    val isEarliestWeek: StateFlow<Boolean> = _isEarliestWeek

    // 사용자의 데이터 시작일
    private var earliestDate: LocalDate = LocalDate.now()

    init {
        viewModelScope.launch {
            // TODO: 실제 earliestDate 로드
            earliestDate = LocalDate.now()
            jumpToWeekOf(LocalDate.now())
        }
    }

    /* ---------------- Week 이동 ---------------- */

    fun showPreviousWeek() {
        if (_isEarliestWeek.value) return
        val newStart = _currentWeek.value.first.minusWeeks(1)
        updateWeekState(newStart)
    }

    fun showNextWeek() {
        if (_isLatestWeek.value) return
        val newStart = _currentWeek.value.first.plusWeeks(1)
        updateWeekState(newStart)
    }

    /** 오늘이 속한 주로 이동 */
    fun jumpToTodayWeek() = jumpToWeekOf(LocalDate.now())

    /** 주어진 날짜가 속한 주로 이동 */
    fun jumpToWeekOf(date: LocalDate) = updateWeekState(weekStartOf(date))

    private fun updateWeekState(weekStart: LocalDate) {
        _currentWeek.value = Pair(weekStart, weekStart.plusDays(6))
        _isLatestWeek.value = weekStart == weekStartOf(LocalDate.now())
        _isEarliestWeek.value = weekStart == weekStartOf(earliestDate)
    }

    /* ---------------- Week 계산 ---------------- */

    private fun getWeekRange(date: LocalDate): Pair<LocalDate, LocalDate> {
        val start = weekStartOf(date)
        return Pair(start, start.plusDays(6))
    }

    private fun weekStartOf(date: LocalDate): LocalDate =
        date.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY))

    /* ---------------- 데이터 로딩 ---------------- */

    fun getWeeklyStatistics(elderId: Int, startDate: LocalDate) {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.value = StatisticsUiState(isLoading = true)

            val formatted = startDate.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE)
            try {
                val dto = repository.getStatistics(elderId, formatted)
                _uiState.value = StatisticsUiState(
                    isLoading = false,
                    summary = dto.toWeeklySummaryUiState(),
                    error = null
                )
            } catch (e: Exception) {
                if (e is HttpException && e.code() == 404) {
                    Log.i("API_INFO", "No data found (404), showing empty state.")
                    _uiState.value = StatisticsUiState(isLoading = false, summary = WeeklySummaryUiState.EMPTY)
                } else {
                    Log.e("API_ERROR", "API call failed", e)
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
}