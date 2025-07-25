package com.konkuk.medicarecall.ui.homedetail.medicine.model

data class MedicineUiState(
    val medicineName: String,           // 약 이름
    val todayTakenCount: Int,           // 오늘 복약 완료 횟수
    val todayRequiredCount: Int,        // 목표 복약 횟수
    val nextDoseTime: String?,          // 다음 복약 예정 시간대 (MORNING 등)
    val doseStatusList: List<DoseStatusItem> // 복약 시간대 + 상태
)

data class DoseStatusItem(
    val time: String,       // "MORNING", "LUNCH", "DINNER"
    val status: DoseStatus  // TAKEN, SKIPPED
)

enum class DoseStatus {
    TAKEN,
    SKIPPED,
    NOT_RECORDED
}
