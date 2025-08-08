package com.konkuk.medicarecall.data.repository

import com.konkuk.medicarecall.data.api.MemberRegisterService
import com.konkuk.medicarecall.data.dto.request.MemberRegisterRequestDto
import com.konkuk.medicarecall.data.dto.response.MemberRegisterResponseDto
import com.konkuk.medicarecall.ui.model.GenderType
import javax.inject.Inject

class MemberRegisterRepository @Inject constructor(
    private val memberRegisterService: MemberRegisterService
) {
    suspend fun registerMember(
        name: String,
        birthDate: String,
        gender: GenderType
    ): Result<MemberRegisterResponseDto> =
        runCatching {
            val response = memberRegisterService.postMemberRegister(
                MemberRegisterRequestDto(
                    name,
                    birthDate,
                    gender
                )
            )

            if (response.isSuccessful) {
                response.body() ?: throw IllegalStateException("Response body is null")
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                throw Exception("Request failed with code ${response.code()}: $errorBody")
            }
        }

}