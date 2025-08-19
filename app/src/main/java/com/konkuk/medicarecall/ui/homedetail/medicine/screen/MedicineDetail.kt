package com.konkuk.medicarecall.ui.homedetail.medicine.screen

import android.util.Log
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
import com.konkuk.medicarecall.ui.homedetail.medicine.MedicineViewModel
import com.konkuk.medicarecall.ui.homedetail.medicine.component.MedicineDetailCard
import com.konkuk.medicarecall.ui.homedetail.medicine.model.DoseStatus
import com.konkuk.medicarecall.ui.homedetail.medicine.model.DoseStatusItem
import com.konkuk.medicarecall.ui.homedetail.medicine.model.MedicineUiState
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MedicineDetail(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    medicineViewModel: MedicineViewModel = hiltViewModel()
) {

    // 재진입 시 오늘로 초기화
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        calendarViewModel.resetToToday()
    }

    val selectedDate by calendarViewModel.selectedDate.collectAsState()
    val elderId = homeViewModel.selectedElderId.collectAsState().value

    // 날짜/어르신 변경 시마다 로드
    LaunchedEffect(elderId, selectedDate) {
        Log.d("MED_UI", "LaunchedEffect: elderId=$elderId, date=$selectedDate")
        elderId?.let { medicineViewModel.loadMedicinesForDate(it, selectedDate) }
    }

    val uiState by medicineViewModel.state.collectAsState()
    Log.d("MED_UI", "render medicines=${uiState.items.size}")



    MedicineDetailLayout(
        navController = navController,
        selectedDate = selectedDate,
        medicines = uiState.items,
        weekDates = calendarViewModel.getCurrentWeekDates(),
        onDateSelected = { calendarViewModel.selectDate(it) },
                onMonthClick = { /* 모달 열기 */ }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MedicineDetailLayout(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedDate: LocalDate,
    medicines: List<MedicineUiState>,
    weekDates: List<LocalDate>,
    onDateSelected: (LocalDate) -> Unit,
    onMonthClick: () -> Unit,
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
                title = "복약",
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
                medicines.forEach { medicine ->
                    MedicineDetailCard(
                        medicineName = medicine.medicineName,
                        todayTakenCount = medicine.todayTakenCount,
                        todayRequiredCount = medicine.todayRequiredCount,
                        doseStatusList = medicine.doseStatusList
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMedicineDetail() {
    val dummyMedicines = listOf(
        MedicineUiState(
            medicineName = "당뇨약",
            todayTakenCount = 1,
            todayRequiredCount = 3,
            doseStatusList = listOf(
                DoseStatusItem(time = "MORNING", doseStatus = DoseStatus.TAKEN),
                DoseStatusItem(time = "LUNCH",   doseStatus = DoseStatus.SKIPPED)
            )
        ),
        MedicineUiState(
            medicineName = "혈압약",
            todayTakenCount = 1,
            todayRequiredCount = 2,
            doseStatusList = listOf(
                DoseStatusItem(time = "아침", doseStatus = DoseStatus.TAKEN)
            )
        )
    )

    MediCareCallTheme {
        MedicineDetailLayout(
            navController = rememberNavController(),
            selectedDate = LocalDate.now(),
            medicines = dummyMedicines,
            weekDates = (0..6).map { LocalDate.now().plusDays(it.toLong()) },
            onDateSelected = {},
            onMonthClick = {}
        )
    }
}