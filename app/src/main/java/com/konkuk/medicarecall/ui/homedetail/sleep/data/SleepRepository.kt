package com.konkuk.medicarecall.ui.homedetail.sleep.data

import com.konkuk.medicarecall.ui.homedetail.sleep.model.SleepUiState
import java.time.LocalDate

interface SleepRepository {
    suspend fun getSleepUiState(elderId: Int, date: LocalDate): SleepUiState
}