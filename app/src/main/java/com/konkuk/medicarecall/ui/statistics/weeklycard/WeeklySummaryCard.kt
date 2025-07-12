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
import com.konkuk.medicarecall.ui.theme.LocalMediCareCallShadowProvider
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow

@Composable
fun WeeklySummaryCard(
    modifier: Modifier = Modifier

    //TODO:패딩값 조정
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
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 10.dp,
                    top = 20.dp,
                    bottom = 20.dp
                )
        ) {
            //1) Title: 주간 요약 통계
            Row(
                modifier = Modifier
                    .fillMaxWidth()

            ) {

                Text(
                    "주간 요약통계",
                    style = MediCareCallTheme.typography.R_15,
                    color = MediCareCallTheme.colors.gray5,
                )

            }




            Spacer(modifier = Modifier.height(8.dp))


            //2) 식사율+복약률+건강징후+미응답

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                // 식사율
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "식사율",
                        style = MediCareCallTheme.typography.R_14,
                        color = MediCareCallTheme.colors.gray6,
                    )

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Text(
                            text = "65",
                            style = MediCareCallTheme.typography.SB_22,
                            color = MediCareCallTheme.colors.black,
                        )
                        Text(
                            text = "%",
                            style = MediCareCallTheme.typography.R_16,
                            color = MediCareCallTheme.colors.black,
                        )


                    }
                }


                // 복약률
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {


                    Text(
                        text = "복약률",
                        style = MediCareCallTheme.typography.R_14,
                        color = MediCareCallTheme.colors.gray6,
                    )

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "57",
                            style = MediCareCallTheme.typography.SB_22,
                            color = MediCareCallTheme.colors.black,
                        )
                        Text(
                            text = "%",
                            style = MediCareCallTheme.typography.R_16,
                            color = MediCareCallTheme.colors.black,
                        )


                    }
                }


                // 건강징후
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {


                    Text(
                        text = "건강징후",
                        style = MediCareCallTheme.typography.R_14,
                        color = MediCareCallTheme.colors.gray6,
                    )

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "3",
                            style = MediCareCallTheme.typography.SB_22,
                            color = MediCareCallTheme.colors.black,
                        )
                        Text(
                            text = "건",
                            style = MediCareCallTheme.typography.R_16,
                            color = MediCareCallTheme.colors.black,
                        )


                    }
                }


                // 미응답
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {


                    Text(
                        text = "미응답",
                        style = MediCareCallTheme.typography.R_14,
                        color = MediCareCallTheme.colors.gray6,
                    )

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "8",
                            style = MediCareCallTheme.typography.SB_22,
                            color = MediCareCallTheme.colors.black,
                        )
                        Text(
                            text = "건",
                            style = MediCareCallTheme.typography.R_16,
                            color = MediCareCallTheme.colors.black,
                        )


                    }
                }

            }

        }

    }

}

@Preview
@Composable
fun PreviewWeeklySummaryCard() {
    WeeklySummaryCard()

}