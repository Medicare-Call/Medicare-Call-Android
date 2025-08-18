package com.konkuk.medicarecall.ui.homedetail.statehealth.data

import com.konkuk.medicarecall.ui.homedetail.statehealth.model.HealthResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HealthApi {
    @GET("elders/{elderId}/health-analysis")
    suspend fun getDailyHealth(
        @Path("elderIdId") elderIdId: Int,
        @Query("date") date: String
    ): HealthResponseDto
}