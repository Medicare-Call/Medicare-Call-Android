package com.konkuk.medicarecall.ui.homedetail.statehealth.data

import com.konkuk.medicarecall.ui.homedetail.statehealth.model.HealthResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HealthApi {
    @GET("view/dailyHealthAnalysis")
    suspend fun getDailyHealth(
        @Query("guardianId") guardianId: Int,
        @Query("date") date: String
    ): HealthResponseDto
}