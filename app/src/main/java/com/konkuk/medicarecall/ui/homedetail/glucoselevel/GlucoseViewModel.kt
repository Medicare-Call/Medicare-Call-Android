package com.konkuk.medicarecall.ui.homedetail.glucoselevel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseTiming
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseUiState
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GraphDataPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class GlucoseViewModel @Inject constructor(
    // private val glucoseRepository: GlucoseRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(GlucoseUiState())
    val uiState: State<GlucoseUiState> = _uiState

    private var beforeMealData: List<GraphDataPoint> = emptyList()
    private var afterMealData: List<GraphDataPoint> = emptyList()

    fun loadDummyWeek() {
        val today = LocalDate.now()

        // '공복' 상태일 때 그래프에 보여줄 14일치 가상 데이터
        beforeMealData = (0..13).map { i ->
            GraphDataPoint(date = today.minusDays(i.toLong()), value = (70..130).random().toFloat())
        }.reversed()

        // '식후'는 Empty View 테스트
        afterMealData = emptyList()

        // 초기 화면을 beforeMealData로 설정
        _uiState.value = GlucoseUiState(
            graphDataPoints = beforeMealData,
            selectedTiming = GlucoseTiming.BEFORE_MEAL
        )
    }

    fun updateTiming(newTiming: GlucoseTiming) {
        // '공복' 또는 '식후' 버튼이 눌렸을 때 어떤 데이터를 보여줄지 결정
        val dataToShow = if (newTiming == GlucoseTiming.BEFORE_MEAL) {
            beforeMealData
        } else {
            afterMealData
        }
        _uiState.value = _uiState.value.copy(
            graphDataPoints = dataToShow,
            selectedTiming = newTiming
        )
    }
}