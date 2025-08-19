package com.konkuk.medicarecall.data.api

import com.konkuk.medicarecall.data.dto.response.EldersInfoResponseDto
import com.konkuk.medicarecall.data.dto.response.EldersHealthResponseDto
import com.konkuk.medicarecall.data.dto.response.EldersSubscriptionResponseDto
import com.konkuk.medicarecall.data.dto.response.MyInfoResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface SettingService {
    // 설정 불러오기
    @GET("member")
    suspend fun getMyInfo(): Response<MyInfoResponseDto>

    // 내 정보 수정
    @POST("member")
    suspend fun updateMyInfo(
        @Body userUpdateRequestDto: MyInfoResponseDto
    ) : Response<MyInfoResponseDto> // 추후 수정 필요

    @POST("auth/logout")
    suspend fun logout(
        @Header("Authorization") authorization: String // "Bearer <refresh>"
    ): Response<Unit>
}