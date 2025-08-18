package com.konkuk.medicarecall.ui.home.model

data class HomeUiState(
    val elderName: String = "",
    val balloonMessage: String = "",
    val isRecorded: Boolean = false,
    val isEaten: Boolean = false,
    val breakfastEaten: Boolean? = null,
    val lunchEaten: Boolean? = null,
    val dinnerEaten: Boolean? = null,
    val medicines: List<MedicineUiState> = emptyList(),
    val sleep: HomeResponseDto.SleepDto = HomeResponseDto.SleepDto(0, 0),
    val healthStatus: String = "",
    val mentalStatus: String = "",
    val glucoseLevelAverageToday: Int = 0
) {
    companion object {
        val EMPTY = HomeUiState(
            elderName = "",
            balloonMessage = "",
            isRecorded = false,
            isEaten = false,
            medicines = emptyList(),
            sleep = HomeResponseDto.SleepDto(0, 0),
            healthStatus = "",
            mentalStatus = "",
            glucoseLevelAverageToday = 0
        )
        fun from(dto: HomeResponseDto): HomeUiState = HomeUiState(
            elderName = dto.elderName,
            balloonMessage = dto.aiSummary,
            isRecorded = dto.mealStatus.breakfast || dto.mealStatus.lunch || dto.mealStatus.dinner,
            isEaten = dto.mealStatus.breakfast || dto.mealStatus.lunch || dto.mealStatus.dinner,
            breakfastEaten = dto.mealStatus.breakfast,
            lunchEaten = dto.mealStatus.lunch,
            dinnerEaten = dto.mealStatus.dinner,
            medicines = dto.medicationStatus.medicationList.map {
                MedicineUiState(
                    medicineName = it.type,
                    todayTakenCount = it.taken,
                    todayRequiredCount = it.goal,
                    nextDoseTime = when (it.nextTime) {
                        "MORNING" -> "아침"
                        "LUNCH"   -> "점심"
                        "DINNER"  -> "저녁"
                        else      -> "-"
                    }
                )
            },
            sleep = dto.sleep,
            healthStatus = dto.healthStatus,
            mentalStatus = dto.mentalStatus,
            glucoseLevelAverageToday = dto.bloodSugar.meanValue
        )
    }
}

data class MedicineUiState(
    val medicineName: String,
    val todayTakenCount: Int,
    val todayRequiredCount: Int,
    val nextDoseTime: String?
)
