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


    private val _meals = MutableStateFlow<List<MealUiState>>(emptyList())
    val meals: StateFlow<List<MealUiState>> = _meals

    private val elderId = 1


    fun loadMealsForDate(date: LocalDate) {
        viewModelScope.launch {
            try {
                val result = mealRepository.getMealUiStateList(elderId, date)
                _meals.value = result
            } catch (e: Exception) {
                // 오류 발생 시 '미기록' 상태로 UI를 초기화
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
}