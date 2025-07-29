package com.konkuk.medicarecall.ui.statistics.weeklycard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.konkuk.medicarecall.ui.statistics.model.WeeklyMentalUiState
import com.konkuk.medicarecall.ui.theme.LocalMediCareCallShadowProvider
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow

@Composable
fun WeeklyMentalCard(
    modifier: Modifier = Modifier,
    mental: WeeklyMentalUiState
) {

    Card(
        modifier = modifier
            .figmaShadow(
                group = LocalMediCareCallShadowProvider.current.shadow03,
                cornerRadius = 14.dp
            ),

        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            //1) Title: 심리 상태


            Text(
                "심리상태",
                style = MediCareCallTheme.typography.R_15,
                color = MediCareCallTheme.colors.gray5,
            )


            Spacer(modifier = Modifier.height(8.dp))


            // 2) 좋음+보통+나쁨

            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {


                // 2) 좋음
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "좋음",
                        style = MediCareCallTheme.typography.R_15,
                        color = MediCareCallTheme.colors.gray4
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Row(
                        modifier = Modifier
                            .padding(vertical = 1.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(13.3.dp),
                            painter = painterResource(id = R.drawable.ic_emoji_good),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "${mental.good}번",
                            style = MediCareCallTheme.typography.R_14,
                            color = MediCareCallTheme.colors.gray6
                        )
                    }
                }

                // 3) 보통
                Row(
                    modifier = Modifier
                        .padding(vertical = 1.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "보통",
                        style = MediCareCallTheme.typography.R_15,
                        color = MediCareCallTheme.colors.gray4
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.size(13.3.dp),
                            painter = painterResource(id = R.drawable.ic_emoji_normal),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "${mental.normal}번",
                            style = MediCareCallTheme.typography.R_14,
                            color = MediCareCallTheme.colors.gray6
                        )
                    }
                }
                // 4) 나쁨
                Row(
                    modifier = Modifier
                        .padding(vertical = 1.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "나쁨",
                        style = MediCareCallTheme.typography.R_15,
                        color = MediCareCallTheme.colors.gray4
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.size(13.3.dp),
                            painter = painterResource(id = R.drawable.ic_emoji_bad),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "${mental.bad}번",
                            style = MediCareCallTheme.typography.R_14,
                            color = MediCareCallTheme.colors.gray6
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewWeeklyMentalCard() {
    WeeklyMentalCard(
        modifier = Modifier
            .size(150.dp, 140.dp),
        mental = WeeklyMentalUiState(
            good = 4,
            normal = 2,
            bad = 1
        )
    )

}