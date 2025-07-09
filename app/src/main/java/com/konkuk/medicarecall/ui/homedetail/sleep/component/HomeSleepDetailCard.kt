package com.konkuk.medicarecall.ui.homedetail.sleep.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.konkuk.medicarecall.ui.homedetail.sleep.SleepUiState
import com.konkuk.medicarecall.ui.theme.LocalMediCareCallShadowProvider
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow

@Composable
fun HomeSleepDetailCard(

    sleeps: SleepUiState,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .figmaShadow(
                group = LocalMediCareCallShadowProvider.current.shadow03,
                cornerRadius = 14.dp
            ),

        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp)

    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(11.dp)
        ) {


            //1) 총 수면 시간
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {

                    Text(
                        text = "총 수면 시간",
                        style = MediCareCallTheme.typography.R_15,
                        color = MediCareCallTheme.colors.gray5
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${sleeps.totalSleepHours}",
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
                            text = "${sleeps.totalSleepMinutes}",
                            style = MediCareCallTheme.typography.SB_22
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



            //취침 + 기상
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) { // TODO: 오전 오후 수정
                //2) 취침 시간
                Column {

                    Text(
                        text = "취침 시간",
                        style = MediCareCallTheme.typography.R_15,
                        color = MediCareCallTheme.colors.gray5
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = sleeps.bedTime,
                            style = MediCareCallTheme.typography.SB_16,
                        )
                    }
                }

                Spacer(modifier = Modifier.width(32.dp))

                //3) 기상 시간

                Column {

                    Text(
                        text = "기상 시간",
                        style = MediCareCallTheme.typography.R_15,
                        color = MediCareCallTheme.colors.gray5
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = sleeps.wakeUpTime,
                            style = MediCareCallTheme.typography.SB_16,
                            color = MediCareCallTheme.colors.gray8,
                        )


                    }
                }

            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeSleepDetailCard() {
    HomeSleepDetailCard(
        sleeps = SleepUiState(
            date = "2025-07-07",
            totalSleepHours = 8,
            totalSleepMinutes = 12,
            bedTime = "오후 10:12",
            wakeUpTime = "오전 06:00"
        )
    )


}

