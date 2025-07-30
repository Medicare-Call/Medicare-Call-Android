package com.konkuk.medicarecall.ui.homedetail.medicine.data

import com.konkuk.medicarecall.ui.homedetail.medicine.model.MedicineUiState
import com.konkuk.medicarecall.ui.homedetail.medicine.model.DoseStatus
import com.konkuk.medicarecall.ui.homedetail.medicine.model.DoseStatusItem
import java.time.LocalDate
import javax.inject.Inject

class MedicineRepository @Inject constructor(
    private val medicineApi: MedicineApi
) {
    suspend fun getMedicineUiStateList(
        guardianId: Int,
        date: LocalDate
    ): List<MedicineUiState> {
        // 아래 두 줄은 임시로 주석 처리
        // val response: MedicineResponse = medicineApi.getDailyMedication(
        //     guardianId = guardianId,
        //     date = date.toString()
        // )

        // 임시 테스트용
        // 조건 추가: 2025-07-23에만 데이터 있고, 그 외는 비어 있음
        // 테스트 날짜
        val isDataRecorded = date == LocalDate.of(2025, 7, 23)

        return listOf(
            MedicineUiState(
                medicineName = "당뇨약",
                todayTakenCount = if (isDataRecorded) 2 else 0,
                todayRequiredCount = 3,
                nextDoseTime = if (isDataRecorded) "오전 10:00" else null,
                doseStatusList = listOf(
                    DoseStatusItem(
                        "아침",
                        if (isDataRecorded) DoseStatus.TAKEN else DoseStatus.NOT_RECORDED
                    ),
                    DoseStatusItem(
                        "점심",
                        if (isDataRecorded) DoseStatus.TAKEN else DoseStatus.NOT_RECORDED
                    ),
                    DoseStatusItem(
                        "저녁",
                        if (isDataRecorded) DoseStatus.NOT_RECORDED else DoseStatus.NOT_RECORDED
                    )
                )
            ),
            MedicineUiState(
                medicineName = "혈압약",
                todayTakenCount = if (isDataRecorded) 0 else 0,
                todayRequiredCount = 2,
                nextDoseTime = if (isDataRecorded) "오후 2:00" else null,
                doseStatusList = listOf(
                    DoseStatusItem(
                        "아침",
                        if (isDataRecorded) DoseStatus.SKIPPED else DoseStatus.NOT_RECORDED
                    ),
                    DoseStatusItem(
                        "점심",
                        if (isDataRecorded) DoseStatus.NOT_RECORDED else DoseStatus.NOT_RECORDED
                    )
                )
            )
        )
    }
}