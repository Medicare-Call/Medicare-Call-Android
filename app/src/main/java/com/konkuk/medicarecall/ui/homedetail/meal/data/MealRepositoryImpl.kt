package com.konkuk.medicarecall.ui.homedetail.meal.data

import com.konkuk.medicarecall.ui.homedetail.meal.model.MealUiState
import java.time.LocalDate
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mealApi: MealApi
) : MealRepository {
    override suspend fun getMealUiStateList(elderId: Int, date: LocalDate): List<MealUiState> {

        val response = mealApi.getDailyMeal(elderId, date.toString())

        return listOf(
            MealUiState(
                mealTime = "아침",
                description = response.meals.breakfast ?: "식사 기록 전이에요.",
                isRecorded = response.meals.breakfast != null,
                isEaten = null
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