package com.konkuk.medicarecall.ui.calendar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import java.time.Instant
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val customColors = DatePickerDefaults.colors(
        selectedDayContainerColor = MediCareCallTheme.colors.main,
        selectedDayContentColor = Color.White,
        containerColor = Color.White,
        todayContentColor = MediCareCallTheme.colors.black,
        todayDateBorderColor = MediCareCallTheme.colors.main,
    )

    //세계 표준시
    val utc = java.time.ZoneOffset.UTC

    val initialMillis = remember(initialDate) {
        initialDate.atStartOfDay(utc).toInstant().toEpochMilli()
    }
    val initialMonthMillis = remember(initialDate) {
        initialDate.withDayOfMonth(1).atStartOfDay(utc).toInstant().toEpochMilli()
    }

    // initialDate가 바뀌면 상태 재생성
    androidx.compose.runtime.key(initialMillis) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = initialMillis,
            initialDisplayedMonthMillis = initialMonthMillis
        )

        val confirmDate = datePickerState.selectedDateMillis?.let {
            Instant.ofEpochMilli(it).atZone(utc).toLocalDate()
        }

        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MediCareCallTheme.colors.black
                    ),
                    onClick = {
                        confirmDate?.let(onDateSelected)
                        onDismiss()
                    }
                ) { Text("확인") }
            },
            dismissButton = {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MediCareCallTheme.colors.black
                    ),
                    onClick = onDismiss
                ) { Text("취소") }
            }
        ) {
            Surface(shape = RoundedCornerShape(16.dp), color = Color.White) {
                DatePicker(state = datePickerState, colors = customColors)
            }
        }
    }
}


@Preview
@Composable
fun PreviewDatePickerModal() {
    DatePickerModal(
        initialDate = LocalDate.now(),
        onDateSelected = {},
        onDismiss = {}
    )
}