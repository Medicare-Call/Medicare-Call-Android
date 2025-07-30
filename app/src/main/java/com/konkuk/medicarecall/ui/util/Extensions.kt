package com.konkuk.medicarecall.ui.util

fun List<Int>.averageOrNull(): Double? {
    return if (this.isEmpty()) null else this.average()
}