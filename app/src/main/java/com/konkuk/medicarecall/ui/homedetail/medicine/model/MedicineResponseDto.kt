package com.konkuk.medicarecall.ui.homedetail.medicine.model


data class MedicineResponseDto(
    val date: String,
    val medications: List<MedicineData>
)

data class MedicineData(
    val type: String,              // 약 종류 (예: "당뇨약")
    val goalCount: Int,           // 하루 목표 복약 횟수
    val takenCount: Int,          // 복약 완료 횟수
    val times: List<MedicineTime> // 복용 시간대 별 상태
)

data class MedicineTime(
    val time: String,   // 예: "MORNING", "LUNCH", "DINNER"
    val taken: Boolean  // 복용 여부
)
