package com.konkuk.medicarecall.ui.homedetail.medicine.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.konkuk.medicarecall.ui.homedetail.medicine.DoseStatus
import com.konkuk.medicarecall.ui.homedetail.medicine.MedicineUiState
import com.konkuk.medicarecall.ui.homedetail.medicine.component.MedicineDetailCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MedicineDetail(
    navController: NavHostController,
    medicines: List<MedicineUiState>
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
                title = "복용",
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

                medicines.forEach { medicine ->
                    MedicineDetailCard(
                        medicineName = medicine.medicineName,                  // 약 이름
                        todayTakenCount = 2,                                   //오늘 복약 완료 횟수
                        todayRequiredCount = medicine.todayRequiredCount,     //오늘 복약 해야 할 횟수
                        doseStatusList = medicine.doseStatusList.orEmpty()

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
    MedicineDetail(
        navController = rememberNavController(), // 프리뷰 전용
        medicines = listOf(
            MedicineUiState(
                medicineName = "당뇨약",
                todayTakenCount = 2,
                todayRequiredCount = 3,
                nextDoseTime = null,
                doseStatusList = listOf(
                    DoseStatus.TAKEN,
                    DoseStatus.TAKEN,
                    DoseStatus.NOT_RECORDED
                )
            ),
            MedicineUiState(
                medicineName = "혈압약",
                todayTakenCount = 0,
                todayRequiredCount = 2,
                nextDoseTime = null,
                doseStatusList = listOf(
                    DoseStatus.SKIPPED,
                    DoseStatus.NOT_RECORDED
                )
            )
        )
    )
}