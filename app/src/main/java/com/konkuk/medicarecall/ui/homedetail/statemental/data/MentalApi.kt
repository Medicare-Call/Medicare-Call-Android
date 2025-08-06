package com.konkuk.medicarecall.ui.homedetail.statemental.data


import com.konkuk.medicarecall.ui.homedetail.statemental.model.MentalResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MentalApi {
    @GET("/elders/{elderId}/mental-analysis?date=2025-07-16")
    suspend fun getDailyMental(
        @Query("guardianId") guardianId: Int,
        @Query("date") date: String
    ): MentalResponseDto
}