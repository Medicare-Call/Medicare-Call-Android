package com.konkuk.medicarecall.ui.homedetail.medicine.screen


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
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
import com.konkuk.medicarecall.ui.homedetail.medicine.model.DoseStatus
import com.konkuk.medicarecall.ui.homedetail.medicine.model.MedicineUiState
import com.konkuk.medicarecall.ui.homedetail.medicine.MedicineViewModel
import com.konkuk.medicarecall.ui.homedetail.medicine.component.MedicineDetailCard
import com.konkuk.medicarecall.ui.homedetail.medicine.model.DoseStatusItem
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MedicineDetail(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel = hiltViewModel<MedicineViewModel>()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val medicines by viewModel.medicines.collectAsState()



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

                medicines.forEach { medicine ->
                    MedicineDetailCard(
                        medicineName = medicine.medicineName,                  // 약 이름
                        todayTakenCount = medicine.todayTakenCount,                                   //오늘 복약 완료 횟수
                        todayRequiredCount = medicine.todayRequiredCount,     //목표 복약 횟수
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
    MedicineDetailCard(
        medicineName = "혈압약",
        todayTakenCount = 1,
        todayRequiredCount = 2,
        doseStatusList = listOf(
            DoseStatusItem(time = "아침", status = DoseStatus.TAKEN),
            DoseStatusItem(time = "점심", status = DoseStatus.NOT_RECORDED)
        )
    )
}