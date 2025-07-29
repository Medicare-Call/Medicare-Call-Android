package com.konkuk.medicarecall.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import androidx.navigation.navigation
import com.konkuk.medicarecall.ui.alarm.screen.AlarmScreen
import com.konkuk.medicarecall.ui.login_info.uistate.LoginState
import com.konkuk.medicarecall.ui.home.screen.HomeScreen
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.GlucoseGraphState
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.GlucoseTiming
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.GlucoseUiState
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.screen.GlucoseDetail
import com.konkuk.medicarecall.ui.homedetail.meal.screen.MealDetail
import com.konkuk.medicarecall.ui.homedetail.meal.screen.SleepDetail
import com.konkuk.medicarecall.ui.homedetail.meal.screen.StateHealthDetail
import com.konkuk.medicarecall.ui.homedetail.meal.screen.StateMentalDetail
import com.konkuk.medicarecall.ui.homedetail.medicine.DoseStatus
import com.konkuk.medicarecall.ui.homedetail.medicine.MedicineUiState
import com.konkuk.medicarecall.ui.homedetail.medicine.screen.MedicineDetail
import com.konkuk.medicarecall.ui.homedetail.sleep.SleepUiState
import com.konkuk.medicarecall.ui.homedetail.statehealth.HealthUiState
import com.konkuk.medicarecall.ui.homedetail.statemental.MentalUiState
import com.konkuk.medicarecall.ui.login_care_call.screen.SetCallScreen
import com.konkuk.medicarecall.ui.login_info.screen.LoginMyInfoScreen
import com.konkuk.medicarecall.ui.login_info.screen.LoginPhoneScreen
import com.konkuk.medicarecall.ui.login_senior.screen.LoginSeniorInfoScreen
import com.konkuk.medicarecall.ui.login_info.screen.LoginStartScreen
import com.konkuk.medicarecall.ui.login_info.screen.LoginVerificationScreen
import com.konkuk.medicarecall.ui.login_info.viewmodel.LoginViewModel
import com.konkuk.medicarecall.ui.login_payment.screen.FinishSplashScreen
import com.konkuk.medicarecall.ui.login_payment.screen.NaverPayScreen
import com.konkuk.medicarecall.ui.login_payment.screen.PaymentScreen
import com.konkuk.medicarecall.ui.login_senior.LoginSeniorViewModel
import com.konkuk.medicarecall.ui.login_senior.screen.LoginSeniorMedInfoScreen
import com.konkuk.medicarecall.ui.settings.screen.AnnouncementScreen
import com.konkuk.medicarecall.ui.settings.screen.HealthDetailScreen
import com.konkuk.medicarecall.ui.settings.screen.HealthInfoScreen
import com.konkuk.medicarecall.ui.settings.screen.MyDataSettingScreen
import com.konkuk.medicarecall.ui.settings.screen.MyDetailScreen
import com.konkuk.medicarecall.ui.settings.screen.PersonalDetailScreen
import com.konkuk.medicarecall.ui.settings.screen.PersonalInfoScreen
import com.konkuk.medicarecall.ui.settings.screen.ServiceCenterScreen
import com.konkuk.medicarecall.ui.settings.screen.SettingAlarmScreen
import com.konkuk.medicarecall.ui.settings.screen.SettingSubscribeScreen
import com.konkuk.medicarecall.ui.settings.screen.SettingsScreen
import com.konkuk.medicarecall.ui.settings.screen.SubscribeDetailScreen
import com.konkuk.medicarecall.ui.statistics.screen.StatisticsScreen


