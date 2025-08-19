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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.konkuk.medicarecall.ui.homedetail.meal.MealViewModel
import com.konkuk.medicarecall.ui.homedetail.meal.component.MealDetailCard
import com.konkuk.medicarecall.ui.homedetail.meal.model.MealUiState
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MealDetail(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    mealViewModel: MealViewModel = hiltViewModel()
) {
    // 재진입 시 오늘로 초기화
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        calendarViewModel.resetToToday()
    }

    val selectedDate by calendarViewModel.selectedDate.collectAsState()
    val elderId = homeViewModel.selectedElderId.collectAsState().value

    // 날짜/어르신 변경 시마다 로드
    LaunchedEffect(elderId, selectedDate) {
        elderId?.let { id -> mealViewModel.loadMealsForDate(id, selectedDate) }
    }

    val meals by mealViewModel.meals.collectAsState()

    MealDetailLayout(
        navController = navController,
        selectedDate = selectedDate,
        meals = meals,
        weekDates = calendarViewModel.getCurrentWeekDates(),
        onDateSelected = { calendarViewModel.selectDate(it) },
        onMonthClick = { /* 모달 열기 */ }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MealDetailLayout(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedDate: LocalDate,
    meals: List<MealUiState>,
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
                    onMonthClick = onMonthClick,
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


@Preview(name = "식사 - 기록 있음", showBackground = true)
@Composable
fun PreviewMealDetail_Recorded() {
    val dummyMeals = listOf(
        MealUiState(
            mealTime = "아침",
            description = "간단히 밥과 반찬을 드셨어요.",
            isRecorded = true,
            isEaten = true
        ),
        MealUiState(
            mealTime = "점심",
            description = "식사하지 않으셨어요.",
            isRecorded = true,
            isEaten = false
        ),
        MealUiState(
            mealTime = "저녁",
            description = "죽을 드셨어요.",
            isRecorded = true,
            isEaten = true
        )
    )
    val selectedDate = LocalDate.of(2025, 5, 7)
    val weekDates = (0..6).map { selectedDate.plusDays(it.toLong() - selectedDate.dayOfWeek.value % 7) }

    MediCareCallTheme {
        MealDetailLayout(
            navController = rememberNavController(),
            selectedDate = selectedDate,
            meals = dummyMeals,
            weekDates = weekDates,
            onDateSelected = {},
            onMonthClick = {}
        )
    }
}

@Preview(name = "식사 - 미기록 화면", showBackground = true)
@Composable
fun PreviewMealDetail_Unrecorded() {
    val dummyMeals = listOf(
        MealUiState(
            mealTime = "아침",
            description = "식사 기록 전이에요.",
            isRecorded = false,
            isEaten = null
        ),
        MealUiState(
            mealTime = "점심",
            description = "식사 기록 전이에요.",
            isRecorded = false,
            isEaten = null
        ),
        MealUiState(
            mealTime = "저녁",
            description = "식사 기록 전이에요.",
            isRecorded = false,
            isEaten = null
        )
    )
    val selectedDate = LocalDate.of(2025, 5, 7)
    val weekDates = (0..6).map { selectedDate.plusDays(it.toLong() - selectedDate.dayOfWeek.value % 7) }


    MediCareCallTheme {
        MealDetailLayout(
            navController = rememberNavController(),
            selectedDate = selectedDate,
            meals = dummyMeals,
            weekDates = weekDates,
            onDateSelected = {},
            onMonthClick = {}
        )
    }
}