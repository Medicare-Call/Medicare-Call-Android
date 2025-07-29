package com.konkuk.medicarecall.ui.home.model

import com.google.gson.annotations.SerializedName

data class HomeResponseDto(
    val elderName: String,

    @SerializedName("AISummary")
    val aiSummary: String,
    val mealStatus: MealStatusDto,
    val medicationStatus: MedicationStatusDto,
    val sleep: SleepDto,
    val healthStatus: String,
    val mentalStatus: String,
    val bloodSugar: BloodSugarDto
) {
    data class MealStatusDto(
        val breakfast: Boolean,
        val lunch: Boolean,
        val dinner: Boolean
    )

    data class MedicationStatusDto(
        val totalTaken: Int,
        val totalGoal: Int,
        val nextMedicationTime: String,
        val medicationList: List<MedicationDto>
    )

    data class MedicationDto(
        val type: String,
        val taken: Int,
        val goal: Int,
        val nextTime: String
    )

    data class SleepDto(
        val meanHours: Int,
        val meanMinutes: Int
    )

    data class BloodSugarDto(
        val meanValue: Int
    )
}
