package com.konkuk.medicarecall.data.repository

import android.util.Log
import com.konkuk.medicarecall.data.api.ElderRegisterService
import com.konkuk.medicarecall.data.api.EldersInfoService
import com.konkuk.medicarecall.data.dto.request.ElderHealthRegisterRequestDto
import com.konkuk.medicarecall.data.dto.request.MedicationSchedule
import com.konkuk.medicarecall.data.dto.response.EldersHealthResponseDto
import com.konkuk.medicarecall.ui.model.MedicationTimeType
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EldersHealthInfoRepository @Inject constructor(
    private val elderInfoService: EldersInfoService,
    private val elderRegisterService: ElderRegisterService
) {

    private var cachedHealthInfo: List<EldersHealthResponseDto>? = null


    fun refresh() {
        Log.d("Cache", "EldersHealthInfoRepository cache invalidated")
        cachedHealthInfo = null
    }

    suspend fun getEldersHealthInfo(): Result<List<EldersHealthResponseDto>> {

        cachedHealthInfo?.let {
            Log.d("Cache", "Returning cached health info")
            return Result.success(it)
        }


        return runCatching {
            Log.d("Cache", "Fetching new health info from server")
            val response = elderInfoService.getElderHealthInfo()
            if (response.isSuccessful) {
                val body = response.body()
                    ?: throw IllegalStateException("Response body is null(eldersHealthInfo)")
                cachedHealthInfo = body // 캐시에 저장
                body
            } else {
                throw HttpException(response)
            }
        }
    }


    suspend fun updateHealthInfo(
        elderInfo: EldersHealthResponseDto
    ): Result<Unit> =
        runCatching {
            val medicationSchedule = elderInfo.medications.toMedicationSchedules()
            val elder = ElderHealthRegisterRequestDto(
                diseaseNames = elderInfo.diseases,
                medicationSchedules = medicationSchedule,
                notes = elderInfo.notes
            )
            val response = elderRegisterService.postElderHealthInfo(
                elderInfo.elderId,
                elder
            )
            if (response.isSuccessful) {

                refresh()
                Log.d(
                    "EldersHealthInfoRepository",
                    "Health info updated successfully for elderId: ${elderInfo.elderId}"
                )
            } else {
                val errorBody =
                    response.errorBody()?.string() ?: "Unknown error(updating health info)"
                Log.e(
                    "EldersHealthInfoRepository",
                    "Failed to update health info: ${response.code()} - $errorBody"
                )
                throw HttpException(response)
            }
        }

    fun Map<MedicationTimeType, List<String>>.toMedicationSchedules(): List<MedicationSchedule> {
        val timesByMed = linkedMapOf<String, MutableSet<MedicationTimeType>>()
        for ((time, meds) in this) {
            for (med in meds) {
                timesByMed.getOrPut(med.trim()) { linkedSetOf() }.add(time)
            }
        }
        return timesByMed.map { (medName, times) ->
            MedicationSchedule(
                medicationName = medName,
                scheduleTimes = times.sortedBy { it.ordinal }
            )
        }
    }

}
