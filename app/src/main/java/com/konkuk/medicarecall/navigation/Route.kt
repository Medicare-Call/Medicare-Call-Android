package com.konkuk.medicarecall.navigation

sealed class Route(val route: String) {
    object LoginStart : Route("login_start")
    object LoginPhone : Route("login_phone")
    object LoginVerification : Route("login_verification")
    object LoginMyInfo : Route("login_my_info")
    object LoginSeniorInfoScreen : Route("login_senior_info")
    object LoginSeniorMedInfoScreen : Route("login_senior_med_info")
    object SetCall : Route("set_call")
    object Payment : Route("payment")
    object NaverPay : Route("naver_pay")
    object FinishSplash : Route("finish_splash")
    object Home : Route("home")
    object Statistics : Route("statistics")
    object Settings : Route("settings")

    object HomeMealDetail : Route("home_meal_detail")
    object HomeMedicineDetail : Route("home_medicine_detail")
    object HomeSleepDetail : Route("home_sleep_detail")
    object HomeStateHealthDetail : Route("home_state_health_detail")
    object HomeStateMentalDetail : Route("home_state_mental_detail")
    object HomeGlucoseLevelDetail : Route("home_glucose_level_detail")

    object Alarm: Route("alarm")

    object Announcement : Route("announcement")
    object HealthInfo : Route("health_info")
    object HealthDetail : Route("health_detail")
    object MyDataSetting : Route("my_data_setting")
    object MyDetail : Route("my_detail")
    object PersonalDetail : Route("personal_detail")
    object PersonalInfo : Route("personal_info")
    object ServiceCenter : Route("service_center")
    object SettingAlarm : Route("setting_alarm")
    object SettingSubscribe : Route("setting_subscribe")
    object SubscribeDetail : Route("subscribe_detail")
}