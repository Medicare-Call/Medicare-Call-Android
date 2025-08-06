package com.konkuk.medicarecall.ui.statistics.weeklycard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.statistics.model.WeeklyGlucoseUiState
import com.konkuk.medicarecall.ui.statistics.model.WeeklyMentalUiState
import com.konkuk.medicarecall.ui.statistics.model.WeeklySummaryUiState
import com.konkuk.medicarecall.ui.theme.LocalMediCareCallShadowProvider
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow

@Composable
fun WeeklySummaryCard(
    modifier: Modifier = Modifier,
    summary: WeeklySummaryUiState
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .figmaShadow(
                group = LocalMediCareCallShadowProvider.current.shadow03,
                cornerRadius = 14.dp
            ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                "주간 요약통계",
                style = MediCareCallTheme.typography.R_15,
                color = MediCareCallTheme.colors.gray5,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeeklySummaryItem(title = "식사율", value = summary.weeklyMealRate, unit = "%")
                WeeklySummaryItem(title = "복약률", value = summary.weeklyMedicineRate, unit = "%")
                WeeklySummaryItem(title = "건강징후", value = summary.weeklyHealthIssueCount, unit = "건")
                WeeklySummaryItem(title = "미응답", value = summary.weeklyUnansweredCount, unit = "건")
            }
        }
    }
}


@Composable
private fun WeeklySummaryItem(
    modifier: Modifier = Modifier,
    title: String,
    value: Int,
    unit: String
) {
    Column(
        modifier = modifier,

    ) {
        Text(
            text = title,
            style = MediCareCallTheme.typography.R_14,
            color = MediCareCallTheme.colors.gray6,
        )

        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = value.toString(),
                style = MediCareCallTheme.typography.SB_22,
                color = MediCareCallTheme.colors.black,
            )
            Text(
                text = unit,
                modifier = Modifier.padding(start = 2.dp, bottom = 2.dp),
                style = MediCareCallTheme.typography.R_16,
                color = MediCareCallTheme.colors.black,
            )
        }
    }
}

@Preview
@Composable
fun PreviewWeeklySummaryCard() {
    val dummySummary = WeeklySummaryUiState(
        weeklyMealRate = 65, weeklyMedicineRate = 57, weeklyHealthIssueCount = 3, weeklyUnansweredCount = 8,
        weeklyMeals = emptyList(), weeklyMedicines = emptyList(), weeklyHealthNote = "",
        weeklySleepHours = 0, weeklySleepMinutes = 0,
        weeklyMental = WeeklyMentalUiState(0, 0, 0),
        weeklyGlucose = WeeklyGlucoseUiState(0, 0, 0, 0, 0, 0)
    )
    WeeklySummaryCard(summary = dummySummary)
}