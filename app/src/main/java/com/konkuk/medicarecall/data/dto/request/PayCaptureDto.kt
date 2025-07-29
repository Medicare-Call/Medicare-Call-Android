package com.konkuk.medicarecall.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class PayCaptureRequestDto(
    val phone : String
)
