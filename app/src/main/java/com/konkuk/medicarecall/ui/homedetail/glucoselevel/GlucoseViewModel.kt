package com.konkuk.medicarecall.ui.homedetail.glucoselevel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.data.GlucoseRepository
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseTiming
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseUiState
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GraphDataPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class GlucoseViewModel @Inject constructor(
    private val glucoseRepository: GlucoseRepository
) : ViewModel() {

    private companion object {
        const val TAG = "GLUCOSE_API"
        const val ELDER_ID = 1 // 테스트용
    }

    private val _uiState = mutableStateOf(GlucoseUiState())
    val uiState: State<GlucoseUiState> = _uiState

    private var beforeMealData: List<GraphDataPoint> = emptyList()
    private var afterMealData: List<GraphDataPoint> = emptyList()

    /** 더미 주간 데이터 로드*/
    fun loadDummyWeek() {
        val today = LocalDate.now()
        Log.d(TAG, "loadDummyWeek() elderId=$ELDER_ID, today=$today")

        // '공복' 14일치 가상 데이터
        beforeMealData = (0..13).map { i ->
            GraphDataPoint(date = today.minusDays(i.toLong()), value = (70..130).random().toFloat())
        }.reversed()

        // '식후'는 Empty View 테스트
        afterMealData = emptyList()
        Log.i(TAG, "Dummy generated: before=${beforeMealData.size}, after=${afterMealData.size}")

        // 초기 화면은 공복 데이터
        _uiState.value = GlucoseUiState(
            graphDataPoints = beforeMealData,
            selectedTiming = GlucoseTiming.BEFORE_MEAL
        )
    }

    /** 타이밍 전환*/
    fun updateTiming(newTiming: GlucoseTiming) {
        Log.d(TAG, "updateTiming(newTiming=$newTiming)")
        val dataToShow = if (newTiming == GlucoseTiming.BEFORE_MEAL) beforeMealData else afterMealData
        _uiState.value = _uiState.value.copy(
            graphDataPoints = dataToShow,
            selectedTiming = newTiming
        )
    }

    // --------------------------------------------------------------------
    // 🔽🔽 실제 API 붙일 때 사용할 404/400 로그/분기 템플릿 (지금은 주석으로만 제공)
    // --------------------------------------------------------------------
    /*
    fun loadWeekFromServer(startDate: LocalDate) {
        viewModelScope.launch {
            val formatted = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
            Log.d(TAG, "Request elderId=$ELDER_ID, start=$formatted")

            try {

                val before = glucoseRepository.getWeeklyGlucose(
                    elderId = ELDER_ID,
                    startDate = startDate,
                    timing = GlucoseTiming.BEFORE_MEAL
                )
                val after = glucoseRepository.getWeeklyGlucose(
                    elderId = ELDER_ID,
                    startDate = startDate,
                    timing = GlucoseTiming.AFTER_MEAL
                )

                beforeMealData = before
                afterMealData = after

                // 현재 선택된 타이밍 유지하여 렌더
                val currentTiming = _uiState.value.selectedTiming
                val show = if (currentTiming == GlucoseTiming.AFTER_MEAL) afterMealData else beforeMealData
                _uiState.value = _uiState.value.copy(graphDataPoints = show)

                Log.i(TAG, "Success elderId=$ELDER_ID, start=$formatted, before=${before.size}, after=${after.size}")
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> when (e.code()) {
                        404 -> {
                            // 미기록
                            Log.i(TAG, "No data (404) elderId=$ELDER_ID, start=$formatted")
                            beforeMealData = emptyList()
                            afterMealData = emptyList()
                            _uiState.value = _uiState.value.copy(graphDataPoints = emptyList())
                        }
                        400 -> {
                            Log.w(TAG, "Bad request (400) elderId=$ELDER_ID, start=$formatted, msg=${e.message()}")
                            beforeMealData = emptyList(); afterMealData = emptyList()
                            _uiState.value = _uiState.value.copy(graphDataPoints = emptyList())
                        }
                        401, 403 -> {
                            Log.w(TAG, "Unauthorized (${e.code()}) elderId=$ELDER_ID")
                            beforeMealData = emptyList(); afterMealData = emptyList()
                            _uiState.value = _uiState.value.copy(graphDataPoints = emptyList())
                        }
                        else -> {
                            Log.e(TAG, "API error code=${e.code()} elderId=$ELDER_ID, start=$formatted", e)
                            beforeMealData = emptyList(); afterMealData = emptyList()
                            _uiState.value = _uiState.value.copy(graphDataPoints = emptyList())
                        }
                    }
                    else -> {
                        Log.e(TAG, "Unexpected error elderId=$ELDER_ID, start=$formatted", e)
                        beforeMealData = emptyList(); afterMealData = emptyList()
                        _uiState.value = _uiState.value.copy(graphDataPoints = emptyList())
                    }
                }
            }
        }
    }
    */
}