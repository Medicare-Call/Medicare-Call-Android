package com.konkuk.medicarecall.ui.homedetail.meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.homedetail.meal.data.MealRepository
import com.konkuk.medicarecall.ui.homedetail.meal.model.MealUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealRepository: MealRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _meals = MutableStateFlow<List<MealUiState>>(emptyList())
    val meals: StateFlow<List<MealUiState>> = _meals

    private val guardianId = 1 // 임시 하드코딩된 사용자 ID (로그인 기능 연동되면 대체)

    init {
        fetchMealData(_selectedDate.value) // 오늘 날짜 기준 데이터 불러오기
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date // 날짜 상태 갱신
        fetchMealData(date) // 선택한 날짜에 맞는 식사 데이터 불러오기
    }


    private fun fetchMealData(date: LocalDate) {
        viewModelScope.launch {
            try {
                val result = mealRepository.getMealUiStateList(guardianId, date)
                _meals.value = result // 받아온 데이터를 UI 상태로 저장
            } catch (e: Exception) {

                // 네트워크 오류 등 예외 발생 시 기본 '미기록 상태' 표시
                _meals.value = listOf("아침", "점심", "저녁").map {
                    MealUiState(
                        mealTime = it,
                        description = "식사 기록 전이에요.",
                        isRecorded = false,
                        isEaten = null
                    )
                }
            }
        }
    }

    // 주간 달력 표시용: 선택한 날짜 기준으로 주간 날짜 계산
    fun getCurrentWeekDates(): List<LocalDate> {
        val selected = _selectedDate.value
        val dayOfWeek = selected.dayOfWeek.value % 7 // 일요일 = 0
        val sunday = selected.minusDays(dayOfWeek.toLong())
        return (0..6).map { sunday.plusDays(it.toLong()) }
    }
}
