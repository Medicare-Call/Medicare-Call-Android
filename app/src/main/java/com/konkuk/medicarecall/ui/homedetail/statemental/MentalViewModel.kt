package com.konkuk.medicarecall.ui.homedetail.statemental

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.homedetail.statemental.data.MentalRepository
import com.konkuk.medicarecall.ui.homedetail.statemental.model.MentalUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MentalViewModel @Inject constructor(
    private val mentalRepository: MentalRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _mental = MutableStateFlow(
        MentalUiState(
            mentalSummary = listOf("심리 상태 기록 전이에요."),
            isRecorded = false
        )
    )
    val mental: StateFlow<MentalUiState> = _mental

    init {
        fetchMentalData(_selectedDate.value)
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        fetchMentalData(date)
    }

    private fun fetchMentalData(date: LocalDate) {
        viewModelScope.launch {
            _mental.value = mentalRepository.getMentalUiState(
                guardianId = 1,
                date = date
            )
        }
    }

    fun getCurrentWeekDates(): List<LocalDate> {
        val selected = _selectedDate.value
        val dayOfWeek = selected.dayOfWeek.value % 7 // 일요일=0
        val sunday = selected.minusDays(dayOfWeek.toLong())
        return (0..6).map { sunday.plusDays(it.toLong()) }
    }
}
