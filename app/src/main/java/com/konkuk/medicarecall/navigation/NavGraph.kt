package com.konkuk.medicarecall.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.konkuk.medicarecall.ui.home.screen.screen.HomeScreen
import com.konkuk.medicarecall.ui.homedetail.meal.screen.HomeMealDetail
import com.konkuk.medicarecall.ui.home.screen.screen.detail.HomeMedicineDetail
import com.konkuk.medicarecall.ui.homedetail.medicine.DoseStatus
import com.konkuk.medicarecall.ui.homedetail.medicine.MedicineUiState
import com.konkuk.medicarecall.ui.homedetail.medicine.screen.HomeMedicineDetail
import com.konkuk.medicarecall.ui.settings.screen.SettingsScreen
import com.konkuk.medicarecall.ui.statistics.screen.StatisticsScreen


@Composable
fun NavGraph(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController, startDestination = Route.Home.route, // 시작 화면
        modifier = modifier
    ) {
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

        // 홈 상세 화면_복용 화면 // 테스트 용 더미 데이터
        composable(route = Route.HomeMedicineDetail.route) {
            HomeMedicineDetail(
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

        // 통계
        composable(route = Route.Statistics.route) {
            StatisticsScreen()
        }
        // 설정
        composable(route = Route.Settings.route) {
            SettingsScreen()
        }
        composable(route = Route.HomeMealDetail.route) {
            HomeMealDetail()
        }

    }

}
