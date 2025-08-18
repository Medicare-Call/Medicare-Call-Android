package com.konkuk.medicarecall.ui.home.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeResponseDto(
    @SerialName("memberName")
    val elderName: String = "",

    @SerialName("AISummary")
    val aiSummary: String = "",

    val mealStatus: MealStatusDto = MealStatusDto(),
    val medicationStatus: MedicationStatusDto = MedicationStatusDto(),
    val sleep: SleepDto = SleepDto(),
    val healthStatus: String = "",
    val mentalStatus: String = "",
    val bloodSugar: BloodSugarDto = BloodSugarDto()
) {
    @Serializable
    data class MealStatusDto(
        val breakfast: Boolean = false,
        val lunch: Boolean = false,
        val dinner: Boolean = false
    )

    @Serializable
    data class MedicationStatusDto(
        val totalTaken: Int = 0,
        val totalGoal: Int = 0,
        val nextMedicationTime: String? = null,

        val medicationList: List<MedicationDto> = emptyList()
    )

    @Serializable
    data class MedicationDto(
        val type: String = "",
        val taken: Int = 0,
        val goal: Int = 0,
        val nextTime: String? = null
    )

    @Serializable
    data class SleepDto(
        val meanHours: Int = 0,
        val meanMinutes: Int = 0
    )

    @Serializable
    data class BloodSugarDto(
        @SerialName("meanValue")
        val meanValue: Int = 0
    )
}