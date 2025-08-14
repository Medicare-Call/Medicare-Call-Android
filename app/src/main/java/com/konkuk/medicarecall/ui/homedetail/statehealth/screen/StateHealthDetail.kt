package com.konkuk.medicarecall.ui.homedetail.statehealth.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.konkuk.medicarecall.ui.calendar.CalendarViewModel
import com.konkuk.medicarecall.ui.calendar.DateSelector
import com.konkuk.medicarecall.ui.calendar.WeeklyCalendar
import com.konkuk.medicarecall.ui.homedetail.TopAppBar
import com.konkuk.medicarecall.ui.homedetail.statehealth.HealthViewModel
import com.konkuk.medicarecall.ui.homedetail.statehealth.component.StateHealthDetailCard
import com.konkuk.medicarecall.ui.homedetail.statehealth.model.HealthUiState
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import java.time.LocalDate


@Composable
fun StateHealthDetail(
    navController: NavHostController,
    calendarViewModel: CalendarViewModel,
    healthViewModel: HealthViewModel = hiltViewModel()
) {
    val selectedDate by calendarViewModel.selectedDate.collectAsState()
    val health by healthViewModel.health.collectAsState()

    LaunchedEffect(selectedDate) {
        healthViewModel.loadHealthDataForDate(selectedDate)
    }

    StateHealthDetailLayout(
        modifier = Modifier,
        navController = navController,
        selectedDate = selectedDate,
        health = health,
        weekDates = calendarViewModel.getCurrentWeekDates(),
        onDateSelected = { calendarViewModel.selectDate(it) }
    )
}


@Composable
fun StateHealthDetailLayout(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedDate: LocalDate,
    health: HealthUiState,
    weekDates: List<LocalDate>,
    onDateSelected: (LocalDate) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        TopAppBar(
            title = "건강징후",
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
                onDateSelected = onDateSelected
            )
            Spacer(Modifier.height(12.dp))
            WeeklyCalendar(
                calendarUiState = CalendarUiState(
                    currentYear = selectedDate.year,
                    currentMonth = selectedDate.monthValue,
                    weekDates = weekDates,
                    selectedDate = selectedDate
                ),
                onDateSelected = onDateSelected
            )
            Spacer(modifier = Modifier.height(24.dp))
            StateHealthDetailCard(
                health = health
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewStateHealthDetail() {
    MediCareCallTheme {
        StateHealthDetailLayout(
            navController = rememberNavController(),
            selectedDate = LocalDate.now(),
            health = HealthUiState.EMPTY,
            weekDates = (0..6).map { LocalDate.now().plusDays(it.toLong()) },
            onDateSelected = {}
        )
    }
}