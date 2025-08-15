package com.konkuk.medicarecall.data.repository

import android.util.Log
import android.util.Log.e
import com.konkuk.medicarecall.data.api.ElderRegisterService
import com.konkuk.medicarecall.data.api.EldersInfoService
import com.konkuk.medicarecall.data.dto.request.ElderHealthRegisterRequestDto
import com.konkuk.medicarecall.data.dto.response.EldersHealthResponseDto
import javax.inject.Inject

class EldersHealthInfoRepository @Inject constructor(
    private val elderInfoService : EldersInfoService,
    private val elderRegisterService : ElderRegisterService
) {
    suspend fun getEldersHealthInfo(): Result<List<EldersHealthResponseDto>> =
        runCatching {
            val response = elderInfoService.getElderHealthInfo()
            if (response.isSuccessful) {
                response.body() ?: throw IllegalStateException("Response body is null(eldersHealthInfo)")
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error(eldersHealthInfo)"
                throw Exception("Request failed with code ${response.code()}: $errorBody")
            }
        }

    suspend fun updateHealthInfo(
        elderInfo : EldersHealthResponseDto
    ) : Result<Unit> =
        runCatching {
            val elder = ElderHealthRegisterRequestDto(
                diseaseNames = elderInfo.diseases,
                medicationSchedules = elderInfo.medications,
                notes = elderInfo.specialNotes
            )
            val response = elderRegisterService.postElderHealthInfo(
                elderInfo.elderId,
                elder
            )
            if (response.isSuccessful) {
                Log.d("EldersHealthInfoRepository", "Health info updated successfully for elderId: ${elderInfo.elderId}")
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error(updating health info)"
                e("EldersHealthInfoRepository", "Failed to update health info: ${response.code()} - $errorBody")
                throw Exception("Request failed with code ${response.code()}: $errorBody")
            }
        }

}

