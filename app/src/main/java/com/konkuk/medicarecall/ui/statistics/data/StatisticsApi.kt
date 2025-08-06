package com.konkuk.medicarecall.ui.statistics.data

import com.konkuk.medicarecall.ui.statistics.model.StatisticsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface StatisticsApi {


    @GET("/elders/{elderId}/weekly-stats")
    suspend fun getStatistics(
        @Query("elderId") elderId: Int,
        @Query("startDate") startDate: String,
    ): StatisticsResponseDto
}