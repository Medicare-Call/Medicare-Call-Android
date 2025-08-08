package com.konkuk.medicarecall.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class MemberTokenResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: Long,
)
