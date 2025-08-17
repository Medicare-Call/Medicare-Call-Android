package com.konkuk.medicarecall.ui.home.data

import com.konkuk.medicarecall.ui.home.model.HomeResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {
    @GET("/api/elders/{elderId}/home")
    suspend fun getHomeSummary(
        @Query("elderId") elderId: Int
        //@Query("date") date: String
    ): HomeResponseDto
}