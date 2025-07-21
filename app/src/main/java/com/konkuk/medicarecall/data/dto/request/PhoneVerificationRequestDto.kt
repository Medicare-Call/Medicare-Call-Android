package com.konkuk.medicarecall.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class PhoneVerificationRequestDto(
    val phone: String
)
