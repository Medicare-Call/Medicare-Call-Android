package com.konkuk.medicarecall.ui.homedetail.sleep

data class SleepUiState(
    val totalSleepHours: Double, //총 수면 시간
    val bedtime: String,         //취침 시각
    val wakeUpTime: String,      //기상 시각
    val isRecorded: Boolean,     //기록 여부
)
