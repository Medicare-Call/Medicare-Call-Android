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
import com.konkuk.medicarecall.ui.homedetail.MonthYearSelector
import com.konkuk.medicarecall.ui.homedetail.TopAppBar
import com.konkuk.medicarecall.ui.homedetail.WeeklyCalendar
import com.konkuk.medicarecall.ui.homedetail.getDatesForWeek
import com.konkuk.medicarecall.ui.homedetail.meal.MealUiState
import com.konkuk.medicarecall.ui.homedetail.meal.component.MealDetailCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MealDetail(
    modifier: Modifier = Modifier
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 52 }
    ) // 주간 달력

    val meals = listOf(
        MealUiState("아침", "간단히 밥과 반찬을 드셨어요.", true, true),
        MealUiState("점심", "식사하지 않으셨어요.", true, true),
        MealUiState("저녁", "", false,false )
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {


            TopAppBar(
                title = "식사"
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

                meals.forEach { meal ->
                    MealDetailCard(
                        mealTime = meal.mealTime,       // 아침 점심 저녁
                        description = meal.description, // 식사 내용
                        isRecorded = meal.isRecorded,   // 식사 기록 여부
                        isEaten = meal.isEaten          // 식사 유무
                    )

                    Spacer(modifier = Modifier.height(8.dp))


                }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMealDetail() {
    MealDetail()

}