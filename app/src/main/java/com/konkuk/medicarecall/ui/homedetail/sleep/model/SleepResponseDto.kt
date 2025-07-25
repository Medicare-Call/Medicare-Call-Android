package com.konkuk.medicarecall.ui.homedetail.sleep.model

data class SleepResponseDto(
    val date: String,
    val totalSleep: TotalSleepDto?,  // null이면 미기록으로 간주
    val sleepTime: String?,          // 취침 시간 (예: "22:12")
    val wakeTime: String?            // 기상 시간 (예: "06:00")
)

data class TotalSleepDto(
    val hours: Int?,
    val minutes: Int?
)