package com.konkuk.medicarecall.ui.homedetail.meal.model

data class MealResponseDto(
    val date: String,
    val meals: MealData
)

data class MealData(
    val breakfast: String?, // null 허용
    val lunch: String?,
    val dinner: String?
)