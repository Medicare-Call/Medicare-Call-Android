package com.konkuk.medicarecall.ui.homedetail.statehealth.component

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.homedetail.statehealth.model.HealthUiState
import com.konkuk.medicarecall.ui.theme.LocalMediCareCallShadowProvider
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow

@Composable
fun StateHealthDetailCard(

    health: HealthUiState,
    modifier: Modifier = Modifier
) {

    val healthSummaryText = "주요 증상으로 보아 파킨슨 병이 의심돼요. 어르신과 함께 병원에 방문해 보세요."
    val trimmedText = healthSummaryText.take(100) // 글자 수 제한

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
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
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                //1) 건강징후 요약
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {


                    Text(
                        text = "건강징후 요약",
                        style = MediCareCallTheme.typography.R_15,
                        color = MediCareCallTheme.colors.gray5
                    )

                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    if (!health.isRecorded) {
                        Text(
                            text = "건강징후 기록 전이에요.",
                            style = MediCareCallTheme.typography.R_16,
                            color = MediCareCallTheme.colors.gray4
                        )
                    } else {
                        Column(
                            modifier = Modifier,

                            ) {
                            health.symptoms.forEach { symptom ->
                                Row(verticalAlignment = Alignment.Top) {
                                    Text(
                                        text = "•",
                                        style = MediCareCallTheme.typography.R_16,
                                        color = MediCareCallTheme.colors.gray8
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = symptom,
                                        style = MediCareCallTheme.typography.R_16,
                                        color = MediCareCallTheme.colors.gray8
                                    )
                                }

                            }
                        }
                    }
                }
            }

            //증상 분석
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {


                    Text(
                        text = "증상 분석",
                        style = MediCareCallTheme.typography.R_15,
                        color = MediCareCallTheme.colors.gray5
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!health.isRecorded) {
                        Text(
                            text = "증상분석 전이에요.",
                            style = MediCareCallTheme.typography.R_16,
                            color = MediCareCallTheme.colors.gray4
                        )
                    } else {
                        Text(
                            text = health.symptomAnalysis.take(100),
                            //TODO: 병명 볼드처리
                            style = MediCareCallTheme.typography.R_16,
                            color = MediCareCallTheme.colors.gray8
                        )
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStateHealthDetailCard() {
    StateHealthDetailCard(
        health = HealthUiState(
            symptoms = listOf(
                "손 떨림 증상",
                "거동 불편",
                "몸이 느려짐"
            ),
            //TODO: 병명 볼드처리
            symptomAnalysis = "주요 증상으로 보아 파킨슨 병이 의심돼요. 어르신과 함께 병원에 방문해 보세요.",
            isRecorded = true

        )
    )
}

