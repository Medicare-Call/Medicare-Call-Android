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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.medicarecall.ui.calendar.CalendarUiState
import com.konkuk.medicarecall.ui.calendar.CalendarViewModel
import com.konkuk.medicarecall.ui.calendar.DateSelector
import com.konkuk.medicarecall.ui.calendar.WeeklyCalendar
import com.konkuk.medicarecall.ui.home.HomeViewModel
import com.konkuk.medicarecall.ui.homedetail.TopAppBar
import com.konkuk.medicarecall.ui.homedetail.statemental.MentalViewModel
import com.konkuk.medicarecall.ui.homedetail.statemental.component.StateMentalDetailCard
import com.konkuk.medicarecall.ui.homedetail.statemental.model.MentalUiState
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StateMentalDetail(
    navController: NavHostController,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    mentalViewModel: MentalViewModel = hiltViewModel(),

) {
    val homeEntry = remember(navController.currentBackStackEntry) {
        navController.getBackStackEntry("main")
    }
    val homeViewModel: HomeViewModel = hiltViewModel(homeEntry)

    // 재진입 시 오늘로 초기화
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        calendarViewModel.resetToToday()
    }

    val selectedDate by calendarViewModel.selectedDate.collectAsState()
    val mental by mentalViewModel.mental.collectAsState()

    // 네임드롭에서 선택된 어르신
    val elderId by homeViewModel.selectedElderId.collectAsState()

    // 날짜/어르신 변경 시마다 로드
    LaunchedEffect(elderId, selectedDate) {
        elderId?.let { id ->
            mentalViewModel.loadMentalDataForDate(id, selectedDate)
        }
    }


    StateMentalDetailLayout(
        navController = navController,
        selectedDate = selectedDate,
        mental = mental,
        weekDates = calendarViewModel.getCurrentWeekDates(),
        onDateSelected = { calendarViewModel.selectDate(it) },
        onMonthClick = { /* 모달 열기 */ }
    )
}


@Composable
fun StateMentalDetailLayout(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedDate: LocalDate,
    mental: MentalUiState,
    weekDates: List<LocalDate>,
    onDateSelected: (LocalDate) -> Unit,
    onMonthClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
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
                    onMonthClick = onMonthClick,
                    onDateSelected = onDateSelected
                )
                Spacer(Modifier.height(10.dp))
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
    StateMentalDetailLayout(
        navController = rememberNavController(),
        selectedDate = LocalDate.now(),
        mental = MentalUiState.EMPTY,
        weekDates = (0..6).map { LocalDate.now().plusDays(it.toLong()) },
        onDateSelected = {},
        onMonthClick = {}
    )
}