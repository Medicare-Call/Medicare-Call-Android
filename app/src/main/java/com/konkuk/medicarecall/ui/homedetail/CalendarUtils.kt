package com.konkuk.medicarecall.ui.homedetail

import java.time.LocalDate
/**
 * 페이지 인덱스를 기준으로 해당 주차(일~토)의 날짜 리스트를 반환한다.
 *
 * @param page 스와이프된 주차 인덱스 (0부터 시작)
 * @return 현재 주차의 날짜 리스트 (1~31 범위)
 *
 * 예: page = 0 ➜ 시작일로부터 0주 ➜ 1월 1일부터 해당 주의 일~토 날짜 계산
 */

fun getDatesForWeek(page: Int): List<Int> {
    val startDate = LocalDate.of(2025, 1, 1)
    val targetWeekStart = startDate.plusWeeks(page.toLong())
    return (0..6).map { offset ->
        targetWeekStart.plusDays(offset.toLong()).dayOfMonth
    }
}