package com.konkuk.medicarecall.data.api

import com.konkuk.medicarecall.data.dto.request.ElderRegisterRequestDto
import com.konkuk.medicarecall.data.dto.response.EldersInfoResponseDto
import com.konkuk.medicarecall.data.dto.response.EldersSubscriptionResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EldersInfoService {
    @GET("elders")
    suspend fun getElders(): Response<List<EldersInfoResponseDto>>

    @GET("elders/subscriptions")
    suspend fun getSubscriptions(): Response<EldersSubscriptionResponseDto>

    @POST("elders/{elderId}")
    suspend fun updateElder(
        @Path("elderId") elderId: Int,
        @Body request: ElderRegisterRequestDto
    ): Response<Unit>

}