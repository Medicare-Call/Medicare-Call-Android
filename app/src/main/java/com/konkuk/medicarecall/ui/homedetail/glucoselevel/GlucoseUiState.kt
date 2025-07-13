package com.konkuk.medicarecall.ui.homedetail.glucoselevel


data class GlucoseUiState(
    val selectedTiming: GlucoseTiming,// BEFORE_MEAL(공복) / AFTER_MEAL(식후)

    val dailyAverageBeforeMeal: Int, // 오늘 하루 평균 공복 혈당
    val dailyAverageAfterMeal: Int,  // 오늘 하루 평균 식후 혈당
    val recentBeforeMeal: Int,       // 어제 마지막 평균 공복 혈당
    val recentAfterMeal: Int,        // 어제 마지막 평균 식후 혈당
    val glucoseLevelStatusBeforeMeal: String,  // 공복 혈당 상태 (낮음/정상/높음)
    val glucoseLevelStatusAfterMeal: String,   // 식후 혈당 상태 (낮음/정상/높음)
    val isRecorded: Boolean           // 기록 여부

)

enum class GlucoseTiming {
    BEFORE_MEAL,
    AFTER_MEAL
}


