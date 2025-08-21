package com.konkuk.medicarecall.ui.homedetail.statehealth.model

import kotlinx.serialization.Serializable

@Serializable
data class HealthResponseDto(
    val date: String,
    val symptomList: List<String>? = null,
    val analysisComment: String? = null
)