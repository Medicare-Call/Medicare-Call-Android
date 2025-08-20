package com.konkuk.medicarecall.data.repository

import android.util.Log
import com.konkuk.medicarecall.data.api.ElderRegisterService
import com.konkuk.medicarecall.data.dto.request.ElderHealthRegisterRequestDto
import com.konkuk.medicarecall.data.dto.request.ElderRegisterRequestDto
import com.konkuk.medicarecall.data.dto.response.ElderRegisterResponseDto
import com.konkuk.medicarecall.data.mapper.ElderHealthMapper
import com.konkuk.medicarecall.ui.model.ElderData
import com.konkuk.medicarecall.ui.model.ElderHealthData
import com.konkuk.medicarecall.ui.model.ElderResidenceType
import com.konkuk.medicarecall.ui.model.GenderType
import com.konkuk.medicarecall.ui.model.HealthIssueType
import com.konkuk.medicarecall.ui.model.RelationshipType
import com.konkuk.medicarecall.ui.util.formatAsDate
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ElderRegisterRepository @Inject constructor(
    private val elderRegisterService: ElderRegisterService,
    private val elderIdRepository: ElderIdRepository
) {
    private suspend fun postElder(elderData: ElderData): ElderRegisterResponseDto {
        val response = elderRegisterService.postElder(
            ElderRegisterRequestDto(
                name = elderData.name,
                birthDate = elderData.birthDate.formatAsDate(),
                gender = if (elderData.gender) GenderType.MALE else GenderType.FEMALE,
                phone = elderData.phoneNumber,
                relationship = RelationshipType.entries.find { it.displayName == elderData.relationship }!!,
                residenceType = ElderResidenceType.entries.find { it.displayName == elderData.livingType }!!,
            )
        )
        if (response.isSuccessful) {
            return response.body() ?: throw IllegalStateException("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw HttpException(response)
        }
    }

    suspend fun postElderHealthInfo(id: Int, elderHealthData: ElderHealthData) {
        val response = elderRegisterService.postElderHealthInfo(
            id, ElderHealthRegisterRequestDto(
                diseaseNames = elderHealthData.diseaseNames,
                medicationSchedules = ElderHealthMapper.toRequestSchedules(elderHealthData.medicationMap),
                notes = elderHealthData.notes.map { notes ->
                    HealthIssueType.entries.find { it.displayName == notes }!!
                }
            ))
        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw HttpException(response)
        }
    }

    suspend fun registerElderAndHealth(
        elders: Int,
        elderInfoList: List<ElderData>,
        elderHealthInfo: List<ElderHealthData>
    ): Result<Unit> {
        return runCatching {
            repeat(elders) { index ->
                // 이미 등록돼 있지 않은 어르신일 경우 실행
                if (elderInfoList[index].id == null) {
                    val elderResponse = postElder(
                        elderInfoList[index]
                    )
                    // postElder가 성공적으로 끝나야만 이 라인으로 넘어올 수 있음
                    val id = elderResponse.id
                    val name = elderResponse.name
                    elderIdRepository.addElderId(name, id)
                    elderInfoList[index].id = id
                    Log.d("httplog", "어르신 등록 성공, id: $id")
                    postElderHealthInfo(
                        id,
                        elderHealthInfo[index]
                    )
                    Log.d("httplog", "어르신 건강정보 등록 성공")
                    elderHealthInfo[index].id = id
                }
            }
        }
    }

}