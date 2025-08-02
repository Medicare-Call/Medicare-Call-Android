package com.konkuk.medicarecall.ui.homedetail.statehealth.data

import com.konkuk.medicarecall.ui.homedetail.statehealth.model.HealthUiState
import java.time.LocalDate
import javax.inject.Inject

class HealthRepository @Inject constructor(
    private val healthApi: HealthApi
) {
    suspend fun getHealthUiState(
        guardianId: Int,
        date: LocalDate
    ): HealthUiState {

        // 테스트: 2025-07-23
        if (date.toString() == "2025-07-23") {
            return HealthUiState(
                symptoms = listOf("손 떨림 증상", "거동 불편", "몸이 느려짐"),
                symptomAnalysis = "주요증상으로 보아 파킨슨병이 의심돼요. 어르신과 함께 병원에 방문해 보세요.",
                isRecorded = true
            )
        }

        // 그 외 날짜는 미기록 처리
        return try {
            val response = healthApi.getDailyHealth(guardianId, date.toString())
            if (response.symptomList.isNotEmpty() && response.analysisComment != null) {
                HealthUiState(
                    symptoms = response.symptomList,
                    symptomAnalysis = response.analysisComment,
                    isRecorded = true
                )
            } else {
                HealthUiState(
                    symptoms = listOf("건강징후 기록 전이에요."),
                    symptomAnalysis = "증상분석 전이에요.",
                    isRecorded = false
                )
            }
        } catch (e: Exception) {
            HealthUiState(
                symptoms = listOf("건강징후 기록 전이에요."),
                symptomAnalysis = "증상분석 전이에요.",
                isRecorded = false
            )
        }
    }
}