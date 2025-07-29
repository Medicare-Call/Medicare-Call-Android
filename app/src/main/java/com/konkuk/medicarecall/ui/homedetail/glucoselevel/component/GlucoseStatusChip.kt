package com.konkuk.medicarecall.ui.homedetail.glucoselevel.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun GlucoseStatusChip(
    value: Int
) {
    val statusText = when {
        value >= 130 -> "높음"
        value <= 90 -> "낮음"
        else -> "정상"
    }

    val statusColor = when (statusText) {
        "높음" -> MediCareCallTheme.colors.negative
        "낮음" -> MediCareCallTheme.colors.active
        else -> MediCareCallTheme.colors.positive
    }


    Card(
        modifier = Modifier
            .height(30.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = statusColor),

        ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 20.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
                text = statusText,
                style = MediCareCallTheme.typography.R_16,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun PreviewGlucoseStatusChip_Normal() {
    GlucoseStatusChip(value = 100)
}