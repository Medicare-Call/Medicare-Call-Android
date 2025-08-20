package com.konkuk.medicarecall.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.data.repository.EldersHealthInfoRepository
import com.konkuk.medicarecall.ui.statistics.data.StatisticsRepository
import com.konkuk.medicarecall.ui.statistics.model.MedicationStatDto
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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
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
    private val eldersHealthInfoRepository: EldersHealthInfoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()


    private val _selectedElderId = MutableStateFlow<Int?>(null)

    // 주차 이동 상태
    private val _currentWeek = MutableStateFlow(getWeekRange(LocalDate.now()))
    val currentWeek: StateFlow<Pair<LocalDate, LocalDate>> = _currentWeek

    private val _isLatestWeek = MutableStateFlow(true)
    val isLatestWeek: StateFlow<Boolean> = _isLatestWeek

    private val _isEarliestWeek = MutableStateFlow(true)
    val isEarliestWeek: StateFlow<Boolean> = _isEarliestWeek

    // 필요 시 서버로부터 갱신 (초기값은 오늘 기준)
    private var earliestDate: LocalDate = LocalDate.now()

    init {
        // ★ elderId/주차가 바뀔 때에만 로드
        viewModelScope.launch {
            _selectedElderId
                .combine(_currentWeek) { id, week -> id to week }
                .distinctUntilChanged()
                .collect { (id, week) ->
                    if (id != null) getWeeklyStatistics(elderId = id, startDate = week.first)
                }
        }
    }

    /** 설정에서 복약 항목 변경 후 강제 새로고침 */
    fun refresh() {
        val id = _selectedElderId.value ?: return
        val start = _currentWeek.value.first
        getWeeklyStatistics(
            elderId = id,
            startDate = start,
            ignoreLoadingGate = true
        )
    }

    // ★ 외부(HomeVM)에서만 elderId 설정
    fun setSelectedElderId(id: Int) {
        if (_selectedElderId.value != id) _selectedElderId.value = id
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

    fun jumpToTodayWeek() = jumpToWeekOf(LocalDate.now())
    fun jumpToWeekOf(date: LocalDate) = updateWeekState(weekStartOf(date))

    private fun updateWeekState(weekStart: LocalDate) {
        _currentWeek.value = weekStart to weekStart.plusDays(6)
        _isLatestWeek.value = weekStart == weekStartOf(LocalDate.now())
        _isEarliestWeek.value = weekStart == weekStartOf(earliestDate)
    }

    /* ---------------- Week 계산 ---------------- */

    private fun getWeekRange(date: LocalDate): Pair<LocalDate, LocalDate> {
        val start = weekStartOf(date)
        return start to start.plusDays(6)
    }

    private fun weekStartOf(date: LocalDate): LocalDate =
        date.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY))

    /* ---------------- 데이터 로딩 ---------------- */

    private fun getWeeklyStatistics(
        elderId: Int,
        startDate: LocalDate,
        ignoreLoadingGate: Boolean = false
    ) {
        if (_uiState.value.isLoading && !ignoreLoadingGate) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            runCatching {
                val formatted = startDate.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE)

                // 설정(건강정보) 최신화 후 순서 확보

                val correctOrder: List<String> =
                    eldersHealthInfoRepository.getEldersHealthInfo()
                        .getOrNull()
                        ?.firstOrNull { it.elderId == elderId }
                        ?.medications
                        ?.flatMap { it.value }
                        ?.distinct()
                        ?: emptyList()

                repository.getStatistics(elderId, formatted) to correctOrder
            }.onSuccess { (dto, order) ->

                android.util.Log.d("STATISTICS_DEBUG", "onSuccess: DTO 수신 완료\n$dto")
                val summary = dto.toWeeklySummaryUiState(order)
                android.util.Log.d("STATISTICS_DEBUG", "onSuccess: UI State로 변환 완료\n$summary")

                _uiState.value = StatisticsUiState(
                    isLoading = false,
                    summary = summary,
                    error = null
                )
            }.onFailure { e ->

                android.util.Log.e("STATISTICS_DEBUG", "onFailure: 데이터 로딩 실패", e)

                if (e is HttpException && e.code() == 404) {
                    _uiState.value = StatisticsUiState(
                        isLoading = false,
                        summary = WeeklySummaryUiState.EMPTY
                    )
                } else {
                    _uiState.value = StatisticsUiState(
                        isLoading = false,
                        error = "데이터 로딩 실패: ${e.message}"
                    )
                }
            }
        }
    }

    private fun StatisticsResponseDto.toWeeklySummaryUiState(
        correctOrder: List<String>
    ): WeeklySummaryUiState {

        val indexOf: (String) -> Int = { key ->
            val idx = correctOrder.indexOf(key)
            if (idx == -1) Int.MAX_VALUE else idx
        }

        val orderedMedicines = medicationStats.entries
            .sortedWith(
                compareBy<Map.Entry<String, MedicationStatDto>>(
                    { indexOf(it.key) }, { it.key }
                ))
            .map { (name, stats) ->
                WeeklyMedicineUiState(
                    medicineName = name,
                    takenCount = stats.takenCount,
                    totalCount = stats.totalCount
                )
            }


        val sleepH = averageSleep.hours
        val sleepM = averageSleep.minutes

        return WeeklySummaryUiState(
            elderName = elderName,
            weeklyMealRate = summaryStats.mealRate,
            weeklyMedicineRate = summaryStats.medicationRate,
            weeklyHealthIssueCount = summaryStats.healthSignals,
            weeklyUnansweredCount = summaryStats.missedCalls,
            weeklyMeals = listOf(
                WeeklyMealUiState("아침", mealStats.breakfast, 7),
                WeeklyMealUiState("점심", mealStats.lunch, 7),
                WeeklyMealUiState("저녁", mealStats.dinner, 7)
            ),
            weeklyMedicines = orderedMedicines,
            weeklyHealthNote = healthSummary,
            weeklySleepHours = sleepH,
            weeklySleepMinutes = sleepM,
            weeklyMental = WeeklyMentalUiState(
                good = psychSummary.good,
                normal = psychSummary.normal,
                bad = psychSummary.bad
            ),
            weeklyGlucose = WeeklyGlucoseUiState(
                beforeMealNormal = bloodSugar.beforeMeal.normal,
                beforeMealHigh = bloodSugar.beforeMeal.high,
                beforeMealLow = bloodSugar.beforeMeal.low,
                afterMealNormal = bloodSugar.afterMeal.normal,
                afterMealHigh = bloodSugar.afterMeal.high,
                afterMealLow = bloodSugar.afterMeal.low
            )

        )
    }
}