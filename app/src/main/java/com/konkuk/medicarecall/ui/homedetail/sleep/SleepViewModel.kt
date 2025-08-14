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


    private val _sleepState = MutableStateFlow(SleepUiState.EMPTY)
    val sleep: StateFlow<SleepUiState> = _sleepState


    fun loadSleepDataForDate(date: LocalDate) {
        viewModelScope.launch {
            _sleepState.value = sleepRepository.getSleepUiState(elderId = 1, date = date)
        }
    }
}