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
import com.konkuk.medicarecall.ui.homedetail.CalendarUiState
import com.konkuk.medicarecall.ui.homedetail.MonthYearSelector
import com.konkuk.medicarecall.ui.homedetail.TopAppBar
import com.konkuk.medicarecall.ui.homedetail.WeeklyCalendar
import com.konkuk.medicarecall.ui.homedetail.getDatesForWeek
import com.konkuk.medicarecall.ui.homedetail.statehealth.HealthUiState
import com.konkuk.medicarecall.ui.homedetail.statehealth.component.StateHealthDetailCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StateHealthDetail(
    navController: NavController,
    healths: HealthUiState
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
                title = "건강징후"
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

                StateHealthDetailCard(
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
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewStateHealthDetail() {

    StateHealthDetail(
        navController = rememberNavController(),
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