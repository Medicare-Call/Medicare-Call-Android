package com.konkuk.medicarecall.ui.homedetail.meal.data


import com.konkuk.medicarecall.ui.homedetail.meal.model.MealResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("/api/view/dailyMeal")
    suspend fun getDailyMeal(
        @Query("guardianId") guardianId: Int,
        @Query("date") date: String
    ): MealResponseDto
}