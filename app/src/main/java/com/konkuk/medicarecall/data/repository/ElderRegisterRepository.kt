package com.konkuk.medicarecall.data.repository

import android.net.http.HttpException
import android.util.Log
import com.konkuk.medicarecall.data.api.ElderRegisterService
import com.konkuk.medicarecall.data.dto.request.ElderHealthRegisterRequestDto
import com.konkuk.medicarecall.data.dto.request.ElderRegisterRequestDto
import com.konkuk.medicarecall.data.dto.response.ElderRegisterResponseDto
import com.konkuk.medicarecall.data.mapper.ElderHealthMapper
import com.konkuk.medicarecall.ui.model.GenderType
import com.konkuk.medicarecall.ui.model.HealthIssueType
import com.konkuk.medicarecall.ui.model.RelationshipType
import com.konkuk.medicarecall.ui.model.SeniorData
import com.konkuk.medicarecall.ui.model.SeniorHealthData
import com.konkuk.medicarecall.ui.model.SeniorLivingType
import com.konkuk.medicarecall.ui.util.formatAsDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ElderRegisterRepository @Inject constructor(
    private val elderRegisterService: ElderRegisterService,
    private val elderIdRepository: ElderIdRepository
) {
    private suspend fun postElder(request: ElderRegisterRequestDto): ElderRegisterResponseDto {
        val response = elderRegisterService.postElder(request)
        if (response.isSuccessful) {
            return response.body() ?: throw IllegalStateException("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Request failed with code ${response.code()}: $errorBody")
        }
    }

    private suspend fun postElderHealthInfo(id: Int, request: ElderHealthRegisterRequestDto) {
        val response = elderRegisterService.postElderHealthInfo(id, request)
        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Request failed with code ${response.code()}: $errorBody")
        }
    }

    suspend fun registerElderAndHealth(
        elders: Int,
        elderInfo: List<SeniorData>,
        elderHealthInfo: List<SeniorHealthData>
    ): Result<Unit> {
        return runCatching {
            repeat(elders) { index ->
                val elderResponse = postElder(
                    ElderRegisterRequestDto(
                        name = elderInfo[index].name,
                        birthDate = elderInfo[index].birthDate.formatAsDate(),
                        gender = if (elderInfo[index].gender) GenderType.MALE else GenderType.FEMALE,
                        phone = elderInfo[index].phoneNumber,
                        relationship = RelationshipType.entries.find { it.displayName == elderInfo[index].relationship }!!,
                        residenceType = SeniorLivingType.entries.find { it.displayName == elderInfo[index].livingType }!!,
                    )
                )

                // postElder가 성공적으로 끝나야만 이 라인으로 넘어올 수 있음
                val id = elderResponse.id
                val name = elderResponse.name
                elderIdRepository.addElderId(name, id)
                Log.d("httplog", "어르신 등록 성공, id: $id")
                postElderHealthInfo(
                    id,
                    ElderHealthRegisterRequestDto(
                        diseaseNames = elderHealthInfo[index].diseaseNames,
                        medicationSchedules = ElderHealthMapper.toRequestSchedules(elderHealthInfo[index].medicationMap),
                        notes = elderHealthInfo[index].notes.map { notes ->
                            HealthIssueType.entries.find { it.displayName == notes }!!
                        }
                    )
                )
            }
        }
    }

}