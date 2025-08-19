package com.konkuk.medicarecall.ui.homedetail.glucoselevel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.data.GlucoseRepository
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseTiming
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseUiState
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GraphDataPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class GlucoseViewModel @Inject constructor(
    private val glucoseRepository: GlucoseRepository
) : ViewModel() {

    private companion object {
        const val TAG = "GLUCOSE_API"
    }

    // UI State
    private val _uiState = MutableStateFlow(GlucoseUiState())
    val uiState: StateFlow<GlucoseUiState> = _uiState

    // 내부 캐시
    private var beforeMealData = mutableStateListOf<GraphDataPoint>()
    private var afterMealData = mutableStateListOf<GraphDataPoint>()

    fun getGlucoseData(
        elderId: Int,
        counter: Int,
        type: GlucoseTiming
    ) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            glucoseRepository.getGlucoseGraph(elderId, counter, type.toString())
                .onSuccess {
                    when (type) {
                        GlucoseTiming.BEFORE_MEAL -> {
                            beforeMealData.addAll(0, it.data.reversed().map { record ->
                                GraphDataPoint(
                                    date = LocalDate.parse(record.date),
                                    value = record.value.toFloat()
                                )
                            })
                            _uiState.value = _uiState.value.copy(graphDataPoints = beforeMealData)
                        }

                        GlucoseTiming.AFTER_MEAL -> {
                            afterMealData.addAll(0, it.data.reversed().map { record ->
                                GraphDataPoint(
                                    date = LocalDate.parse(record.date),
                                    value = record.value.toFloat()
                                )
                            })
                            _uiState.value = _uiState.value.copy(graphDataPoints = afterMealData)

                        }
                    }

                    _uiState.value = _uiState.value.copy(hasNext = it.hasNextPage)
                }
            _uiState.value = _uiState.value.copy(isLoading = false)

        }
    }


    /** 타이밍 전환*/
    fun updateTiming(newTiming: GlucoseTiming) {
        Log.d(TAG, "updateTiming(newTiming=$newTiming)")
        val dataToShow =
            if (newTiming == GlucoseTiming.BEFORE_MEAL) beforeMealData else afterMealData
        _uiState.value = _uiState.value.copy(
            graphDataPoints = dataToShow,
            selectedTiming = newTiming,
            hasNext = true
        )
    }


}