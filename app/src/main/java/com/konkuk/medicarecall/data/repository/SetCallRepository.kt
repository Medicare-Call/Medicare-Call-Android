package com.konkuk.medicarecall.data.repository

import com.konkuk.medicarecall.data.api.SetCallService
import com.konkuk.medicarecall.data.dto.request.SetCallTimeRequestDto
import javax.inject.Inject

class SetCallRepository @Inject constructor(
    private val service : SetCallService
){
    suspend fun saveForElder(
        elderId : Int,
        body : SetCallTimeRequestDto
    ) : Result <Unit> =
        runCatching {
            val response = service.saveCareCallTimes(elderId, body)
            if (!response.isSuccessful) {
                throw Exception("Error saving care call times: ${response.errorBody()?.string()} / SetCallRepository.kt")
            }
        }
}