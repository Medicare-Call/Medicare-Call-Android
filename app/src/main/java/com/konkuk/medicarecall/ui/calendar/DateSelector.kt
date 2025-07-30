package com.konkuk.medicarecall.ui.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import java.time.LocalDate

@Composable
fun DateSelector(
    selectedDate: LocalDate,
    onMonthClick: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    val year = selectedDate.year
    val month = selectedDate.monthValue

    Row(
        modifier = Modifier
            .fillMaxWidth(),

        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${year}년 ${month}월",
            style = MediCareCallTheme.typography.SB_20,
            color = MediCareCallTheme.colors.gray9,
            modifier = Modifier.padding(end = 4.dp)
        )
        Icon(
            modifier = Modifier
                .clickable { showDatePicker = true }, // 여기서 모달 열기
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = "Month Select",
            tint = MediCareCallTheme.colors.gray4
        )
    }

    // 모달 표시 조건
    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = {
                onDateSelected(it)
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewDateSelector() {
    val fakeDate = LocalDate.of(2025, 7, 22)
    DateSelector(
        selectedDate = fakeDate,
        onMonthClick = { },
        onDateSelected = { /* no-op */ }
    )

}