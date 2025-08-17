package com.konkuk.medicarecall.ui.homedetail.medicine.data

import com.konkuk.medicarecall.ui.homedetail.medicine.model.MedicineUiState
import java.time.LocalDate

interface MedicineRepository {
    suspend fun getMedicineUiStateList(elderId: Int, date: LocalDate): List<MedicineUiState>
}