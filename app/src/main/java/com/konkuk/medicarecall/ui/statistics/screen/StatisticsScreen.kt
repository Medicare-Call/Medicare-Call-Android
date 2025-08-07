package com.konkuk.medicarecall.ui.statistics.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.medicarecall.ui.component.NameBar
import com.konkuk.medicarecall.ui.home.NameDropdown
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
    viewModel: StatisticsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState


    LaunchedEffect(key1 = Unit) {
        // TODO: elderId와 조회할 날짜 전달
        viewModel.getWeeklyStatistics(elderId = 1, startDate = LocalDate.now())
    }


    StatisticsScreenLayout(
        modifier = modifier,
        uiState = uiState,
        navController = navController,
        onElderSelected = { /* TODO: ViewModel에 어르신 변경 요청 */ }
    )
}


@Composable
fun StatisticsScreenLayout(
    modifier: Modifier = Modifier,
    uiState: StatisticsUiState,
    navController: NavHostController,
    onElderSelected: (String) -> Unit
) {
    val dropdownOpened = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.white)
            .statusBarsPadding()
    ) {
        NameBar(
            navController = navController,
            onDropdownClick = { dropdownOpened.value = !dropdownOpened.value }
        )

        Spacer(modifier = Modifier.height(20.dp))


        when {
            // 로딩 중일 때
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            // 에러 발생 시
            uiState.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = uiState.error)
                }
            }
            // 데이터 로딩 성공 시
            uiState.summary != null -> {

                StatisticsContent(summary = uiState.summary)
            }
        }
    }

    if (dropdownOpened.value) {
        NameDropdown(
            items = listOf("김옥자", "박막례"),
            selectedName = "김옥자",
            onDismiss = { dropdownOpened.value = false },
            onItemSelected = onElderSelected
        )
    }
}


@Composable
private fun StatisticsContent(summary: WeeklySummaryUiState) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        WeekendBar(title = "이번주")
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.padding(horizontal = 5.dp)
        ) {

            WeeklySummaryCard(summary = summary)
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
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
}


@Preview(showBackground = true, heightDp = 1200)
@Composable
fun PreviewStatisticsScreen() {

    val dummySummary = WeeklySummaryUiState(
        weeklyMealRate = 65,
        weeklyMedicineRate = 57,
        weeklyHealthIssueCount = 3,
        weeklyUnansweredCount = 8,
        weeklyMeals = listOf(
            WeeklyMealUiState("아침", 7, 7),
            WeeklyMealUiState("점심", 5, 7),
            WeeklyMealUiState("저녁", 0, 7)
        ),
        weeklyMedicines = listOf(
            WeeklyMedicineUiState("혈압약", 0, 14),
            WeeklyMedicineUiState("영양제", 4, 7),
            WeeklyMedicineUiState("당뇨약", 21, 21)
        ),
        weeklyHealthNote = "아침·점심 복약과 식사는 문제 없으나, 저녁 약 복용이 늦어질 우려가 있어요. 전반적으로 양호하나 피곤과 호흡곤란을 호소하셨으므로 휴식과 보호자 확인이 필요해요.",
        weeklySleepHours = 7,
        weeklySleepMinutes = 12,
        weeklyMental = WeeklyMentalUiState(good = 4, normal = 2, bad = 1),
        weeklyGlucose = WeeklyGlucoseUiState(
            beforeMealNormal = 5,
            beforeMealHigh = 2,
            beforeMealLow = 1,
            afterMealNormal = 5,
            afterMealLow = 2,
            afterMealHigh = 0
        )
    )
    val dummyUiState = StatisticsUiState(summary = dummySummary)

    MediCareCallTheme {
        StatisticsScreenLayout(
            uiState = dummyUiState,
            navController = rememberNavController(),
            onElderSelected = {}
        )
    }
}
