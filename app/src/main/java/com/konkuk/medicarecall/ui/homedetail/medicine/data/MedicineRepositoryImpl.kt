package com.konkuk.medicarecall.ui.homedetail.medicine.data

import com.konkuk.medicarecall.ui.homedetail.medicine.model.DoseStatus
import com.konkuk.medicarecall.ui.homedetail.medicine.model.DoseStatusItem
import com.konkuk.medicarecall.ui.homedetail.medicine.model.MedicineUiState
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.time.LocalDate
import javax.inject.Inject

class MedicineRepositoryImpl @Inject constructor(
    private val medicineApi: MedicineApi
) : MedicineRepository {

    private val json = Json { ignoreUnknownKeys = true }

    private var lastTemplate: List<Pair<String, Int>> = emptyList()

    override suspend fun getMedicineUiStateList(
        elderId: Int,
        date: LocalDate
    ): List<MedicineUiState> {
        return try {
            val res = medicineApi.getDailyMedication(elderId, date.toString())

            if (res.isSuccessful) {
                val dto = res.body() ?: return buildUnrecordedCardsOrMessage(serverMsg = null)
                if (dto.medications.isEmpty()) return buildUnrecordedCardsOrMessage(serverMsg = null)

                val ui = dto.medications.map { m ->
                    MedicineUiState(
                        medicineName = m.type,
                        todayTakenCount = m.takenCount,
                        todayRequiredCount = m.goalCount,
                        doseStatusList = m.times.map { t ->
                            DoseStatusItem(
                                time = when (t.time) {
                                    "MORNING" -> "아침"
                                    "LUNCH"   -> "점심"
                                    "DINNER"  -> "저녁"
                                    else      -> t.time
                                },
                                doseStatus = if (t.taken) DoseStatus.TAKEN else DoseStatus.SKIPPED
                            )
                        }
                    )
                }


                lastTemplate = dto.medications.map { it.type to it.goalCount }
                ui
            } else {
                // ✅ 서버 메시지(400/404 등) 파싱
                val serverMsg = res.errorBody()?.string()?.let { body ->
                    runCatching {
                        json.parseToJsonElement(body)
                            .jsonObject["message"]?.jsonPrimitive?.contentOrNull
                    }.getOrNull()
                }
                buildUnrecordedCardsOrMessage(serverMsg)
            }
        } catch (_: Exception) {
            buildUnrecordedCardsOrMessage(serverMsg = null)
        }
    }


    private fun buildUnrecordedCardsOrMessage(serverMsg: String?): List<MedicineUiState> {
        if (lastTemplate.isNotEmpty()) {

            return lastTemplate.map { (name, goal) ->
                MedicineUiState(
                    medicineName = name,
                    todayTakenCount = 0,
                    todayRequiredCount = goal,
                    doseStatusList = List(goal) {
                        DoseStatusItem(time = "", doseStatus = DoseStatus.NOT_RECORDED)
                    }
                )
            }
        }
        return listOf(
            MedicineUiState(
                medicineName = serverMsg ?: "복약 기록 전이에요.",
                todayTakenCount = 0,
                todayRequiredCount = 0,
                doseStatusList = emptyList()
            )
        )
    }
}
