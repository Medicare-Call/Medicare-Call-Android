package com.konkuk.medicarecall.data.dto.response

data class VerificationResponseDto(
    val verified: Boolean,
    val message: String,
    val memberStatus: String,
    val nextAction: String,
    val token: String
)
