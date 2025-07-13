package com.konkuk.medicarecall.ui.homedetail


data class CalendarUiState(
    val year: Int,          // 선택한 연도
    val month: Int,         // 선택한 월
    val weekDates: List<Int>, // 해당 주차 날짜 리스트 (예: [7,8,9,10,11,12,13])
    val selectedDate: Int   // 선택한 날짜 (1~31)
)
