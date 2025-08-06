package com.konkuk.medicarecall.ui.homedetail.sleep.data

import com.konkuk.medicarecall.ui.homedetail.sleep.model.SleepResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SleepApi {
    @GET("/elders/{elderId}/sleep?date=2025-07-16")
    suspend fun getDailySleep(
        @Query("guardianId") guardianId: Int,
        @Query("date") date: String
    ): SleepResponseDto
}