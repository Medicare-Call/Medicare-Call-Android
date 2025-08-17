package com.konkuk.medicarecall.ui.homedetail.statemental.data


import com.konkuk.medicarecall.ui.homedetail.statemental.model.MentalResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MentalApi {
    @GET("/elders/{elderId}/mental-analysis")
    suspend fun getDailyMental(
        @Path("elderIdId") elderIdId: Int,
        @Query("date") date: String
    ): retrofit2.Response<MentalResponseDto>
}