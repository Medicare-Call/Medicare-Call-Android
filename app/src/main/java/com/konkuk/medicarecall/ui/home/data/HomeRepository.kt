package com.konkuk.medicarecall.ui.home.data

import com.konkuk.medicarecall.ui.home.model.HomeResponseDto
import com.konkuk.medicarecall.ui.home.model.HomeUiState
import com.konkuk.medicarecall.ui.home.model.MedicineUiState
import java.time.LocalDate
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeApi: HomeApi
) {
    suspend fun requestImmediateCareCall() = homeApi.requestImmediateCareCall()

    private fun mapNextTimeToKor(nextTime: String?): String = when (nextTime) {
        "MORNING" -> "아침"
        "LUNCH"   -> "점심"
        "DINNER"  -> "저녁"
        else      -> "-"
    }

    suspend fun getHomeUiState(elderId: Int, date: LocalDate): HomeUiState {
        return try {
            android.util.Log.d("HomeRepo", "[REQ] elderId=$elderId")

            val res = homeApi.getHomeSummary(elderId)

            val meds = res.medicationStatus.medicationList.orEmpty()
            android.util.Log.d(
                "HomeRepo",
                "[RES] elderName=${res.elderName}, medsCount=${meds.size}, " +
                        "totalTaken=${res.medicationStatus.totalTaken}, totalGoal=${res.medicationStatus.totalGoal}, " +
                        meds.joinToString(prefix = "items=[", postfix = "]") {
                            "type=${it.type}, taken=${it.taken}, goal=${it.goal}, next=${it.nextTime}"
                        }
            )

            HomeUiState(
                elderName = res.elderName,
                balloonMessage = res.aiSummary,
                breakfastEaten = res.mealStatus.breakfast,
                lunchEaten     = res.mealStatus.lunch,
                dinnerEaten    = res.mealStatus.dinner,

                medicines = res.medicationStatus.medicationList.orEmpty().map {
                    MedicineUiState(
                        medicineName = it.type,
                        todayTakenCount = it.taken,
                        todayRequiredCount = it.goal,
                        nextDoseTime = mapNextTimeToKor(it.nextTime)
                    )
                },


                sleep = res.sleep ?: HomeResponseDto.SleepDto(0, 0),
                healthStatus = res.healthStatus ?: "",
                mentalStatus = res.mentalStatus ?: "",
                glucoseLevelAverageToday = res.bloodSugar?.meanValue ?: 0
            )
        } catch (e: Exception) {
            android.util.Log.e("HomeRepo", "getHomeUiState failed elderId=$elderId", e)
            HomeUiState.EMPTY
        }
    }
}



private fun mapNextTimeToKor(nextTime: String?): String = when (nextTime) {
    "MORNING" -> "아침"
    "LUNCH"   -> "점심"
    "DINNER"  -> "저녁"
    else      -> "-"
}