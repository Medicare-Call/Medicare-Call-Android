package com.konkuk.medicarecall.data.api

import com.konkuk.medicarecall.data.dto.request.CertificationCodeRequestDto
import com.konkuk.medicarecall.data.dto.request.MemberRegisterRequestDto
import com.konkuk.medicarecall.data.dto.request.PhoneNumberConfirmRequestDto
import com.konkuk.medicarecall.data.dto.response.MemberRegisterResponseDto
import com.konkuk.medicarecall.data.dto.response.VerificationResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MemberRegisterService {
    @POST("members")
    suspend fun postMemberRegister(
        @Header("Authorization") header: String,
        @Body request: MemberRegisterRequestDto
    ): Response<MemberRegisterResponseDto>
}