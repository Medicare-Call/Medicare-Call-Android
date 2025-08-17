package com.konkuk.medicarecall.ui.homedetail.medicine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.homedetail.medicine.data.MedicineRepository
import com.konkuk.medicarecall.ui.homedetail.medicine.model.MedicineUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val medicineRepository: MedicineRepository
) : ViewModel() {

    private companion object {
        const val TAG = "MED_API"
    }

    private val elderId = 1 // 테스트용

    data class ScreenState(
        val loading: Boolean = false,
        val items: List<MedicineUiState> = emptyList(),
        val emptyDate: LocalDate? = null
    )

    private val _state = MutableStateFlow(ScreenState())
    val state: StateFlow<ScreenState> = _state

    fun loadMedicinesForDate(date: LocalDate) {
        viewModelScope.launch {
            val formatted = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            Log.d(TAG, "Request elderId=$elderId, date=$formatted")

            _state.update { it.copy(loading = true, emptyDate = null) }
            try {
                val list = medicineRepository.getMedicineUiStateList(elderId, date)

                _state.update {
                    if (list.isEmpty()) {
                        it.copy(loading = false, items = emptyList(), emptyDate = date)
                    } else {
                        it.copy(loading = false, items = list, emptyDate = null)
                    }
                }
                Log.i(TAG, "Success elderId=$elderId, date=$formatted, items=${list.size}")
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        when (e.code()) {
                            404 -> {
                                // 미기록
                                Log.i(TAG, "No data (404) elderId=$elderId, date=$formatted")
                                _state.update { it.copy(loading = false, items = emptyList(), emptyDate = date) }
                            }
                            400 -> {
                                Log.w(TAG, "Bad request (400) elderId=$elderId, date=$formatted, msg=${e.message()}")
                                _state.update { it.copy(loading = false, items = emptyList(), emptyDate = date) }
                            }
                            401, 403 -> {
                                Log.w(TAG, "Unauthorized (${e.code()}) elderId=$elderId")
                                _state.update { it.copy(loading = false, items = emptyList(), emptyDate = date) }
                            }
                            else -> {
                                Log.e(TAG, "API error code=${e.code()} elderId=$elderId, date=$formatted", e)
                                _state.update { it.copy(loading = false, items = emptyList(), emptyDate = date) }
                            }
                        }
                    }
                    else -> {
                        Log.e(TAG, "Unexpected error elderId=$elderId, date=$formatted", e)
                        _state.update { it.copy(loading = false, items = emptyList(), emptyDate = date) }
                    }
                }
            }
        }
    }
}