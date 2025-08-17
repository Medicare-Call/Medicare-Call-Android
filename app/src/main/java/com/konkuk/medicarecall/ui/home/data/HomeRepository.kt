package com.konkuk.medicarecall.ui.home.data

import android.util.Log
import com.konkuk.medicarecall.ui.home.model.HomeUiState
import com.konkuk.medicarecall.ui.home.model.MedicineUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.time.LocalDate
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeApi: HomeApi
) {
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
                isRecorded = res.mealStatus.breakfast || res.mealStatus.lunch || res.mealStatus.dinner,
                isEaten = res.mealStatus.breakfast || res.mealStatus.lunch || res.mealStatus.dinner,
                medicines = meds.map {
                    MedicineUiState(
                        medicineName = it.type,
                        todayTakenCount = it.taken,
                        todayRequiredCount = it.goal,
                        nextDoseTime = mapNextTimeToKor(it.nextTime)
                    )
                },
                sleep = res.sleep,
                healthStatus = res.healthStatus,
                mentalStatus = res.mentalStatus,
                glucoseLevelAverageToday = res.bloodSugar.meanValue
            )
        } catch (e: Exception) {
            android.util.Log.e("HomeRepo", "getHomeUiState failed elderId=$elderId", e)
            HomeUiState.EMPTY
        }
    }

    suspend fun startTestCall(phoneNumber: String, prompt: String): Result<String> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val res = homeApi.startTestCall(TestCallRequest(phoneNumber, prompt))
                if (res.isSuccessful) {

                    val body = res.body()?.string().orEmpty()
                    Result.success(body.ifEmpty { "OK" })
                } else {
                    val err = res.errorBody()?.string().orEmpty()
                    Log.e("HomeRepo", "startTestCall failed: ${res.code()} $err")
                    Result.failure(HttpException(res))
                }
            } catch (t: Throwable) {
                Result.failure(t)
            }
        }
}