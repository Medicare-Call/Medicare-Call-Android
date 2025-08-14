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


    private companion object {
        const val ELDER_ID = 1 // 테스트용
    }

    private val _mental = MutableStateFlow(MentalUiState.EMPTY)
    val mental: StateFlow<MentalUiState> = _mental

    fun loadMentalDataForDate(date: LocalDate) {
        viewModelScope.launch {
            try {
                _mental.value = mentalRepository.getMentalUiState(
                    elderId = ELDER_ID,
                    date = date
                )
            } catch (_: Exception) {
                // 네트워크/파싱 실패 시에도 '미기록'으로 보여주기
                _mental.value = MentalUiState(
                    mentalSummary = listOf("건강징후 기록 전이에요."),
                    isRecorded = false
                )
            }
        }
    }
}