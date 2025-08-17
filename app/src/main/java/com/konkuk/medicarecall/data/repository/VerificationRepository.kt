package com.konkuk.medicarecall.data.repository

import android.util.Log
import com.konkuk.medicarecall.data.api.VerificationService
import com.konkuk.medicarecall.data.dto.request.CertificationCodeRequestDto
import com.konkuk.medicarecall.data.dto.request.PhoneNumberConfirmRequestDto
import com.konkuk.medicarecall.data.dto.response.VerificationResponseDto
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class VerificationRepository @Inject constructor(
    private val verificationService: VerificationService
) {
    suspend fun requestCertificationCode(phone: String) =
        runCatching { verificationService.requestCertificationCode(CertificationCodeRequestDto(phone)) }


    suspend fun confirmPhoneNumber(phone: String, code: String): Result<VerificationResponseDto> =
        runCatching {
            val response = verificationService.confirmPhoneNumber(
                PhoneNumberConfirmRequestDto(phone, code)
            )

            if (response.isSuccessful) {
                response.body() ?: throw IllegalStateException("Response body is null")
            } else {
                throw HttpException(response)
            }
        }


}