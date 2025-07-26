package com.konkuk.medicarecall.ui.homedetail.glucoselevel.model

data class GlucoseWeeklyState(
    val beforeMealGraph: List<Int>,   // 주간 공복 혈당 그래프 데이터
    val afterMealGraph: List<Int>,    // 주간 식후 혈당 그래프 데이터
    val weekLabels: List<String>
)