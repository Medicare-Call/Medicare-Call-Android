package com.konkuk.medicarecall.navigation

sealed class Route(val route: String) {
    object LoginStart : Route("login_start")
    object LoginPhone : Route("login_phone")
    object LoginVerification : Route("login_verification")
    object LoginMyInfo : Route("login_my_info")
    object LoginSeniorInfoScreen : Route("login_senior_info")
    object LoginSeniorMedInfoScreen : Route("login_senior_med_info")
    object Home : Route("home")
    object Statistics : Route("statistics")
    object Settings : Route("settings")

    object HomeMealDetail : Route("home_meal_detail")
    object HomeMedicineDetail : Route("home_medicine_detail")
    object HomeSleepDetail : Route("home_sleep_detail")
    object HomeStateHealthDetail : Route("home_state_health_detail")
    object HomeStateMentalDetail : Route("home_state_mental_detail")
    object HomeGlucoseLevelDetail : Route("home_glucose_level_detail")
}