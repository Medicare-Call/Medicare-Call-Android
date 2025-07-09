package com.konkuk.medicarecall.navigation

sealed class Route(val route: String) {
    object LoginStart : Route("login_start")
    object LoginPhone : Route("login_phone")
    object LoginVerification : Route("login_verification")
    object LoginMyInfo : Route("login_my_info")
    object Home : Route("home")
    object Statistics : Route("statistics")
    object Settings : Route("settings")

    object MealDetail : Route("home_meal_detail")
    object MedicineDetail : Route("home_medicine_detail")
    object SleepDetail : Route("home_sleep_detail")
    object StateHealthDetail : Route("home_state_health_detail")
    object StateMentalDetail : Route("home_state_mental_detail")
    object GlucoseLevelDetail : Route("home_glucose_level_detail")
}