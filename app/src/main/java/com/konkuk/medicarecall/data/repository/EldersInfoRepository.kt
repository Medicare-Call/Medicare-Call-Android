package com.konkuk.medicarecall.data.repository

import com.konkuk.medicarecall.data.api.EldersInfoService
import com.konkuk.medicarecall.data.dto.request.ElderRegisterRequestDto
import com.konkuk.medicarecall.data.dto.response.EldersInfoResponseDto
import com.konkuk.medicarecall.data.dto.response.EldersSubscriptionResponseDto
import com.konkuk.medicarecall.ui.model.GenderType
import com.konkuk.medicarecall.ui.model.RelationshipType
import com.konkuk.medicarecall.ui.model.SeniorData
import com.konkuk.medicarecall.ui.model.SeniorLivingType
import javax.inject.Inject

class EldersInfoRepository @Inject constructor(
    private val eldersInfoService: EldersInfoService
) {
    suspend fun getElders(): Result<List<EldersInfoResponseDto>> = runCatching {
        val response = eldersInfoService.getElders()
        if (response.isSuccessful) {
            response.body() ?: throw IllegalStateException("Response body is null(eldersPersonalInfo)")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error(eldersPersonalInfo)"
            throw Exception("Request failed with code ${response.code()}: $errorBody")
        }
    }

    suspend fun getSubscriptions(): Result<List<EldersSubscriptionResponseDto>> = runCatching {
        val response = eldersInfoService.getSubscriptions()
        if (response.isSuccessful) {
            response.body() ?: throw IllegalStateException("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Request failed with code ${response.code()}: $errorBody")
        }
    }

    suspend fun updateElder(
        id: Int,
        request: SeniorData
    ): Result<Unit> = runCatching {
        val response = eldersInfoService.updateElder(
            id,
            ElderRegisterRequestDto(
                request.name,
                birthDate = request.birthDate,
                gender = if (request.gender) GenderType.MALE else GenderType.FEMALE,
                phone = request.phoneNumber,
                relationship = RelationshipType.entries.find { it.displayName == request.relationship }!!,
                residenceType = SeniorLivingType.entries.find { it.displayName == request.livingType }!!,
            )
        )
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

    suspend fun getCareCallTimes(id:Int): Result<Unit> = runCatching {
        val response = eldersInfoService.getCallTimes(id)
        if (response.isSuccessful) {
            response.body() ?: throw IllegalStateException("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Request failed with code ${response.code()}: $errorBody")
        }

    }

}