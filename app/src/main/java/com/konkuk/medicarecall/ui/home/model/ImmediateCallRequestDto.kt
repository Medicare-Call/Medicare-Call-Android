package com.konkuk.medicarecall.ui.home.model

import kotlinx.serialization.Serializable

@Serializable
data class ImmediateCallRequestDto(
    val elderId: Int,
    val careCallOption: String, // FIRST, SECOND, THIRD
)
