package com.konkuk.medicarecall.ui.homedetail.meal.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.medicarecall.ui.calendar.CalendarUiState
import com.konkuk.medicarecall.ui.calendar.DateSelector
import com.konkuk.medicarecall.ui.homedetail.TopAppBar
import com.konkuk.medicarecall.ui.calendar.WeeklyCalendar
import com.konkuk.medicarecall.ui.homedetail.meal.MealViewModel
import com.konkuk.medicarecall.ui.homedetail.meal.component.MealDetailCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MealDetail(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel = hiltViewModel<MealViewModel>()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val weekDates = viewModel.getCurrentWeekDates()

    val meals by viewModel.meals.collectAsState()

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
                title = "식사",
                navController = navController
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {

                DateSelector(
                    selectedDate = selectedDate,
                    onMonthClick = { /* 모달 열기 */ },
                    onDateSelected = { viewModel.selectDate(it) }
                )


                Spacer(Modifier.height(12.dp))



                WeeklyCalendar(
                    calendarUiState = CalendarUiState(
                        currentYear = selectedDate.year,
                        currentMonth = selectedDate.monthValue,
                        weekDates = viewModel.getCurrentWeekDates(),  // selectedDate 기준
                        selectedDate = selectedDate
                    ),
                    onDateSelected = { viewModel.selectDate(it) }
                )

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
    MealDetail(navController = rememberNavController())

}