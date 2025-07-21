package com.konkuk.medicarecall.data.api

import com.konkuk.medicarecall.data.dto.request.ConfirmRequestDto
import com.konkuk.medicarecall.data.dto.request.PhoneNumberRequestDto
import com.konkuk.medicarecall.data.dto.response.VerificationResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface VerificationService {
    @POST("/verifications")
    suspend fun phoneOnly(
        @Body request: PhoneNumberRequestDto
    )

    @POST("/verifications/confirmation")
    suspend fun phoneConfirm(
        @Body request: ConfirmRequestDto
    )
}