package com.konkuk.medicarecall.ui.homedetail.glucoselevel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.calendar.getWeekDatesFromDate
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.data.GlucoseRepository
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.*
import com.konkuk.medicarecall.ui.util.averageOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GlucoseViewModel @Inject constructor(
    private val glucoseRepository: GlucoseRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(GlucoseUiState())
    val uiState: State<GlucoseUiState> = _uiState

    private var lastCursorDate: String? = null
    private var isRequesting = false

    fun loadDummyWeek() {
        val today = LocalDate.now()
        val weekLabels = getWeekDatesFromDate(today)
            .map { it.format(DateTimeFormatter.ofPattern("M.d", Locale.KOREA)) }

        val dummyWeek = GlucoseWeeklyState(
            beforeMealGraph = listOf(65, 110, 129, 180, 131, 125, 115),
            afterMealGraph = listOf(), // 식후 미기록 테스트용
            weekLabels = weekLabels
        )

        _uiState.value = GlucoseUiState(
            weeklyData = listOf(dummyWeek),
            hasNext = false,
            isLoading = false
        )
    }



    // ✅ 현재 주차 데이터를 바탕으로 DailyState 계산
    fun dailyData(week: GlucoseWeeklyState): GlucoseDailyState {
        val before = week.beforeMealGraph
        val after = week.afterMealGraph

        return GlucoseDailyState(
            selectedTiming = GlucoseTiming.BEFORE_MEAL,
            dailyAverageBeforeMeal = before.averageOrNull()?.toInt() ?: 0,
            dailyAverageAfterMeal = after.averageOrNull()?.toInt() ?: 0,
            recentBeforeMeal = before.lastOrNull() ?: 0,
            recentAfterMeal = after.lastOrNull() ?: 0,
            glucoseLevelStatusBeforeMeal = GlucoseStatusUtil.getStatusName(before.lastOrNull()),
            glucoseLevelStatusAfterMeal = GlucoseStatusUtil.getStatusName(after.lastOrNull()),
            isRecorded = before.isNotEmpty() || after.isNotEmpty()
        )
    }

    // ✅ 다음 주차 데이터 로드 + 페이징 처리
    fun loadNextWeek(guardianId: Int) {
        if (isRequesting || !_uiState.value.hasNext) return
        isRequesting = true

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val response = glucoseRepository.getWeeklyGlucose(
                    guardianId = guardianId,
                    cursorDate = lastCursorDate
                )
                if (response.data.isEmpty()) {
                    val emptyWeek = GlucoseWeeklyState(
                        beforeMealGraph = emptyList(),
                        afterMealGraph = emptyList(),
                        weekLabels = listOf("이번 주") // or 오늘 날짜
                    )

                    _uiState.value = _uiState.value.copy(
                        weeklyData = listOf(emptyWeek),
                        hasNext = false,
                        isLoading = false
                    )
                } else {
                    val mapped = response.data.map { day ->
                        val beforeMeal =
                            day.records.filter { it.type == GlucoseType.FASTING }.map { it.value }
                        val afterMeal = day.records.filter { it.type == GlucoseType.AFTER_MEAL }
                            .map { it.value }

                        val weekDates = getWeekDatesFromDate(LocalDate.parse(day.date))
                        val formattedWeek = weekDates.map {
                            it.format(DateTimeFormatter.ofPattern("M.d", Locale.KOREA))
                        }

                        GlucoseWeeklyState(
                            beforeMealGraph = beforeMeal,
                            afterMealGraph = afterMeal,
                            weekLabels = formattedWeek
                        )
                    }

                    _uiState.value = _uiState.value.copy(
                        weeklyData = _uiState.value.weeklyData + mapped,
                        hasNext = response.hasNext,
                        isLoading = false
                    )

                    lastCursorDate = response.data.lastOrNull()?.date
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            } finally {
                isRequesting = false
            }
        }
    }
}
