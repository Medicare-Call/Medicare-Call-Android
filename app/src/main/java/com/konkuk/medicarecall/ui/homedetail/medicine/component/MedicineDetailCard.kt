package com.konkuk.medicarecall.ui.homedetail.medicine.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.homedetail.medicine.DoseStatus
import com.konkuk.medicarecall.ui.theme.LocalMediCareCallShadowProvider
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow

@Composable
fun HomeMedicineDetailCard(
    medicineName: String,               // 약 이름
    todayTakenCount: Int,               // 오늘 복약 완료 횟수
    todayRequiredCount: Int,            // 오늘 복용 해야 하는 횟수
    doseStatusList: List<DoseStatus>,   // 복용 상태 리스트 (초록/빨강/회색)
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
            modifier = Modifier.padding(20.dp)
        ) {
            // 제목 + 하루 복약 횟수
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = medicineName,
                    style = MediCareCallTheme.typography.SB_18,
                    color = MediCareCallTheme.colors.gray8,
                )

                Row(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "하루 ${todayRequiredCount}회 복약",
                        style = MediCareCallTheme.typography.R_14,
                        color = MediCareCallTheme.colors.gray4,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            //복약 아이콘 리스트
            Row {
                doseStatusList.forEach { doseStatus ->
                    val tintColor = when (doseStatus) {
                        DoseStatus.TAKEN -> MediCareCallTheme.colors.positive  // 초록
                        DoseStatus.SKIPPED -> MediCareCallTheme.colors.negative // 빨강
                        DoseStatus.NOT_RECORDED -> MediCareCallTheme.colors.gray2 // 회색
                    }

                    Image(
                        painter = painterResource(id = R.drawable.ic_pills_basic),
                        contentDescription = "복약 상태 아이콘",
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 8.dp),
                        colorFilter = ColorFilter.tint(tintColor)
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeMedicineDetailCard() {
    HomeMedicineDetailCard(
        medicineName = "당뇨약",
        todayTakenCount = 2,
        todayRequiredCount = 3,
        doseStatusList = listOf(
            DoseStatus.TAKEN,
            DoseStatus.TAKEN,
            DoseStatus.NOT_RECORDED
        )
    )
}

