package com.konkuk.medicarecall.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.home.data.HomeRepository
import com.konkuk.medicarecall.ui.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState.EMPTY)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        fetchHomeSummaryForToday()
    }
    private fun fetchHomeSummaryForToday() {
        viewModelScope.launch {

            val uiState = homeRepository.getHomeUiState(1, LocalDate.now())
            _homeUiState.value = uiState
        }
    }
}