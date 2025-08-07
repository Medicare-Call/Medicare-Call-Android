package com.konkuk.medicarecall.ui.statistics.weeklycard


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.statistics.model.WeeklyGlucoseUiState
import com.konkuk.medicarecall.ui.statistics.model.WeeklyMentalUiState
import com.konkuk.medicarecall.ui.statistics.model.WeeklySummaryUiState
import com.konkuk.medicarecall.ui.theme.LocalMediCareCallShadowProvider
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow

@Composable
fun WeeklySleepCard(
    modifier: Modifier = Modifier,
    summary: WeeklySummaryUiState
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .figmaShadow(
                group = LocalMediCareCallShadowProvider.current.shadow03,
                cornerRadius = 14.dp
            ),

        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp)

    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)

        ) {

            //1) Title: 평균 수면

            Text(
                "평균수면",
                style = MediCareCallTheme.typography.R_15,
                color = MediCareCallTheme.colors.gray5,
            )
            Spacer(modifier = Modifier.height(4.dp))

            // 2) 아이콘
            Icon(
                painter = painterResource(id = R.drawable.ic_moon),
                contentDescription = "moon",
                modifier = Modifier
                    .size(40.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(4.dp))
            // 3) 시간

            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "${summary.weeklySleepHours}",
                    style = MediCareCallTheme.typography.SB_22,
                    color = MediCareCallTheme.colors.gray8,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "시간",
                    style = MediCareCallTheme.typography.R_16,
                    color = MediCareCallTheme.colors.gray8,
                )
                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "${summary.weeklySleepMinutes}",
                    style = MediCareCallTheme.typography.SB_22,
                    color = MediCareCallTheme.colors.gray8,
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    "분",
                    style = MediCareCallTheme.typography.R_16,
                    color = MediCareCallTheme.colors.gray8,
                )


            }


        }
    }
}

@Preview
@Composable
fun PreviewWeeklySleepCard() {
    val dummySummary = WeeklySummaryUiState(
        weeklySleepHours = 7,
        weeklySleepMinutes = 12,

        weeklyMealRate = 0,
        weeklyMedicineRate = 0,
        weeklyHealthIssueCount = 0,
        weeklyUnansweredCount = 0,
        weeklyMeals = emptyList(),
        weeklyMedicines = emptyList(),
        weeklyHealthNote = "",
        weeklyMental = WeeklyMentalUiState(good = 0, normal = 0, bad = 0),
        weeklyGlucose = WeeklyGlucoseUiState(
            beforeMealNormal = 0,
            beforeMealHigh = 0,
            beforeMealLow = 0,
            afterMealNormal = 0,
            afterMealHigh = 0,
            afterMealLow = 0
        )
    )
    WeeklySleepCard(
        modifier = Modifier.size(150.dp, 140.dp),
        summary = dummySummary
    )
}