package com.konkuk.medicarecall.ui.calendar

import java.time.LocalDate


/*data class CalendarUiState(
    val year: Int,          // 선택한 연도
    val month: Int,         // 선택한 월
    val weekDates: List<Int>, // 해당 주차 날짜 리스트 (예: [7,8,9,10,11,12,13])
    val selectedDate: Int   // 선택한 날짜 (1~31)
)*/


data class CalendarUiState(
    val currentYear: Int,               // 현재 연도
    val currentMonth: Int,              // 현재 월
    val weekDates: List<LocalDate>,     // 해당 주의 날짜 리스트
    val selectedDate: LocalDate         // 선택된 날짜 (1일~31일 아님, 실제 날짜)
)
