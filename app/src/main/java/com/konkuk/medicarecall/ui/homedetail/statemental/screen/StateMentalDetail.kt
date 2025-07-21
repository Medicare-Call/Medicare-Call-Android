package com.konkuk.medicarecall.ui.homedetail.meal.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.medicarecall.ui.homedetail.CalendarUiState
import com.konkuk.medicarecall.ui.homedetail.MonthYearSelector
import com.konkuk.medicarecall.ui.homedetail.TopAppBar
import com.konkuk.medicarecall.ui.homedetail.WeeklyCalendar
import com.konkuk.medicarecall.ui.homedetail.getDatesForWeek
import com.konkuk.medicarecall.ui.homedetail.statehealth.component.StateMentalDetailCard
import com.konkuk.medicarecall.ui.homedetail.statemental.MentalUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StateMentalDetail(
    navController: NavHostController,
    mentals: MentalUiState
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
                .statusBarsPadding()
        ) {


            TopAppBar(
                title = "심리상태 요약",
                navController = navController
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
                        calendarUiState = CalendarUiState(
                            year = 2025,
                            month = 5,
                            weekDates = listOf(4, 5, 6, 7, 8, 9, 10),
                            selectedDate = 7
                        ),
                        onDateSelected = { /* 클릭 테스트용 */ }
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                StateMentalDetailCard(
                    mentals = MentalUiState(
                        mentalSummary = listOf(
                            "날씨가 좋아서 기분이 좋음",
                            "여느 때와 비슷함"
                        ),

                        isRecorded = true
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewStateMentalDetail() {

    StateMentalDetail(
        navController = rememberNavController(),
        mentals = MentalUiState(
           mentalSummary = listOf(
               "날씨가 좋아서 기분이 좋음",
               "여느 때와 비슷함"
            ),
            isRecorded = true
        )
    )

}