package com.konkuk.medicarecall.ui.homedetail.statehealth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.homedetail.statehealth.data.HealthRepository
import com.konkuk.medicarecall.ui.homedetail.statehealth.model.HealthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(
    private val healthRepository: HealthRepository
) : ViewModel() {

    private companion object {
        const val TAG = "HEALTH_API"
    }

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _health = MutableStateFlow(HealthUiState.EMPTY)
    val health: StateFlow<HealthUiState> = _health

    fun loadHealthDataForDate(elderId: Int, date: LocalDate) {
        viewModelScope.launch {
            if (!_isLoading.value) _isLoading.value = true
            val formatted = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            Log.d(TAG, "Request elderId=$elderId, date=$formatted")


            healthRepository.getHealthUiState(elderId = elderId, date = date)
                .onSuccess {
                    _health.value = it
                    Log.d(TAG, "Success elderId=$elderId, date=$formatted")
                }
                .onFailure {
                    Log.d(TAG, "Failed elderId=$elderId, date=$formatted")
                    _health.value = HealthUiState.EMPTY
                }
            _isLoading.value = false
        }
    }
}