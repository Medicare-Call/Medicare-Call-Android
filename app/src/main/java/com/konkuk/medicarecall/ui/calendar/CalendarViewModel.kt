package com.konkuk.medicarecall.ui.calendar

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _currentWeek = MutableStateFlow(calculateWeekRange(LocalDate.now()))
    val currentWeek: StateFlow<Pair<LocalDate, LocalDate>> = _currentWeek.asStateFlow()

    private val _isLatestWeek = MutableStateFlow(true)
    val isLatestWeek: StateFlow<Boolean> = _isLatestWeek.asStateFlow()

    // 해당 날짜가 속한 '주'의 정보도 함께 업데이트
    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        updateWeekState(date)
    }

    fun showPreviousWeek() {
        // 저번주 보기
        val newDate = _currentWeek.value.first.minusWeeks(1)
        updateWeekState(newDate)
    }

    fun showNextWeek() {
        val newDate = _currentWeek.value.first.plusWeeks(1)
        updateWeekState(newDate)
    }

    //새로고침
    private fun updateWeekState(date: LocalDate) {
        _currentWeek.value = calculateWeekRange(date)

        val today = LocalDate.now()
        val todayWeek = calculateWeekRange(today)
        _isLatestWeek.value = _currentWeek.value == todayWeek
    }

    private fun calculateWeekRange(date: LocalDate): Pair<LocalDate, LocalDate> {
        val startOfWeek = date.with(DayOfWeek.MONDAY)
        val endOfWeek = date.with(DayOfWeek.SUNDAY)
        return Pair(startOfWeek, endOfWeek)
    }


    fun getCurrentYear() = _selectedDate.value.year
    fun getCurrentMonth() = _selectedDate.value.monthValue
    fun getCurrentWeekDates(): List<LocalDate> {
        val start = _currentWeek.value.first
        return (0..6).map { start.plusDays(it.toLong()) }
    }
}