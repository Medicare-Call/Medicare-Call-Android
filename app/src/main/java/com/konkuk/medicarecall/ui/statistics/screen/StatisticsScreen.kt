package com.konkuk.medicarecall.ui.statistics.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.medicarecall.ui.component.NameBar
import com.konkuk.medicarecall.ui.statistics.StatisticsUiState
import com.konkuk.medicarecall.ui.statistics.StatisticsViewModel
import com.konkuk.medicarecall.ui.statistics.component.WeekendBar
import com.konkuk.medicarecall.ui.statistics.model.WeeklyGlucoseUiState
import com.konkuk.medicarecall.ui.statistics.model.WeeklyMealUiState
import com.konkuk.medicarecall.ui.statistics.model.WeeklyMedicineUiState
import com.konkuk.medicarecall.ui.statistics.model.WeeklyMentalUiState
import com.konkuk.medicarecall.ui.statistics.model.WeeklySummaryUiState
import com.konkuk.medicarecall.ui.statistics.weeklycard.WeeklyGlucoseCard
import com.konkuk.medicarecall.ui.statistics.weeklycard.WeeklyHealthCard
import com.konkuk.medicarecall.ui.statistics.weeklycard.WeeklyMealCard
import com.konkuk.medicarecall.ui.statistics.weeklycard.WeeklyMedicineCard
import com.konkuk.medicarecall.ui.statistics.weeklycard.WeeklyMentalCard
import com.konkuk.medicarecall.ui.statistics.weeklycard.WeeklySleepCard
import com.konkuk.medicarecall.ui.statistics.weeklycard.WeeklySummaryCard
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import java.time.LocalDate

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    statisticsViewModel: StatisticsViewModel = hiltViewModel()
) {

    val currentWeek by statisticsViewModel.currentWeek.collectAsState()
    val isLatestWeek by statisticsViewModel.isLatestWeek.collectAsState()
    val isEarliestWeek by statisticsViewModel.isEarliestWeek.collectAsState()
    val uiState by statisticsViewModel.uiState.collectAsState()
    LaunchedEffect(currentWeek) {
        statisticsViewModel.getWeeklyStatistics(elderId = 1, startDate = currentWeek.first)
    }

    StatisticsScreenLayout(
        modifier = modifier,
        uiState = uiState,
        navController = navController,
        currentWeek = currentWeek,
        isLatestWeek = isLatestWeek,
        isEarliestWeek = isEarliestWeek,
        onPreviousWeek = { statisticsViewModel.showPreviousWeek() },
        onNextWeek = { statisticsViewModel.showNextWeek() },
    )
}


@Composable
fun StatisticsScreenLayout(
    modifier: Modifier = Modifier,
    uiState: StatisticsUiState,
    navController: NavHostController,
    currentWeek: Pair<LocalDate, LocalDate>,
    isLatestWeek: Boolean,
    isEarliestWeek: Boolean,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit
) {
    val dropdownOpened = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.white)
    ) {
        NameBar(
            modifier = Modifier.statusBarsPadding(),
            navController = navController,
            onDropdownClick = { dropdownOpened.value = !dropdownOpened.value }
        )

        Spacer(modifier = Modifier.height(17.dp))
        when {
            uiState.isLoading -> { /* ... */ }
            uiState.error != null -> { /* ... */ }
            uiState.summary != null -> {

                StatisticsContent(
                    summary = uiState.summary,
                    currentWeek = currentWeek,
                    isLatestWeek = isLatestWeek,
                    isEarliestWeek = isEarliestWeek,
                    onPreviousWeek = onPreviousWeek,
                    onNextWeek = onNextWeek
                )
            }
        }
    }

    if (dropdownOpened.value) { /* ... */ }
}


@Composable
private fun StatisticsContent(
    summary: WeeklySummaryUiState,
    currentWeek: Pair<LocalDate, LocalDate>,
    isLatestWeek: Boolean,
    isEarliestWeek: Boolean,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
    ) {
        WeekendBar(
            currentWeek = currentWeek,
            isLatestWeek = isLatestWeek,
            isEarliestWeek = isEarliestWeek,
            onPreviousWeek = onPreviousWeek,
            onNextWeek = onNextWeek
        )
        Spacer(modifier = Modifier.height(20.dp))
        WeeklySummaryCard(summary = summary)
        Spacer(modifier = Modifier.height(10.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            WeeklyMealCard(
                modifier = Modifier.weight(1f),
                meal = summary.weeklyMeals
            )
            WeeklyMedicineCard(
                modifier = Modifier.weight(1f),
                medicine = summary.weeklyMedicines
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        WeeklyHealthCard(healthNote = summary.weeklyHealthNote)
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            WeeklySleepCard(
                modifier = Modifier.weight(1f),
                summary = summary
            )
            WeeklyMentalCard(
                modifier = Modifier.weight(1f),
                mental = summary.weeklyMental
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        WeeklyGlucoseCard(weeklyGlucose = summary.weeklyGlucose)
        Spacer(modifier = Modifier.height(70.dp))
    }
}


@Preview(name = "주간 통계 - 기록 있음", showBackground = true, heightDp = 1200)
@Composable
fun PreviewStatisticsScreen_Recorded() {
    // ...
    val dummySummary = WeeklySummaryUiState(
        weeklyMealRate = 65,
        weeklyMedicineRate = 57,
        weeklyHealthIssueCount = 3,
        weeklyUnansweredCount = 8,
        weeklyMeals = listOf(
            WeeklyMealUiState("아침", 7, 7),
            WeeklyMealUiState("점심", 5, 7),
            WeeklyMealUiState("저녁", 1, 7)
        ),
        weeklyMedicines = listOf(
            WeeklyMedicineUiState("혈압약", 0, 14),
            WeeklyMedicineUiState("영양제", 4, 7),
            WeeklyMedicineUiState("당뇨약", 21, 21)
        ),

        weeklyHealthNote = "아침·점심 복약과 식사는 문제 없으나, 저녁 약 복용이 늦어질 우려가 있어요. 전반적으로 양호하나 피곤과 후흡곤란을 호소하셨으므로 휴식과 보호자 확인이 필요해요.",
        weeklySleepHours = 7,
        weeklySleepMinutes = 12,
        weeklyMental = WeeklyMentalUiState(good = 4, normal = 4, bad = 4),
        weeklyGlucose = WeeklyGlucoseUiState(
            beforeMealNormal = 5, beforeMealHigh = 2, beforeMealLow = 1,
            afterMealNormal = 5, afterMealHigh = 0, afterMealLow = 2
        )
    )
    val dummyUiState = StatisticsUiState(summary = dummySummary)

    MediCareCallTheme {
        StatisticsScreenLayout(
            uiState = dummyUiState,
            navController = rememberNavController(),
            currentWeek = Pair(LocalDate.now(), LocalDate.now().plusDays(6)),
            isLatestWeek = false,
            isEarliestWeek = false,
            onPreviousWeek = {},
            onNextWeek = {}
        )
    }
}


@Preview(name = "주간 통계 - 미기록", showBackground = true, heightDp = 1200)
@Composable
fun PreviewStatisticsScreen_Unrecorded() {
    // ...


    MediCareCallTheme {
        StatisticsScreenLayout(
            uiState = StatisticsUiState(summary = WeeklySummaryUiState.EMPTY),
            navController = rememberNavController(),
            currentWeek = Pair(LocalDate.now(), LocalDate.now().plusDays(6)),
            isLatestWeek = true,
            isEarliestWeek = true,
            onPreviousWeek = {},
            onNextWeek = {}
        )
    }
}