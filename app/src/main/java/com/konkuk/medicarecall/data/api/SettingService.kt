package com.konkuk.medicarecall.data.api

import com.konkuk.medicarecall.data.dto.request.UserUpdateRequestDto
import com.konkuk.medicarecall.data.dto.response.DelElderInfoResponseDto
import com.konkuk.medicarecall.data.dto.response.EldersHealthResponseDto
import com.konkuk.medicarecall.data.dto.response.EldersInfoResponseDto
import com.konkuk.medicarecall.data.dto.response.EldersSubscriptionResponseDto
import com.konkuk.medicarecall.data.dto.response.MyInfoResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SettingService {
    // 설정 불러오기
    @GET("me")
    suspend fun getMyInfo(): Response<MyInfoResponseDto>

    // 내 정보 수정
    @PATCH("me")
    suspend fun updateMyInfo(
        @Body userUpdateRequestDto: UserUpdateRequestDto
    ) : Response<MyInfoResponseDto> // 추후 수정 필요

    // 노인 개인 정보 불러오기
    @GET("elders/settings")
    suspend fun getEldersPersonalInfo(): Response<EldersInfoResponseDto>

    // 노인 개인정보 삭제
    @DELETE("elders/{elderId}/settings")
    suspend fun deleteElderSettings(
        @Path("elderId") elderId: Int,
    ): Response<DelElderInfoResponseDto>

    // 노인 건강 정보 불러오기
    @GET("elders/health")
    suspend fun getEldersHealthInfo(): Response<EldersHealthResponseDto>
}