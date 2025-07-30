package com.konkuk.medicarecall.ui.statistics.weeklycard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.statistics.model.WeeklyGlucoseUiState
import com.konkuk.medicarecall.ui.theme.LocalMediCareCallShadowProvider
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow

@Composable
fun WeeklyGlucoseCard(
    modifier: Modifier = Modifier,
    weeklyGlucose: WeeklyGlucoseUiState
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
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
            //Title: 혈당
            Row(
                modifier = Modifier
                    .fillMaxWidth()

            ) {

                Text(
                    text = "혈당",
                    style = MediCareCallTheme.typography.R_15,
                    color = MediCareCallTheme.colors.gray5,
                )

            }

            // 공복 + 식후
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top


                ) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(
                        text = "공복",
                        style = MediCareCallTheme.typography.M_16,
                        color = MediCareCallTheme.colors.gray6,

                        )
                    Spacer(modifier = Modifier.height(4.dp))

                    GlucoseStatusRow("정상", 5)
                    GlucoseStatusRow("높음", 2)
                    GlucoseStatusRow("낮음", 1)



                }
                Spacer(modifier = Modifier.width(40.dp))

                // 세로 구분선
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(107.dp)
                        .background(MediCareCallTheme.colors.gray1)
                )

                Spacer(modifier = Modifier.width(40.dp))

                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "식후",
                        style = MediCareCallTheme.typography.M_16,
                        color = MediCareCallTheme.colors.gray6,

                        )
                    Spacer(modifier = Modifier.height(4.dp))

                    GlucoseStatusRow("정상", 5)
                    GlucoseStatusRow("낮음", 2)


                }


            }
        }
    }

}

@Composable
fun GlucoseStatusRow(
    status: String,
    count: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 4.dp)
    ) {
        GlucoseStatusChip(status = status)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${count}번",
            style = MediCareCallTheme.typography.R_14,
            color = MediCareCallTheme.colors.gray6
        )
    }
}

// 임시 칩: value → status 받도록
@Composable
fun GlucoseStatusChip(status: String) {
    val statusColor = when (status) {
        "정상" -> MediCareCallTheme.colors.positive
        "낮음" -> MediCareCallTheme.colors.active
        "높음" -> MediCareCallTheme.colors.negative
        else -> MediCareCallTheme.colors.gray4
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10))
            .background(statusColor)
            .padding(horizontal = 10.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = status,
            style = MediCareCallTheme.typography.R_14,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun PreviewWeeklyGlucoseCard() {
    WeeklyGlucoseCard(
        weeklyGlucose = WeeklyGlucoseUiState(
            beforeMealNormal = 5,
            beforeMealHigh = 2,
            beforeMealLow = 1,
            afterMealNormal = 5,
            afterMealLow = 2,
            afterMealHigh = 0
        )
    )

}