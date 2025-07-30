package com.konkuk.medicarecall.ui.homedetail.sleep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.homedetail.sleep.data.SleepRepository
import com.konkuk.medicarecall.ui.homedetail.sleep.model.SleepUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SleepViewModel @Inject constructor(
    private val sleepRepository: SleepRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _sleepState = MutableStateFlow(
        SleepUiState(
            date = LocalDate.now().toString(),
            totalSleepHours = 0,
            totalSleepMinutes = 0,
            bedTime = "--",
            wakeUpTime = "--",
            isRecorded = false
        )
    )
    val sleep: StateFlow<SleepUiState> = _sleepState

    init {
        fetchSleepData(_selectedDate.value)
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        fetchSleepData(date)
    }

    private fun fetchSleepData(date: LocalDate) {
        viewModelScope.launch {
            _sleepState.value = sleepRepository.getSleepUiState(guardianId = 1, date = date)
        }
    }

    fun getCurrentWeekDates(): List<LocalDate> {
        val selected = _selectedDate.value
        val dayOfWeek = selected.dayOfWeek.value % 7 // 일요일=0
        val sunday = selected.minusDays(dayOfWeek.toLong())
        return (0..6).map { sunday.plusDays(it.toLong()) }
    }
}