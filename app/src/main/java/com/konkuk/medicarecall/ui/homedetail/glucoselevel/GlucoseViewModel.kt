package com.konkuk.medicarecall.ui.homedetail.glucoselevel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.data.GlucoseRepository
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseTiming
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseType
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseUiState
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GraphDataPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class GlucoseViewModel @Inject constructor(
    private val glucoseRepository: GlucoseRepository
) : ViewModel() {

    private companion object {
        const val TAG = "GLUCOSE_API"
    }

    // UI State
    private val _uiState = mutableStateOf(GlucoseUiState())
    val uiState: State<GlucoseUiState> = _uiState

    // 내부 캐시
    private var beforeMealData: List<GraphDataPoint> = emptyList()
    private var afterMealData:  List<GraphDataPoint> = emptyList()

    /**
     * 📌 주간 데이터 로드
     */
    fun loadWeekFromServer(
        elderId: Int,
        startDate: LocalDate = LocalDate.now()
    ) {
        viewModelScope.launch {



            val start = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
            Log.d(TAG, "Request elderId=$elderId, start=$start")

            // BEFORE_MEAL
            beforeMealData = loadGraphData(elderId, start, GlucoseType.BEFORE_MEAL)

            // AFTER_MEAL
            afterMealData  = loadGraphData(elderId, start, GlucoseType.AFTER_MEAL)

            // 현재 선택된 타이밍 기준으로 UI 갱신
            val currentTiming = _uiState.value.selectedTiming
            val dataToShow = if (currentTiming == GlucoseTiming.BEFORE_MEAL) beforeMealData else afterMealData
            _uiState.value = _uiState.value.copy(graphDataPoints = dataToShow)

            Log.i(TAG, "Loaded elderId=$elderId, before=${beforeMealData.size}, after=${afterMealData.size}, show=${dataToShow.size}")
        }
    }

    /**
     * 📌 API 호출 & 데이터 정제
     */
    private suspend fun loadGraphData(
        elderId: Int,
        start: String,
        type: GlucoseType
    ): List<GraphDataPoint> {
        return try {
            val dto = glucoseRepository.getGlucoseGraph(elderId, start, type)
            Log.d(TAG, "$type raw response = $dto")

            dto.data
                .sortedBy { it.date }       // 날짜순 정렬
                .distinctBy { it.date }     // 중복 제거
                .map { day ->
                    GraphDataPoint(
                        date = LocalDate.parse(day.date),
                        value = day.value.toFloat()
                    )
                }
                .also { Log.d(TAG, "$type mapped points = $it") }

        } catch (e: Exception) {
            when (e) {
                is HttpException -> when (e.code()) {
                    404 -> Log.i(TAG, "No $type data (404)")
                    400 -> Log.w(TAG, "Bad $type request (400): ${e.message()}")
                    else -> Log.e(TAG, "$type API error code=${e.code()}", e)
                }
                else -> Log.e(TAG, "$type unexpected error", e)
            }
            emptyList()
        }
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

}