@Composable
fun NavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    loginSeniorViewModel: LoginSeniorViewModel,
    modifier: Modifier = Modifier
) {
    val loginState = loginViewModel.loginState.collectAsState()
    val startDestination = if (loginState.value == LoginState.LoggedIn) "main" else "login"
    // navController = navController, startDestination = Route.Home.route, // 시작 화면
    NavHost(
        navController = navController,
        startDestination = startDestination, // 시작 화면
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = modifier
    ) {
        // 메인 내비게이션
        navigation(startDestination = Route.Home.route, route = "main") {


            // 홈
            composable(route = Route.Home.route) {
                HomeScreen(
                    navController = navController,
                    onNavigateToMealDetail = { navController.navigate(Route.MealDetail.route) },
                    onNavigateToMedicineDetail = { navController.navigate(Route.MedicineDetail.route) },
                    onNavigateToSleepDetail = { navController.navigate(Route.SleepDetail.route) },
                    onNavigateToStateHealthDetail = { navController.navigate(Route.StateHealthDetail.route) },
                    onNavigateToStateMentalDetail = { navController.navigate(Route.StateMentalDetail.route) },
                    onNavigateToGlucoseDetail = { navController.navigate(Route.GlucoseDetail.route) }
                )
            }

            // 홈 상세 화면_식사 화면
            composable(route = Route.MealDetail.route) {
                MealDetail( navController = navController)
            }


            // 홈 상세 화면_복용 화면 // 테스트
            composable(route = Route.MedicineDetail.route) {
                MedicineDetail(
                    navController = navController,
                    medicines = listOf(
                        MedicineUiState(
                            medicineName = "당뇨약",
                            todayTakenCount = 2,
                            todayRequiredCount = 3,
                            nextDoseTime = null,
                            doseStatusList = listOf(
                                DoseStatus.TAKEN,
                                DoseStatus.TAKEN,
                                DoseStatus.NOT_RECORDED
                            )
                        ),
                        MedicineUiState(
                            medicineName = "혈압약",
                            todayTakenCount = 0,
                            todayRequiredCount = 2,
                            nextDoseTime = null,
                            doseStatusList = listOf(
                                DoseStatus.SKIPPED,
                                DoseStatus.NOT_RECORDED
                            )
                        )
                    )
                )
            }

            //홈 상세 화면_수면 화면 // 테스트

            composable(route = Route.SleepDetail.route) {
                SleepDetail(
                    navController = navController,
                    sleeps = SleepUiState(
                        date = "2025-07-07",
                        totalSleepHours = 8,
                        totalSleepMinutes = 12,
                        bedTime = "오후 10:12",
                        wakeUpTime = "오전 06:00",
                    )
                )
            }


            //홈 상세 화면_건강 징후 화면 // 테스트

            composable(route = Route.StateHealthDetail.route) {
                StateHealthDetail(
                    navController = navController,
                    healths = HealthUiState(
                        symptoms = listOf(
                            "손 떨림 증상",
                            "거동 불편",
                            "몸이 느려짐"
                        ),
                        //TODO: 병명 볼드처리
                        symptomAnalysis = "주요 증상으로 보아 파킨슨 병이 의심돼요. 어르신과 함께 병원에 방문해 보세요.",
                        isRecorded = true
                    )
                )
            }


            //홈 상세 화면_심리 상태 화면 // 테스트

            composable(route = Route.StateMentalDetail.route) {
                StateMentalDetail(
                    navController = navController,
                    mentals = MentalUiState(
                        mentalSummary = listOf(
                            "날씨가 좋아서 기분이 좋음",
                            "여느 때와 비슷함"
                        ),
                        isRecorded = true
                    )
                )
            }


            //홈 상세 화면_혈당 화면 // 테스트

            composable(route = Route.GlucoseDetail.route) {
                GlucoseDetail(
                    navController = navController,

                    glucose = GlucoseUiState(
                        selectedTiming = GlucoseTiming.BEFORE_MEAL,   // 공복 기본 선택

                        dailyAverageBeforeMeal = 120,   // 오늘 하루 평균 공복 혈당
                        dailyAverageAfterMeal = 120,    // 오늘 하루 평균 식후 혈당
                        recentBeforeMeal = 127,         // 어제 마지막 공복 혈당
                        recentAfterMeal = 127,          // 어제 마지막 식후 혈당
                        glucoseLevelStatusBeforeMeal = "정상",   // 공복 상태
                        glucoseLevelStatusAfterMeal = "정상",     // 식후 상태
                        isRecorded = true           // 기록 여부
                    ),

                    graph = GlucoseGraphState(

                        beforeMealGraph = listOf(60, 75, 90, 110, 200, 130, 100),  // 공복 주간 데이터
                        afterMealGraph = listOf(60, 75, 90, 110, 200, 130, 100),   // 식후 주간 데이터
                        weekLabels = listOf("일", "월", "화", "수", "목", "금", "토")

                    )


                )
            }


            // 통계
            composable(route = Route.Statistics.route) {
                StatisticsScreen(
                    navController = navController
                )
            }


            // 설정
            composable(route = Route.Settings.route) {
                SettingsScreen(
                    navController = navController,
                    onNavigateToMyDataSetting = {
                        navController.navigate(Route.MyDataSetting.route)
                    },
                    onNavigateToAnnouncement = {
                        navController.navigate(Route.Announcement.route)
                    },
                    onNavigateToCenter = {
                        navController.navigate(Route.ServiceCenter.route)
                    },
                    onNavigateToSubscribe = {
                        navController.navigate(Route.SettingSubscribe.route)
                    },
                    onNavigateToPersonalInfo = {
                        navController.navigate(Route.PersonalInfo.route)
                    },
                    onNavigateToHealthInfo = {
                        navController.navigate(Route.HealthInfo.route)
                    },
                    onNavigateToSettingAlarm = {
                        navController.navigate(Route.SettingAlarm.route)
                    }
                )
            }

            composable(
                route = Route.MyDataSetting.route
            ) {
                MyDataSettingScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    navController = navController
                )
            }

            composable(
                route = Route.MyDetail.route
            ) {
                MyDetailScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    navController = navController
                )
            }

            composable(route = Route.Announcement.route) {
                AnnouncementScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    navController = navController
                )
            }

            composable(route = Route.ServiceCenter.route) {
                ServiceCenterScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = Route.SettingSubscribe.route) {
                SettingSubscribeScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    navController = navController
                )
            }

            composable(route = Route.SubscribeDetail.route) {
                SubscribeDetailScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    navController = navController
                )
            }

            composable(route = Route.PersonalInfo.route) {
                PersonalInfoScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    navController = navController
                )
            }

            composable(route = Route.PersonalDetail.route) {
                PersonalDetailScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    navController = navController
                )
            }

            composable(route = Route.HealthInfo.route) {
                HealthInfoScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    navController = navController
                )
            }

            composable(route = Route.HealthDetail.route) {
                HealthDetailScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    navController = navController
                )
            }

            composable(route = Route.SettingAlarm.route) {
                SettingAlarmScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    navController = navController
                )
            }

            composable(route = Route.Alarm.route) {
                AlarmScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    navController = navController
                )
            }
        }

        navigation(startDestination = Route.LoginStart.route, route = "login") {


            composable(route = Route.LoginStart.route) {
                LoginStartScreen(navController, loginViewModel)
            }
            composable(route = Route.LoginPhone.route) {
                LoginPhoneScreen(navController, loginViewModel)
            }
            composable(route = Route.LoginVerification.route) {
                LoginVerificationScreen(navController, loginViewModel)
            }
            composable(route = Route.LoginMyInfo.route) {
                LoginMyInfoScreen(navController, loginViewModel)
            }
            composable(route = Route.LoginSeniorInfoScreen.route) {
                LoginSeniorInfoScreen(navController, loginSeniorViewModel)
            }
            composable(route = Route.LoginSeniorMedInfoScreen.route) {
                LoginSeniorMedInfoScreen(navController, loginSeniorViewModel)
            }

            composable(route = Route.SetCall.route) {
                SetCallScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    navController = navController,
                    loginSeniorViewModel = loginSeniorViewModel
                ) // 예시로 이름을 넣었지만, 실제로는 필요한 데이터를 전달해야 합니다.
            }

            composable(route = Route.Payment.route) {
                PaymentScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    navController = navController,
                    loginSeniorViewModel = loginSeniorViewModel
                )
            }

            composable(
                route = Route.NaverPay.route,
                arguments = listOf(navArgument("payUrl") {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val url = backStackEntry.arguments!!.getString("payUrl")!!
                NaverPayScreen(
                    payUrl = url,
                    onBack = { navController.popBackStack() },
                    navController = navController
                )
            }

            composable(route = Route.FinishSplash.route) {
                FinishSplashScreen(
                    navController = navController,
                )
            }
        }


    }

}
