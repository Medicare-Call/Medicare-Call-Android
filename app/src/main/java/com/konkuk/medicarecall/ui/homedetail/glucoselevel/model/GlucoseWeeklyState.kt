package com.konkuk.medicarecall.ui.homedetail.glucoselevel.model

data class GlucoseWeeklyState(
    val beforeMealGraph: List<Int>,   // 주간 공복 혈당 그래프 데이터
    val afterMealGraph: List<Int>,    // 주간 식후 혈당 그래프 데이터
    val weekLabels: List<String>
)


data class GlucoseUiState(
    val weeklyData: List<GlucoseWeeklyState> = emptyList(), // 주차별 혈당 데이터들
    val hasNext: Boolean = false,  // 다음 페이지 여부 (무한 스크롤)
    val isLoading: Boolean = false
)