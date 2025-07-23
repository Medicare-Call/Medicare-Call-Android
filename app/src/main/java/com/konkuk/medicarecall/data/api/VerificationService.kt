package com.konkuk.medicarecall.data.api

import com.konkuk.medicarecall.data.dto.request.PhoneNumberConfirmRequestDto
import com.konkuk.medicarecall.data.dto.request.CertificationCodeRequestDto
import com.konkuk.medicarecall.data.dto.response.VerificationResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface VerificationService {
    @POST("/verifications")
    suspend fun requestCertificationCode(
        @Body request: CertificationCodeRequestDto
    ): Response<Unit>

    @POST("/verifications/confirmation")
    suspend fun confirmPhoneNumber(
        @Body request: PhoneNumberConfirmRequestDto
    ): Response<VerificationResponseDto>
}