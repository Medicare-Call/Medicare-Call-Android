package com.konkuk.medicarecall.ui.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun List<Int>.averageOrNull(): Double? {
    return if (this.isEmpty()) null else this.average()
}

fun String.formatAsDate(): String? {
    // String 자체가 "yyyyMMdd" 형식이므로, this를 사용합니다.
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val date = LocalDate.parse(this, inputFormatter)

        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        date.format(outputFormatter)
    } catch (e: DateTimeParseException) {
        // 형식이 잘못된 경우 null 반환
        null
    }
}