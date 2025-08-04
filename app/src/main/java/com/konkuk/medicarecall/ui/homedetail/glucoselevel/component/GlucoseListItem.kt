package com.konkuk.medicarecall.ui.homedetail.glucoselevel.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun GlucoseListItem(
    modifier: Modifier = Modifier,
    date: String,
    timingLabel: String,
    value: Int
) {

    Column(modifier = modifier.padding(horizontal = 20.dp)) {
        // 날짜 텍스트
        Text(
            text = date,
            style = MediCareCallTheme.typography.R_14,
            color = MediCareCallTheme.colors.gray4
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(//아침|공복 or 저녁|식후
                    text = timingLabel,
                    style = MediCareCallTheme.typography.R_14,
                    color = MediCareCallTheme.colors.gray6
                )

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(//혈당값
                        text = value.toString(),
                        style = MediCareCallTheme.typography.SB_16,
                        color = MediCareCallTheme.colors.gray6
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "mg/dL",
                        style = MediCareCallTheme.typography.R_14,
                        color = MediCareCallTheme.colors.gray6
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            GlucoseStatusChip(value)//낮음,정상,높음


        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewGlucoseListItem() {
    MediCareCallTheme {
        GlucoseListItem(
            date = "5월 21일 (수)",
            timingLabel = "아침 | 공복",
            value = 180
        )
    }
}