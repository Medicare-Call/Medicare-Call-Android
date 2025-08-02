package com.konkuk.medicarecall.ui.home.data

import com.konkuk.medicarecall.ui.home.model.MedicineUiState
import com.konkuk.medicarecall.ui.home.model.HomeUiState
import java.time.LocalDate
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeApi: HomeApi
) {


    private fun mapNextTimeToKor(nextTime: String?): String {
        return when (nextTime) {
            "MORNING" -> "아침"
            "LUNCH" -> "점심"
            "DINNER" -> "저녁"
            else -> "-"
        }
    }

    suspend fun getHomeUiState(elderId: Int, date: LocalDate): HomeUiState {
        return try {
            val res = homeApi.getHomeSummary(elderId, date.toString())
            HomeUiState(
                elderName = res.elderName,
                balloonMessage = res.aiSummary,
                isRecorded = true,
                isEaten = res.mealStatus.breakfast || res.mealStatus.lunch || res.mealStatus.dinner,
                medicines = res.medicationStatus.medicationList.map {
                    MedicineUiState(
                        medicineName = it.type,
                        todayTakenCount = it.taken,
                        todayRequiredCount = it.goal,
                        nextDoseTime = mapNextTimeToKor(it.nextTime)
                    )
                },
                sleepHours = res.sleep.meanHours + res.sleep.meanMinutes / 60.0,
                healthStatus = res.healthStatus,
                mentalStatus = res.mentalStatus,
                glucoseLevelAverageToday = res.bloodSugar.meanValue
            )
        } catch (e: Exception) {
            // TODO: 예외 처리용 기본값
            HomeUiState.EMPTY
        }
    }
}
