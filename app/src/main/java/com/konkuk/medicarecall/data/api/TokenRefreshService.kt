package com.konkuk.medicarecall.data.api

import com.konkuk.medicarecall.data.dto.request.TokenRefreshRequestDto
import com.konkuk.medicarecall.data.dto.response.MemberTokenResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenRefreshService {
    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body request: TokenRefreshRequestDto
    ): Response<MemberTokenResponseDto>
}