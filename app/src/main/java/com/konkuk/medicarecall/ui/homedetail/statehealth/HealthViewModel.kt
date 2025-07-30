package com.konkuk.medicarecall.ui.homedetail.statehealth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.homedetail.statehealth.data.HealthRepository
import com.konkuk.medicarecall.ui.homedetail.statehealth.model.HealthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class HealthViewModel @Inject constructor(
    private val healthRepository: HealthRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _health = MutableStateFlow(
        HealthUiState(
            symptoms = listOf("건강징후 기록 전이에요."),
            symptomAnalysis = "증상분석 전이에요.",
            isRecorded = false
        )
    )
    val health: StateFlow<HealthUiState> = _health

    init {
        fetchHealthData(_selectedDate.value)
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        fetchHealthData(date)
    }

    private fun fetchHealthData(date: LocalDate) {
        viewModelScope.launch {
            _health.value = healthRepository.getHealthUiState(
                guardianId = 1,
                date = date
            )
        }
    }

    fun getCurrentWeekDates(): List<LocalDate> {
        val selected = _selectedDate.value
        val dayOfWeek = selected.dayOfWeek.value % 7 // 일요일 = 0
        val sunday = selected.minusDays(dayOfWeek.toLong())
        return (0..6).map { sunday.plusDays(it.toLong()) }
    }
}

