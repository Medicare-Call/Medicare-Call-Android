package com.konkuk.medicarecall.ui.homedetail.medicine

data class MedicineUiState(

    val medicineName: String,       // 약 이름
    val todayTakenCount: Int,       //오늘 복약 완료 횟수
    val todayRequiredCount: Int,    //오늘 복약 해야 할 횟수
    val nextDoseTime: String?,      //다음 복약 예정 시간
    val doseStatusList: List<DoseStatus> //복용 유무, 기록 여부

)

enum class DoseStatus {
    TAKEN,     // 복용함
    SKIPPED,   // 복용 안 함
    NOT_RECORDED // 기록 안됨
}
