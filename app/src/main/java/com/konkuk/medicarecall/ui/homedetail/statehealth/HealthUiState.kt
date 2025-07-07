package com.konkuk.medicarecall.ui.homedetail.statehealth

data class HealthUiState(
    val healthSummary: String,   //건강 징후 요약
    val symptomAnalysis: String, //증상 분석
    val isRecorded: Boolean,     //기록 여부
)
