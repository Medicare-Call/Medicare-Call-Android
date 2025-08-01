package com.konkuk.medicarecall.ui.homedetail.glucoselevel.model

import java.time.LocalDate

data class GraphDataPoint(
    val date: LocalDate, // x축에 표시될 날짜 라벨 (예: "8.3")
    val value: Float   // y축 혈당 값
)
