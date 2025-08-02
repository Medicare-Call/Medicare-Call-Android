package com.konkuk.medicarecall.ui.homedetail.statehealth.model

data class HealthResponseDto(
    val date: String,
    val symptomList: List<String>,
    val analysisComment: String?
)