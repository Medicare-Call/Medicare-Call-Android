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
import java.time.DayOfWeek
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
    private val repository: StatisticsRepository,
    // private val userRepository: UserRepository // 실제 가입일 조회를 위한 예시
) : ViewModel() {

    // --- 통계 데이터 상태 ---
    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()

    // --- 주(Week) 상태 ---
    private val _currentWeek = MutableStateFlow(getWeekRange(LocalDate.now()))
    val currentWeek: StateFlow<Pair<LocalDate, LocalDate>> = _currentWeek

    private val _isLatestWeek = MutableStateFlow(true)
    val isLatestWeek: StateFlow<Boolean> = _isLatestWeek

    private val _isEarliestWeek = MutableStateFlow(true)
    val isEarliestWeek: StateFlow<Boolean> = _isEarliestWeek

    // 사용자의 가입 날짜 또는 데이터가 쌓이기 시작한 가장 첫 날짜
    private var earliestDate: LocalDate = LocalDate.now()


    init {
        // 가입 날짜를 기준으로 WeekendBar 상태를 초기화
        viewModelScope.launch {
            // earliestDate = userRepository.getRegistrationDate() // (실제 코드 예시)

            // 임시로 오늘 날짜를 가입 날짜(가장 첫 데이터)로 설정
            earliestDate = LocalDate.now()
            updateWeekState(LocalDate.now())
        }
    }

    // --- 주(Week) 이동 함수 ---
    fun showPreviousWeek() {
        if (isEarliestWeek.value) return // 가장 첫 주이면 막기
        val newWeekStartDate = _currentWeek.value.first.minusWeeks(1)
        updateWeekState(newWeekStartDate)
    }

    fun showNextWeek() {
        if (isLatestWeek.value) return // 가장 최근 주이면 막기
        val newWeekStartDate = _currentWeek.value.first.plusWeeks(1)
        updateWeekState(newWeekStartDate)
    }

    // --- 주(Week) 상태 업데이트 함수 ---
    private fun updateWeekState(date: LocalDate) {
        _currentWeek.value = getWeekRange(date)
        _isLatestWeek.value = getWeekRange(LocalDate.now()) == _currentWeek.value
        _isEarliestWeek.value = getWeekRange(earliestDate) == _currentWeek.value
    }

    // --- 주(Week) 범위 계산 함수 ---
    private fun getWeekRange(date: LocalDate): Pair<LocalDate, LocalDate> {
        val start = date.with(DayOfWeek.SUNDAY)
        return Pair(start, start.plusDays(6))
    }


    // --- 통계 데이터 요청 함수 ---
    fun getWeeklyStatistics(elderId: Int, startDate: LocalDate) {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.value = StatisticsUiState(isLoading = true)
            val formattedDate = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
            Log.d("API_REQUEST", "Requesting weekly stats for elderId: $elderId, startDate: $formattedDate")

            try {
                val responseDto = repository.getStatistics(
                    elderId = elderId,
                    startDate = formattedDate
                )
                val summaryUiState = responseDto.toWeeklySummaryUiState()
                _uiState.value = StatisticsUiState(isLoading = false, summary = summaryUiState)

                Log.i("API_SUCCESS", "Successfully fetched weekly statistics for elderId: $elderId")

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