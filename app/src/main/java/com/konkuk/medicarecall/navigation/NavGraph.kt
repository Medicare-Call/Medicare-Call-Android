package com.konkuk.medicarecall.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.konkuk.medicarecall.ui.alarm.screen.AlarmScreen
import com.konkuk.medicarecall.ui.calendar.CalendarViewModel
import com.konkuk.medicarecall.ui.home.screen.HomeScreen
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.screen.GlucoseDetail
import com.konkuk.medicarecall.ui.homedetail.meal.screen.MealDetail
import com.konkuk.medicarecall.ui.homedetail.medicine.screen.MedicineDetail
import com.konkuk.medicarecall.ui.homedetail.sleep.screen.SleepDetail
import com.konkuk.medicarecall.ui.homedetail.statehealth.screen.StateHealthDetail
import com.konkuk.medicarecall.ui.homedetail.statemental.screen.StateMentalDetail
import com.konkuk.medicarecall.ui.login_care_call.screen.SetCallScreen
import com.konkuk.medicarecall.ui.login_info.screen.LoginMyInfoScreen
import com.konkuk.medicarecall.ui.login_info.screen.LoginPhoneScreen
import com.konkuk.medicarecall.ui.login_info.screen.LoginStartScreen
import com.konkuk.medicarecall.ui.login_info.screen.LoginVerificationScreen
import com.konkuk.medicarecall.ui.login_info.uistate.LoginState
import com.konkuk.medicarecall.ui.login_info.viewmodel.LoginViewModel
import com.konkuk.medicarecall.ui.login_payment.screen.FinishSplashScreen
import com.konkuk.medicarecall.ui.login_payment.screen.NaverPayScreen
import com.konkuk.medicarecall.ui.login_payment.screen.PaymentScreen
import com.konkuk.medicarecall.ui.login_senior.LoginSeniorViewModel
import com.konkuk.medicarecall.ui.login_senior.screen.LoginSeniorInfoScreen
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
    val calendarViewModel: CalendarViewModel = hiltViewModel()

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


            // 홈 상세 화면_복용 화면
            composable(route = Route.MedicineDetail.route) {
                MedicineDetail(navController = navController)
            }

            //홈 상세 화면_수면 화면
            composable(route = Route.SleepDetail.route) {
                SleepDetail(navController = navController)
            }


            //홈 상세 화면_건강 징후 화면
            composable(route = Route.StateHealthDetail.route) {
                StateHealthDetail(navController = navController)
            }


            //홈 상세 화면_심리 상태 화면
            composable(route = Route.StateMentalDetail.route) {
                StateMentalDetail(navController = navController)
            }


            //홈 상세 화면_혈당 화면

            composable(route = Route.GlucoseDetail.route) {
                GlucoseDetail(navController = navController, calendarViewModel = calendarViewModel)
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

            composable(route = Route.NaverPay.route) {
                NaverPayScreen(
                    onBack = {
                        navController.popBackStack()
                    },
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
