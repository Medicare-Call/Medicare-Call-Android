package com.konkuk.medicarecall.ui.homedetail.statemental.screen

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
import com.konkuk.medicarecall.ui.homedetail.statemental.MentalViewModel
import com.konkuk.medicarecall.ui.homedetail.statemental.component.StateMentalDetailCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StateMentalDetail(
    navController: NavHostController
) {
    val viewModel = hiltViewModel<MentalViewModel>()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val mental by viewModel.mental.collectAsState()

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

                StateMentalDetailCard(
                    mental = mental
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewStateMentalDetail() {

    StateMentalDetail(
        navController = rememberNavController()
    )

}