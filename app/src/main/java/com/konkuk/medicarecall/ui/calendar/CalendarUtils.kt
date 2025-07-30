package com.konkuk.medicarecall.ui.calendar

import java.time.DayOfWeek
import java.time.LocalDate
/**
 * 페이지 인덱스를 기준으로 해당 주차(일~토)의 날짜 리스트를 반환한다.
 *
 * @param page 스와이프된 주차 인덱스 (0부터 시작)
 * @return 현재 주차의 날짜 리스트 (1~31 범위)
 *
 * 예: page = 0 ➜ 시작일로부터 0주 ➜ 1월 1일부터 해당 주의 일~토 날짜 계산
 */

fun getDatesForWeek(page: Int): List<LocalDate> {
    val startDate = LocalDate.of(2025, 1, 1)
    val targetWeekStart = startDate.plusWeeks(page.toLong())
    return (0..6).map { targetWeekStart.plusDays(it.toLong()) }
}


//특정 연월에 대한 주차 단위 날짜 리스트 반환하는 함수 정의
fun getWeeksForMonth(year: Int, month: Int): List<List<LocalDate>> {
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth())

    var startOfWeek = firstDayOfMonth
        .with(DayOfWeek.SUNDAY)
        .minusDays(if (firstDayOfMonth.dayOfWeek != DayOfWeek.SUNDAY) 7 else 0)

    val weeks = mutableListOf<List<LocalDate>>()

    while (startOfWeek <= lastDayOfMonth) {
        val week = (0..6).map { offset ->
            startOfWeek.plusDays(offset.toLong())
        }
        weeks.add(week)
        startOfWeek = startOfWeek.plusWeeks(1)
    }

    return weeks
}


fun getWeekDatesFromDate(date: LocalDate): List<LocalDate> {
    val startOfWeek = date.with(java.time.DayOfWeek.SUNDAY)
    return List(7) { startOfWeek.plusDays(it.toLong()) }
}
