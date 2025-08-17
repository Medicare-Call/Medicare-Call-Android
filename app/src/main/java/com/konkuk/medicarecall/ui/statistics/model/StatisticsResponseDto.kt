package com.konkuk.medicarecall.ui.statistics.model

import com.google.gson.annotations.SerializedName

data class StatisticsResponseDto(
    @SerializedName("elderName")
    val elderName: String,
    @SerializedName("summaryStats")
    val summaryStats: SummaryStatsDto,
    @SerializedName("mealStats")
    val mealStats: MealStatsDto,
    @SerializedName("medicationStats")
    val medicationStats: Map<String, MedicationStatDto>,
    @SerializedName("healthSummary")
    val healthSummary: String,
    @SerializedName("averageSleep")
    val averageSleep: AverageSleepDto,
    @SerializedName("psychSummary")
    val psychSummary: PsychSummaryDto,
    @SerializedName("bloodSugar")
    val bloodSugar: BloodSugarDto
)

data class SummaryStatsDto(
    @SerializedName("mealRate")
    val mealRate: Int,
    @SerializedName("medicationRate")
    val medicationRate: Int,
    @SerializedName("healthSignals")
    val healthSignals: Int,
    @SerializedName("missedCalls")
    val missedCalls: Int
)

data class MealStatsDto(
    @SerializedName("breakfast")
    val breakfast: Int,
    @SerializedName("lunch")
    val lunch: Int,
    @SerializedName("dinner")
    val dinner: Int
)

data class MedicationStatDto(
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("takenCount")
    val takenCount: Int
)

data class AverageSleepDto(
    @SerializedName("hours")
    val hours: Int,
    @SerializedName("minutes")
    val minutes: Int
)

data class PsychSummaryDto(
    @SerializedName("good")
    val good: Int,
    @SerializedName("normal")
    val normal: Int,
    @SerializedName("bad")
    val bad: Int
)

data class BloodSugarDto(
    @SerializedName("beforeMeal")
    val beforeMeal: BloodSugarDetailDto,
    @SerializedName("afterMeal")
    val afterMeal: BloodSugarDetailDto
)

data class BloodSugarDetailDto(
    @SerializedName("normal")
    val normal: Int,
    @SerializedName("high")
    val high: Int,
    @SerializedName("low")
    val low: Int
)