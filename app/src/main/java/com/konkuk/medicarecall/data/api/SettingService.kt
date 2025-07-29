package com.konkuk.medicarecall.data.api

import com.konkuk.medicarecall.data.dto.request.UserUpdateRequestDto
import com.konkuk.medicarecall.data.dto.response.MyInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface SettingService {
    @GET("me")
    suspend fun getMyInfo(): Response<MyInfoResponse>

    @PATCH("me")
    suspend fun updateMyInfo(
        @Body userUpdateRequestDto: UserUpdateRequestDto
    ) : Response<MyInfoResponse>

    @GET("elders/settings")
    suspend fun getElderSettings(): Response<Map<String, Any>>
}