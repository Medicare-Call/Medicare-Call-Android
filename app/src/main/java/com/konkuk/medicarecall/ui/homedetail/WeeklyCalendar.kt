package com.konkuk.medicarecall.ui.homedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun WeeklyCalendar(

    calendarUiState: CalendarUiState,
    onDateSelected: (Int) -> Unit

) {
    val weekDays = listOf("일", "월", "화", "수", "목", "금", "토")

    Column(modifier = Modifier.fillMaxWidth()) {


        // 요일 (일~토)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            weekDays.forEach { day ->

                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center


                ) {

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 6.5.dp),
                        text = day,
                        style = MediCareCallTheme.typography.SB_18,
                    )
                }
            }
        }

        // 해당 주 날짜
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            calendarUiState.weekDates.forEach { date ->
                Box(
                    modifier = Modifier
                        .size(29.dp)
                        .clip(CircleShape)
                        .background(
                            if (date == calendarUiState.selectedDate) MediCareCallTheme.colors.main else Color.Transparent
                        )
                        .clickable { onDateSelected(date) },
                    contentAlignment = Alignment.Center
                ) {


                    Text(
                        modifier = Modifier,
                        text = "$date",
                        style = MediCareCallTheme.typography.R_18,
                        color = if (date == calendarUiState.selectedDate) Color.White
                                else MediCareCallTheme.colors.gray4
                    )
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewWeeklyCalendar() {
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
