package com.konkuk.medicarecall.ui.calendar

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun getCurrentYear() = _selectedDate.value.year
    fun getCurrentMonth() = _selectedDate.value.monthValue
    fun getCurrentWeekDates(): List<LocalDate> {
        val start = _selectedDate.value.with(DayOfWeek.MONDAY)
        return (0..6).map { start.plusDays(it.toLong()) }
    }
}
