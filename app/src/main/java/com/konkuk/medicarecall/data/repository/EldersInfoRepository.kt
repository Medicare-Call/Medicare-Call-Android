package com.konkuk.medicarecall.data.repository

import com.konkuk.medicarecall.data.api.EldersInfoService
import com.konkuk.medicarecall.data.dto.request.ElderRegisterRequestDto
import com.konkuk.medicarecall.data.dto.response.CallTimeResponseDto
import com.konkuk.medicarecall.data.dto.response.EldersInfoResponseDto
import com.konkuk.medicarecall.data.dto.response.EldersSubscriptionResponseDto
import com.konkuk.medicarecall.ui.model.GenderType
import com.konkuk.medicarecall.ui.model.RelationshipType
import com.konkuk.medicarecall.ui.model.ElderData
import com.konkuk.medicarecall.ui.model.ElderResidenceType
import com.konkuk.medicarecall.ui.util.formatAsDate
import retrofit2.HttpException
import javax.inject.Inject

class EldersInfoRepository @Inject constructor(
    private val eldersInfoService: EldersInfoService
) {
    suspend fun getElders(): Result<List<EldersInfoResponseDto>> = runCatching {
        val response = eldersInfoService.getElders()
        if (response.isSuccessful) {
            response.body()
                ?: throw IllegalStateException("Response body is null(eldersPersonalInfo)")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error(eldersPersonalInfo)"
            throw HttpException(response)
        }
    }

    suspend fun getSubscriptions(): Result<List<EldersSubscriptionResponseDto>> = runCatching {
        val response = eldersInfoService.getSubscriptions()
        if (response.isSuccessful) {
            response.body() ?: throw IllegalStateException("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw HttpException(response)
        }
    }

    suspend fun updateElder(
        id: Int,
        request: ElderData
    ): Result<Unit> = runCatching {
        val response = eldersInfoService.updateElder(
            id,
            ElderRegisterRequestDto(
                request.name,
                birthDate = request.birthDate.formatAsDate(),
                gender = if (request.gender) GenderType.MALE else GenderType.FEMALE,
                phone = request.phoneNumber,
                relationship = RelationshipType.entries.find { it.displayName == request.relationship }!!,
                residenceType = ElderResidenceType.entries.find { it.displayName == request.livingType }!!,
            )
        )
        if (response.isSuccessful) {
            response.body() ?: throw IllegalStateException("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception(errorBody)
        }
    }

    suspend fun deleteElder(id: Int): Result<Unit> = runCatching {
        val response = eldersInfoService.deleteElderSettings(id)
        if (response.isSuccessful) {
            response.body() ?: throw IllegalStateException("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw HttpException(response)
        }
    }

    suspend fun getCareCallTimes(id: Int): Result<CallTimeResponseDto> = runCatching {
        val response = eldersInfoService.getCallTimes(id)
        if (response.isSuccessful) {
            response.body() ?: throw IllegalStateException("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw HttpException(response)
        }

    }

}