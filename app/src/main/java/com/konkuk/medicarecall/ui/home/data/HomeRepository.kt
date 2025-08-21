package com.konkuk.medicarecall.ui.home.data

import android.util.Log
import com.konkuk.medicarecall.ui.home.model.HomeResponseDto
import com.konkuk.medicarecall.ui.home.model.HomeUiState
import com.konkuk.medicarecall.ui.home.model.ImmediateCallRequestDto
import com.konkuk.medicarecall.ui.home.model.MedicineUiState
import retrofit2.HttpException
import java.time.LocalDate
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeApi: HomeApi
) {
    suspend fun requestImmediateCareCall(
        elderId: Int, careCallOption: String
    ) = runCatching {
        val response = homeApi.requestImmediateCareCall(
            ImmediateCallRequestDto(
                elderId, careCallOption
            )
        )
        if (response.isSuccessful) {
            Log.d(
                "httplog",
                "전화 걸림, 어르신: $Int, 시간: $careCallOption"
            )
        } else {
            val errorBody =
                response.errorBody()?.string() ?: "Unknown error(updating health info)"
            Log.e(
                "httplog",
                "전화 걸기 실패: ${response.code()} - $errorBody"
            )
            throw HttpException(response)
        }

    }


    private fun mapNextTimeToKor(nextTime: String?): String = when (nextTime) {
        "MORNING" -> "아침"
        "LUNCH" -> "점심"
        "DINNER" -> "저녁"
        else -> "-"
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
                lunchEaten = res.mealStatus.lunch,
                dinnerEaten = res.mealStatus.dinner,

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
    "LUNCH" -> "점심"
    "DINNER" -> "저녁"
    else -> "-"
}