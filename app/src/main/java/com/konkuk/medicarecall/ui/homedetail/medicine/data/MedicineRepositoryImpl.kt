package com.konkuk.medicarecall.ui.homedetail.medicine.data

import android.util.Log
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

            Log.d("MedicineRepo", "response=${res.body()}")
            if (res.isSuccessful) {
                val dto = res.body() ?: return buildUnrecordedCardsOrMessage(serverMsg = null)
                if (dto.medications.isEmpty()) return buildUnrecordedCardsOrMessage(serverMsg = null)

                // 성공 응답 매핑
                val ui = dto.medications.map { m ->
                    // 1) 시간대 고정 순서(아침-점심-저녁)로 정렬
                    val order = listOf("MORNING", "LUNCH", "DINNER")
                    val kor = mapOf("MORNING" to "아침", "LUNCH" to "점심", "DINNER" to "저녁")

                    val mapped = order.mapNotNull { slot ->
                        m.times.find { it.time == slot }?.let { t ->
                            DoseStatusItem(
                                time = kor[slot] ?: slot,
                                // 서버가 해당 슬롯에 레코드를 내려줬는데 taken=false 라면 → '건너뜀'(빨강)
                                doseStatus = if (t.taken) DoseStatus.TAKEN else DoseStatus.SKIPPED
                            )
                        }
                    }

                    // 2) 기록이 아예 없는 슬롯은 회색으로 패딩
                    val padded = if (mapped.size < m.goalCount) {
                        mapped + List(m.goalCount - mapped.size) {
                            DoseStatusItem(time = "", doseStatus = DoseStatus.NOT_RECORDED) // 회색
                        }
                    } else {
                        mapped.take(m.goalCount)
                    }

                    MedicineUiState(
                        medicineName = m.type,
                        todayTakenCount = m.takenCount,
                        todayRequiredCount = m.goalCount,
                        doseStatusList = padded
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
