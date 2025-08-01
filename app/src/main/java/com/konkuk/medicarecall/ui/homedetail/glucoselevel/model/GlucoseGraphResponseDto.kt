package com.konkuk.medicarecall.ui.homedetail.glucoselevel.model

data class GlucoseGraphResponseDto(
    val period: PeriodDto,
    val data: List<GlucoseDayValueDto>,
    val average: GlucoseStatDto?,  // 하루 평균 (nullable)
    val latest: GlucoseStatDto?    // 가장 최근 값 (nullable)
)

data class PeriodDto(
    val startDate: String,  // 조회 시작일 (yyyy-MM-dd)
    val endDate: String     // 조회 종료일 (yyyy-MM-dd)
)

data class GlucoseDayValueDto(
    val date: String,        // 측정 날짜 (yyyy-MM-dd)
    val value: Int,          // 혈당 수치 (mg/dL)
    val status: GlucoseStatus  // LOW, NORMAL, HIGH
)

data class GlucoseStatDto(
    val value: Int,              // 수치
    val status: GlucoseStatus    // LOW, NORMAL, HIGH
)

enum class GlucoseStatus {
    LOW, NORMAL, HIGH
}
