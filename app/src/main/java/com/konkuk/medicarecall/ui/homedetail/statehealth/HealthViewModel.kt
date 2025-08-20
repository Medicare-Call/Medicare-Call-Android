package com.konkuk.medicarecall.ui.homedetail.statehealth

import android.util.Log
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

    private val _health = MutableStateFlow(HealthUiState.EMPTY)
    val health: StateFlow<HealthUiState> = _health

    fun loadHealthDataForDate(elderId: Int, date: LocalDate) {
        viewModelScope.launch {
            val formatted = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            Log.d(TAG, "Request elderId=$elderId, date=$formatted")

            try {
                val ui = healthRepository.getHealthUiState(elderId = elderId, date = date)
                _health.value = ui
                Log.i(TAG, "Success elderId=$elderId, date=$formatted")
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        when (e.code()) {
                            404 -> { // 미기록
                                Log.i(TAG, "No data (404) elderId=$elderId, date=$formatted")
                                _health.value = HealthUiState.EMPTY
                            }

                            400 -> {
                                Log.w(
                                    TAG,
                                    "Bad request (400) elderId=$elderId, date=$formatted, msg=${e.message()}"
                                )
                                _health.value = HealthUiState.EMPTY
                            }

                            401, 403 -> {
                                Log.w(TAG, "Unauthorized (${e.code()}) elderId=$elderId")
                                _health.value = HealthUiState.EMPTY
                            }

                            else -> {
                                Log.e(
                                    TAG,
                                    "API error code=${e.code()} elderId=$elderId, date=$formatted",
                                    e
                                )
                                _health.value = HealthUiState.EMPTY
                            }
                        }
                    }

                    else -> {
                        Log.e(TAG, "Unexpected error elderId=$elderId, date=$formatted", e)
                        _health.value = HealthUiState.EMPTY
                    }
                }
            }
        }
    }
}