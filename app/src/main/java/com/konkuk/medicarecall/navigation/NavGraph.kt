package com.konkuk.medicarecall.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.navigation.navigation
import com.konkuk.medicarecall.ui.login_info.uistate.LoginState
import com.konkuk.medicarecall.ui.home.screen.HomeScreen
import com.konkuk.medicarecall.ui.homedetail.meal.screen.MealDetail
import com.konkuk.medicarecall.ui.homedetail.meal.screen.SleepDetail
import com.konkuk.medicarecall.ui.homedetail.meal.screen.StateHealthDetail
import com.konkuk.medicarecall.ui.homedetail.medicine.DoseStatus
import com.konkuk.medicarecall.ui.homedetail.medicine.MedicineUiState
import com.konkuk.medicarecall.ui.homedetail.medicine.screen.MedicineDetail
import com.konkuk.medicarecall.ui.homedetail.sleep.SleepUiState
import com.konkuk.medicarecall.ui.homedetail.statehealth.HealthUiState
import com.konkuk.medicarecall.ui.login_info.screen.LoginMyInfoScreen
import com.konkuk.medicarecall.ui.login_info.screen.LoginPhoneScreen
import com.konkuk.medicarecall.ui.login_info.screen.LoginStartScreen
import com.konkuk.medicarecall.ui.login_info.screen.LoginVerificationScreen
import com.konkuk.medicarecall.ui.login_info.viewmodel.LoginViewModel

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
                    onNavigateToMealDetail = { navController.navigate(Route.MealDetail.route) },
                    onNavigateToMedicineDetail = { navController.navigate(Route.MedicineDetail.route) },
                    onNavigateToSleepDetail = { navController.navigate(Route.SleepDetail.route) },
                    onNavigateToStateHealthDetail = { navController.navigate(Route.StateHealthDetail.route) },
                    onNavigateToStateMentalDetail = { navController.navigate(Route.StateMentalDetail.route) },
                    onNavigateToGlucoseLevelDetail = { navController.navigate(Route.GlucoseLevelDetail.route) }
                )
            }

            // 홈 상세 화면_식사 화면
            composable(route = Route.MealDetail.route) {
                MealDetail()
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

            // 통계
            composable(route = Route.Statistics.route) {
                StatisticsScreen()
            }
            // 설정
            composable(route = Route.Settings.route) {
                SettingsScreen()
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
        }


    }

}
