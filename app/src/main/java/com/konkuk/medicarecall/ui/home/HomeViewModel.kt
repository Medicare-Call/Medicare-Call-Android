package com.konkuk.medicarecall.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.home.data.HomeRepository
import com.konkuk.medicarecall.ui.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private companion object {
        const val TAG = "HOME_API"
        const val ELDER_ID = 1 // 테스트용
    }

    private val _homeUiState = MutableStateFlow(HomeUiState.EMPTY)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        fetchHomeSummaryForToday()
    }

    private fun fetchHomeSummaryForToday() {
        viewModelScope.launch {
            val today = LocalDate.now()
            val formatted = today.format(DateTimeFormatter.ISO_LOCAL_DATE)
            Log.d(TAG, "Request elderId=$ELDER_ID, date=$formatted")

            try {
                val uiState = homeRepository.getHomeUiState(ELDER_ID, today)
                _homeUiState.value = uiState
                Log.i(TAG, "Success elderId=$ELDER_ID, date=$formatted")
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        when (e.code()) {
                            404 -> {
                                // 미기록
                                Log.i(TAG, "No data (404) elderId=$ELDER_ID, date=$formatted")
                                _homeUiState.value = HomeUiState.EMPTY
                            }
                            400 -> {
                                Log.w(TAG, "Bad request (400) elderId=$ELDER_ID, date=$formatted, msg=${e.message()}")
                                _homeUiState.value = HomeUiState.EMPTY
                            }
                            401, 403 -> {
                                Log.w(TAG, "Unauthorized (${e.code()}) elderId=$ELDER_ID")
                                _homeUiState.value = HomeUiState.EMPTY
                            }
                            else -> {
                                Log.e(TAG, "API error code=${e.code()} elderId=$ELDER_ID, date=$formatted", e)
                                _homeUiState.value = HomeUiState.EMPTY
                            }
                        }
                    }
                    else -> {
                        Log.e(TAG, "Unexpected error elderId=$ELDER_ID, date=$formatted", e)
                        _homeUiState.value = HomeUiState.EMPTY
                    }
                }
            }
        }
    }
}