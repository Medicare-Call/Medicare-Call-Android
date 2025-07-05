package com.konkuk.medicarecall.ui.homedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun WeeklyCalendar(

    weekDays: List<String>,     // 요일
    dates: List<Int>,           // 해당 주 날짜
    selectedDate: Int,          // 현재 선택된 날짜

    onDateSelected: (Int) -> Unit // 날짜 클릭 시 선택된 날짜

) {

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
            dates.forEach { date ->
                Box(
                    modifier = Modifier
                        .size(29.dp)
                        .clip(CircleShape)
                        .background(
                            if (date == selectedDate) MediCareCallTheme.colors.main else Color.Transparent
                        )
                        .clickable { onDateSelected(date) },
                    contentAlignment = Alignment.Center
                ) {


                    Text(
                        modifier = Modifier,
                        text = "$date",
                        style = MediCareCallTheme.typography.R_18,
                        color = if (date == selectedDate) Color.White
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
        weekDays = listOf("일", "월", "화", "수", "목", "금", "토"),
        dates = listOf(4, 5, 6, 7, 8, 9, 10),
        selectedDate = 7,
        onDateSelected = {  }
    )
}
