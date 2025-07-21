package com.konkuk.medicarecall.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmRequestDto(
    val phone: String,
    val certificationCode: String
)
