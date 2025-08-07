package com.konkuk.medicarecall.ui.statistics.weeklycard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.theme.LocalMediCareCallShadowProvider
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow

@Composable
fun WeeklyHealthCard(
    modifier: Modifier = Modifier,
    healthNote: String
) {
    Card(
        modifier = modifier
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
                .fillMaxWidth()
        ) {
            Text(
                text = "건강징후",
                style = MediCareCallTheme.typography.SB_18,
                color = MediCareCallTheme.colors.gray8,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = healthNote,
                style = MediCareCallTheme.typography.R_16,
                color = MediCareCallTheme.colors.gray8,
            )
        }
    }
}

@Preview
@Composable
fun PreviewWeeklyHealthCard() {

    WeeklyHealthCard(
        healthNote = "아침·점심 복약과 식사는 문제 없으나, 저녁 약 복용이 늦어질 우려가 있어요. 전반적으로 양호하나 피곤과 호흡곤란을 호소하셨으므로 휴식과 보호자 확인이 필요해요."
    )
}