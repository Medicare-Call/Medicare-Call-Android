package com.konkuk.medicarecall.data.api

import com.konkuk.medicarecall.data.dto.request.UserUpdateRequestDto
import com.konkuk.medicarecall.data.dto.response.DelElderInfoResponseDto
import com.konkuk.medicarecall.data.dto.response.EldersInfoResponse
import com.konkuk.medicarecall.data.dto.response.MyInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SettingService {
    @GET("me")
    suspend fun getMyInfo(): Response<MyInfoResponse>

    @PATCH("me")
    suspend fun updateMyInfo(
        @Body userUpdateRequestDto: UserUpdateRequestDto
    ) : Response<MyInfoResponse> // 추후 수정 필요

    @GET("elders/settings")
    suspend fun getElderSettings(): Response<EldersInfoResponse>

    @DELETE("elders/{elderId}/settings")
    suspend fun deleteElderSettings(
        @Path("elderId") elderId: Int,
    ): Response<DelElderInfoResponseDto>

}