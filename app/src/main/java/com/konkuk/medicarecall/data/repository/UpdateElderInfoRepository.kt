package com.konkuk.medicarecall.data.repository

import com.konkuk.medicarecall.data.api.EldersInfoService
import com.konkuk.medicarecall.data.dto.request.ElderRegisterRequestDto
import javax.inject.Inject

class UpdateElderInfoRepository @Inject constructor(
    private val eldersInfoService : EldersInfoService
){
    suspend fun updateElderInfo(id: Int, request: ElderRegisterRequestDto): Result<Unit> = runCatching {
        val response = eldersInfoService.updateElder(id, request)
        if (response.isSuccessful) {
            response.body() ?: throw IllegalStateException("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Request failed with code ${response.code()}: $errorBody")
        }
    }

    suspend fun deleteElder(id: Int): Result<Unit> = runCatching {
        val response = eldersInfoService.deleteElderSettings(id)
        if (response.isSuccessful) {
            response.body() ?: throw IllegalStateException("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Request failed with code ${response.code()}: $errorBody")
        }
    }
}

