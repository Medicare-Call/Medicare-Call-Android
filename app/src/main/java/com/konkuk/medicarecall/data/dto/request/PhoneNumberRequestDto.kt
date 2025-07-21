package com.konkuk.medicarecall.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class PhoneNumberRequestDto(
    val phone: String
)
