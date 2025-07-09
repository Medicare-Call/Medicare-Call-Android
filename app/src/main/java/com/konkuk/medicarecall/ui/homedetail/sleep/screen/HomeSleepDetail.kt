package com.konkuk.medicarecall.ui.homedetail.meal.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.homedetail.MonthYearSelector
import com.konkuk.medicarecall.ui.homedetail.TopAppBar
import com.konkuk.medicarecall.ui.homedetail.WeeklyCalendar
import com.konkuk.medicarecall.ui.homedetail.getDatesForWeek
import com.konkuk.medicarecall.ui.homedetail.sleep.SleepUiState
import com.konkuk.medicarecall.ui.homedetail.sleep.component.HomeSleepDetailCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeSleepDetail(
    navController: NavController,
    sleeps: SleepUiState,
    modifier: Modifier = Modifier
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 52 }
    ) // 주간 달력

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {


            TopAppBar(
                title = "수면"
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {

                MonthYearSelector(
                    year = 2025,
                    month = 5,
                    onMonthClick = { /* TODO */ }
                )

                Spacer(Modifier.height(12.dp))

                HorizontalPager(
                    state = pagerState
                ) { page ->
                    val dates = getDatesForWeek(page)

                    WeeklyCalendar(
                        weekDays = listOf("일", "월", "화", "수", "목", "금", "토"),
                        dates = dates,
                        selectedDate = 4,
                        onDateSelected = { /* TODO */ }
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                HomeSleepDetailCard(
                    SleepUiState(
                        date = "2025-07-07",
                        totalSleepHours = 8,
                        totalSleepMinutes = 12,
                        bedTime = "오후 10:12",
                        wakeUpTime = "오전 06:00",
                        //isRecorded= true,     //기록 여부
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeSleepDetail() {

    HomeSleepDetail(
        navController = rememberNavController(),
        SleepUiState(
            date = "2025-07-07",
            totalSleepHours = 8,
            totalSleepMinutes = 12,
            bedTime = "오후 10:12",
            wakeUpTime = "오전 06:00",
            //isRecorded= true,     //기록 여부
        )
    )

}