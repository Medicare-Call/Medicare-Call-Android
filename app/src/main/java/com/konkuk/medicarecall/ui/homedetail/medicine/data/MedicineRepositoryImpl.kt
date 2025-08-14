package com.konkuk.medicarecall.ui.homedetail.medicine.data

import com.konkuk.medicarecall.data.dto.response.BaseResponse
import com.konkuk.medicarecall.ui.homedetail.medicine.model.DoseStatus
import com.konkuk.medicarecall.ui.homedetail.medicine.model.DoseStatusItem
import com.konkuk.medicarecall.ui.homedetail.medicine.model.MedicineUiState
import kotlinx.serialization.json.Json
import java.time.LocalDate
import javax.inject.Inject

class MedicineRepositoryImpl @Inject constructor(
    private val medicineApi: MedicineApi
) : MedicineRepository {

    private fun mapTimeToKor(time: String): String {
        return when (time) {
            "MORNING" -> "아침"
            "LUNCH" -> "점심"
            "DINNER" -> "저녁"
            else -> time
        }
    }

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getMedicineUiStateList(
        elderId: Int,
        date: LocalDate
    ): List<MedicineUiState> {
        return try {
            val res = medicineApi.getDailyMedication(elderId, date.toString())

            if (res.isSuccessful) {
                val dto = res.body() ?: return emptyList()
                if (dto.medications.isEmpty()) return emptyList()

                dto.medications.map { m ->
                    MedicineUiState(
                        medicineName = m.type,
                        todayTakenCount = m.takenCount,
                        todayRequiredCount = m.goalCount,
                        doseStatusList = m.times.map { t ->
                            DoseStatusItem(
                                time = when (t.time) {
                                    "MORNING" -> "아침"
                                    "LUNCH" -> "점심"
                                    "DINNER" -> "저녁"
                                    else -> t.time
                                },
                                doseStatus = if (t.taken) DoseStatus.TAKEN else DoseStatus.SKIPPED
                            )
                        }
                    )
                }
            } else {

                if (res.code() == 404) {
                    val parsed = res.errorBody()?.string()?.let {
                        runCatching { json.decodeFromString<BaseResponse<Nothing>>(it) }.getOrNull()
                    }
                    emptyList()
                } else {
                    emptyList()
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }
}