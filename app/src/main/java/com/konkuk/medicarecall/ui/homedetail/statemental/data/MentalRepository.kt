package com.konkuk.medicarecall.ui.homedetail.statemental.data

import com.konkuk.medicarecall.ui.homedetail.statemental.model.MentalUiState
import java.time.LocalDate
import javax.inject.Inject

class MentalRepository @Inject constructor(
    private val mentalApi: MentalApi
) {
    suspend fun getMentalUiState(
        guardianId: Int,
        date: LocalDate
    ): MentalUiState {
        val dateString = date.toString()
        // 테스트: 2025-07-23
        if (date.toString() == "2025-07-23") {
            return MentalUiState(
                mentalSummary = listOf(
                    "날씨가 좋아서 기분이 좋음",
                    "어느 때와 비슷함"
                ),
                isRecorded = true
            )
        }

        // 그 외 날짜는 미기록 처리
        return try {
            val response = mentalApi.getDailyMental(guardianId, date.toString())
            if (response.commentList.isNotEmpty()) {
                MentalUiState(
                    mentalSummary = response.commentList,
                    isRecorded = true
                )
            } else {
                MentalUiState(
                    mentalSummary = listOf("건강징후 기록 전이에요."),
                    isRecorded = false
                )
            }
        } catch (e: Exception) {
            MentalUiState(
                mentalSummary = listOf("건강징후 기록 전이에요."),
                isRecorded = false
            )
        }
    }
}