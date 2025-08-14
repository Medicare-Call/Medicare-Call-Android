package com.konkuk.medicarecall.ui.homedetail.statehealth.data

import com.konkuk.medicarecall.ui.homedetail.statehealth.model.HealthUiState
import java.time.LocalDate

interface HealthRepository {
    suspend fun getHealthUiState(elderId: Int, date: LocalDate): HealthUiState
}