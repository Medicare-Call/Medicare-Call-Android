package com.konkuk.medicarecall.data.repository

import com.konkuk.medicarecall.data.api.EldersInfoService
import com.konkuk.medicarecall.data.dto.request.ElderRegisterRequestDto
import com.konkuk.medicarecall.data.dto.response.EldersInfoResponseDto
import com.konkuk.medicarecall.data.dto.response.EldersSubscriptionResponseDto
import javax.inject.Inject

class EldersInfoRepository @Inject constructor(
    private val eldersInfoService: EldersInfoService
) {
    suspend fun getElders(): Result<List<EldersInfoResponseDto>> = runCatching {
        val response = eldersInfoService.getElders()
        if (response.isSuccessful) {
            response.body() ?: throw IllegalStateException("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Request failed with code ${response.code()}: $errorBody")
        }
    }

    suspend fun getSubscriptions(): Result<EldersSubscriptionResponseDto> = runCatching {
        val response = eldersInfoService.getSubscriptions()
        if (response.isSuccessful) {
            response.body() ?: throw IllegalStateException("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Request failed with code ${response.code()}: $errorBody")
        }
    }

    suspend fun updateElder(id: Int, request: ElderRegisterRequestDto): Result<Unit> = runCatching {
        val response = eldersInfoService.updateElder(id, request)
        if (response.isSuccessful) {
            response.body() ?: throw IllegalStateException("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Request failed with code ${response.code()}: $errorBody")
        }
    }
}