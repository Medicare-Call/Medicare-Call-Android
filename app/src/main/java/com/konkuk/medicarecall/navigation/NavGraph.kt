package com.konkuk.medicarecall.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.konkuk.medicarecall.ui.login_info.uistate.LoginState
import com.konkuk.medicarecall.ui.home.screen.HomeScreen
import com.konkuk.medicarecall.ui.home.screen.detail.HomeMedicineDetail
import com.konkuk.medicarecall.ui.homedetail.meal.screen.HomeMealDetail
import com.konkuk.medicarecall.ui.login_info.screen.LoginMyInfoScreen
import com.konkuk.medicarecall.ui.login_info.screen.LoginPhoneScreen
import com.konkuk.medicarecall.ui.login_senior.screen.LoginSeniorInfoScreen
import com.konkuk.medicarecall.ui.login_info.screen.LoginStartScreen
import com.konkuk.medicarecall.ui.login_info.screen.LoginVerificationScreen
import com.konkuk.medicarecall.ui.login_info.viewmodel.LoginViewModel
import com.konkuk.medicarecall.ui.login_senior.screen.LoginSeniorMedInfoScreen
import com.konkuk.medicarecall.ui.settings.screen.SettingsScreen
import com.konkuk.medicarecall.ui.statistics.screen.StatisticsScreen


@Composable
fun NavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
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
                    onNavigateToHomeMealDetail = { navController.navigate(Route.HomeMealDetail.route) },
                    onNavigateToHomeMedicineDetail = { navController.navigate(Route.HomeMedicineDetail.route) },
                    onNavigateToHomeSleepDetail = { navController.navigate(Route.HomeSleepDetail.route) },
                    onNavigateToHomeStateHealthDetail = { navController.navigate(Route.HomeStateHealthDetail.route) },
                    onNavigateToHomeStateMentalDetail = { navController.navigate(Route.HomeStateMentalDetail.route) },
                    onNavigateToHomeGlucoseLevelDetail = { navController.navigate(Route.HomeGlucoseLevelDetail.route) }
                )
            }
            // 통계
            composable(route = Route.Statistics.route) {
                StatisticsScreen()
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

            composable (
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

            composable(route = Route.SettingSubscribe.route){
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
                LoginSeniorInfoScreen(navController, loginViewModel)
            }
            composable(route = Route.LoginSeniorMedInfoScreen.route) {
                LoginSeniorMedInfoScreen(navController)
            }

            composable(route = Route.SetCall.route) {
                SetCallScreen(
                    name = "김옥자",
                    onBack = {
                        navController.popBackStack()
                    },
                    navController = navController,
                ) // 예시로 이름을 넣었지만, 실제로는 필요한 데이터를 전달해야 합니다.
            }

            composable(route = Route.Payment.route) {
                PaymentScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    navController = navController
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
        composable(route = Route.HomeMealDetail.route) {
            HomeMealDetail(navController = navController)
        }

        composable(route = Route.HomeMedicineDetail.route) {
            HomeMedicineDetail()
        }

    }

}
