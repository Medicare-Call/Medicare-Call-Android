package com.konkuk.medicarecall.ui.homedetail.meal.data

import com.konkuk.medicarecall.ui.homedetail.meal.model.MealUiState
import java.time.LocalDate
import javax.inject.Inject

class MealRepository @Inject constructor(
    private val mealApi: MealApi
) {
    suspend fun getMealUiStateList(guardianId: Int, date: LocalDate): List<MealUiState> {
        val response = mealApi.getDailyMeal(guardianId, date.toString())

        return listOf(
            MealUiState(
                mealTime = "아침",
                description = response.meals.breakfast ?: "식사 기록 전이에요.",
                isRecorded = response.meals.breakfast != null,
                isEaten = null // AI가 식사 여부 분석하면 추가 가능
            ),
            MealUiState(
                mealTime = "점심",
                description = response.meals.lunch ?: "식사 기록 전이에요.",
                isRecorded = response.meals.lunch != null,
                isEaten = null
            ),
            MealUiState(
                mealTime = "저녁",
                description = response.meals.dinner ?: "식사 기록 전이에요.",
                isRecorded = response.meals.dinner != null,
                isEaten = null
            )
        )
    }
}