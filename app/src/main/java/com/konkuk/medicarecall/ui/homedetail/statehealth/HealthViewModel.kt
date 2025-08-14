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


    private val _health = MutableStateFlow(HealthUiState.EMPTY)
    val health: StateFlow<HealthUiState> = _health


    fun loadHealthDataForDate(date: LocalDate) {
        viewModelScope.launch {
            _health.value = healthRepository.getHealthUiState(elderId = 1, date = date)
        }
    }
}