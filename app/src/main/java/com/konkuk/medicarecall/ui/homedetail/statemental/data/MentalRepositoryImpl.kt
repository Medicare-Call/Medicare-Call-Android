package com.konkuk.medicarecall.ui.homedetail.statemental.data

import com.konkuk.medicarecall.ui.homedetail.statemental.model.MentalUiState
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.time.LocalDate
import javax.inject.Inject

class MentalRepositoryImpl @Inject constructor(
    private val mentalApi: MentalApi
) : MentalRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getMentalUiState(
        elderId: Int,
        date: LocalDate
    ): MentalUiState {
        return try {
            val res = mentalApi.getDailyMental(elderId, date.toString())

            if (res.isSuccessful) {
                val dto = res.body()
                    ?: return noRecord()

                val comments = dto.commentList.orEmpty()
                if (comments.isNotEmpty()) {
                    MentalUiState(
                        mentalSummary = comments,
                        isRecorded = true
                    )
                } else {
                    noRecord()
                }
            } else {
                // 400/404 등 에러 바디에서 message 추출
                val msg = res.errorBody()?.string()?.let { body ->
                    runCatching {
                        json.parseToJsonElement(body)
                            .jsonObject["message"]
                            ?.jsonPrimitive
                            ?.contentOrNull
                    }.getOrNull()
                }
                noRecord(msg)
            }
        } catch (_: Exception) {
            noRecord()
        }
    }

    private fun noRecord(serverMsg: String? = null) = MentalUiState(
        mentalSummary = listOf(serverMsg ?: "건강징후 기록 전이에요."),
        isRecorded = false
    )
}