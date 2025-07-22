package com.konkuk.medicarecall.data.api

import com.konkuk.medicarecall.data.dto.request.PhoneNumberConfirmRequestDto
import com.konkuk.medicarecall.data.dto.request.CertificationCodeRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface VerificationService {
    @POST("/verifications")
    suspend fun requestCertificationCode(
        @Body request: CertificationCodeRequestDto
    )

    @POST("/verifications/confirmation")
    suspend fun confirmPhoneNumber(
        @Body request: PhoneNumberConfirmRequestDto
    )
}