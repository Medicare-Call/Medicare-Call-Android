package com.konkuk.medicarecall.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.home.data.HomeApi
import com.konkuk.medicarecall.ui.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeApi: HomeApi
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _homeUiState = MutableStateFlow(HomeUiState.EMPTY)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState


    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        fetchHomeSummary(date)
    }

    fun fetchHomeSummary(date: LocalDate = _selectedDate.value) {
        viewModelScope.launch {
            try {
                val response = homeApi.getHomeSummary(
                    elderId = 1, // 실제 elderId를 넘겨야함
                    //date = date.toString()
                )

                val uiState = HomeUiState.from(response)
                _homeUiState.value = uiState

            } catch (e: Exception) {
                // 실패 시 null 또는 에러 상태 처리
                _homeUiState.value = HomeUiState.EMPTY
            }
        }
    }
}