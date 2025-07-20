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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.medicarecall.ui.NameBar
import com.konkuk.medicarecall.ui.home.NameDropdown
import com.konkuk.medicarecall.ui.statistics.WeeklyGlucoseUiState
import com.konkuk.medicarecall.ui.statistics.WeeklyMealUiState
import com.konkuk.medicarecall.ui.statistics.WeeklyMedicineUiState
import com.konkuk.medicarecall.ui.statistics.WeeklyMentalUiState
import com.konkuk.medicarecall.ui.statistics.component.WeekendBar
import com.konkuk.medicarecall.ui.statistics.weeklycard.WeeklyGlucoseCard
import com.konkuk.medicarecall.ui.statistics.weeklycard.WeeklyHealthCard
import com.konkuk.medicarecall.ui.statistics.weeklycard.WeeklyMealCard
import com.konkuk.medicarecall.ui.statistics.weeklycard.WeeklyMedicineCard
import com.konkuk.medicarecall.ui.statistics.weeklycard.WeeklyMentalCard
import com.konkuk.medicarecall.ui.statistics.weeklycard.WeeklySleepCard
import com.konkuk.medicarecall.ui.statistics.weeklycard.WeeklySummaryCard
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme


@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val dropdownOpened = remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.white)
    ) {
        NameBar(
            navController = navController,
            onDropdownClick = { dropdownOpened.value = !dropdownOpened.value }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            WeekendBar(title = "이번주")
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 5.dp)

            ) {
                WeeklySummaryCard()
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    WeeklyMealCard(
                        modifier = Modifier.weight(1f),
                        meal = listOf(
                            WeeklyMealUiState("아침", 7, 7),
                            WeeklyMealUiState("점심", 5, 7),
                            WeeklyMealUiState("저녁", 0, 7)
                        )
                    )


                    WeeklyMedicineCard(
                        modifier = Modifier.weight(1f),
                        medicine = listOf(
                            WeeklyMedicineUiState("혈압약", 0, 14),
                            WeeklyMedicineUiState("영양제", 4, 7),
                            WeeklyMedicineUiState("당뇨약", 21, 21)
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                WeeklyHealthCard()
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    WeeklySleepCard(
                        modifier = Modifier.weight(1f),
                        hours = 7,
                        minutes = 12
                    )

                    WeeklyMentalCard(
                        modifier = Modifier.weight(1f),
                        mental = WeeklyMentalUiState(
                            good = 4,
                            normal = 2,
                            bad = 1
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                WeeklyGlucoseCard(
                    weeklyGlucose = WeeklyGlucoseUiState(
                        beforeMealNormal = 5,
                        beforeMealHigh = 2,
                        beforeMealLow = 1,
                        afterMealNormal = 5,
                        afterMealLow = 2,
                        afterMealHigh = 0
                    )
                )


            }

            Spacer(modifier = Modifier.height(28.dp))


        }
    }
    if (dropdownOpened.value) {
        NameDropdown(
            items = listOf("김옥자", "박막례"),
            selectedName = "김옥자",
            onDismiss = { dropdownOpened.value = false },
            onItemSelected = {
                //TODO: 선택된 이름 처리
            }
        )

    }


}


@Preview(showBackground = true, heightDp = 1200)
@Composable
fun PreviewStatisticsScreen() {

    MediCareCallTheme {
        StatisticsScreen(
            navController = rememberNavController(),


            )
    }

}