package com.konkuk.medicarecall.data.repository

import com.konkuk.medicarecall.data.api.VerificationService
import com.konkuk.medicarecall.data.dto.request.CertificationCodeRequestDto
import com.konkuk.medicarecall.data.dto.request.PhoneNumberConfirmRequestDto
import com.konkuk.medicarecall.data.dto.response.VerificationResponseDto
import retrofit2.Response

class VerificationRepository(
    private val verificationService: VerificationService
) {
    suspend fun requestCertificationCode(phone: String): Result<Unit> {
        return try {
            val response =
                verificationService.requestCertificationCode(CertificationCodeRequestDto(phone))
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("코드 ${response.code()} 인증번호 보내기 실패"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    suspend fun confirmPhoneNumber(phone: String, code: String): Result<VerificationResponseDto> {
        return try {
            val response =
                verificationService.confirmPhoneNumber(PhoneNumberConfirmRequestDto(phone, code))
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Request failed with code ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

}