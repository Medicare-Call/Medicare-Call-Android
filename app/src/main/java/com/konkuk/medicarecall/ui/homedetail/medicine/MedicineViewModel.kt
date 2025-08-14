package com.konkuk.medicarecall.ui.homedetail.medicine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.homedetail.medicine.data.MedicineRepository
import com.konkuk.medicarecall.ui.homedetail.medicine.model.MedicineUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val medicineRepository: MedicineRepository
) : ViewModel() {
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
            } catch (_: Exception) {
                _state.update { it.copy(loading = false, items = emptyList(), emptyDate = date) }
            }
        }
    }

}