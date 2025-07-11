package com.konkuk.medicarecall.ui.login_care_call.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.login_info.component.TopBar
import com.konkuk.medicarecall.ui.login_care_call.component.BenefitItem
import com.konkuk.medicarecall.ui.login_care_call.component.TimeSettingItem
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.model.TimeSettingType
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun SetCallScreen(name: String, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.bg)
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 20.dp)

    ) {
        TopBar({})
        Spacer(modifier = modifier.height(20.dp))
        Column(
            modifier = modifier.verticalScroll(scrollState)
        ) {
            Text(
                text = "케이콜 설정",
                style = MediCareCallTheme.typography.M_17,
                color = MediCareCallTheme.colors.gray8
            )
            Spacer(modifier = modifier.height(20.dp))
            Text(
                text = "$name 님께 맞는\n 맞춤형 케어플랜을 만들었어요",
                style = MediCareCallTheme.typography.B_26,
                color = MediCareCallTheme.colors.black,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = modifier.height(24.dp))
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(MediCareCallTheme.colors.white, shape = RoundedCornerShape(20.dp))
                    .border(
                        width = (1.2).dp,
                        color = MediCareCallTheme.colors.gray2,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = modifier
                        .background(
                            color = MediCareCallTheme.colors.g100,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = "매일 2회 풀케어",
                        style = MediCareCallTheme.typography.R_14,
                        color = MediCareCallTheme.colors.main
                    )
                }

                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "프리미엄",
                        color = MediCareCallTheme.colors.gray7,
                        style = MediCareCallTheme.typography.B_26
                    )
                    Spacer(modifier = modifier.weight(1f))
                    Text(
                        text = "₩29,000/월",
                        color = MediCareCallTheme.colors.main,
                        style = MediCareCallTheme.typography.B_17
                    )
                }

                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .width(1.dp)
                        .background(MediCareCallTheme.colors.gray2)
                )
                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BenefitItem("매일 2회 케어콜 제공")
                    BenefitItem("건강 리포트 제공")
                    BenefitItem("무제한 보호자 지정")
                }
            }
            Spacer(modifier = modifier.height(30.dp))

            // 전화 시간대 설정
            Text(
                text = "전화 시간대",
                style = MediCareCallTheme.typography.M_17,
                color = MediCareCallTheme.colors.gray8
            )
            Spacer(modifier = modifier.height(30.dp))
            TimeSettingItem("1차", TimeSettingType.FIRST)
            Spacer(modifier = modifier.height(20.dp))
            TimeSettingItem("2차", TimeSettingType.SECOND)
            Spacer(modifier = modifier.height(30.dp))

            // 안내 사항
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = MediCareCallTheme.colors.gray1,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 12.dp)
            ) {
                Text(
                    text = "안내사항",
                    style = MediCareCallTheme.typography.M_17,
                    color = MediCareCallTheme.colors.gray8
                )
                Spacer(modifier = modifier.height(12.dp))
                Column(
                    modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "전화 시간대는 10분단위로 설정이 가능하나",
                        style = MediCareCallTheme.typography.R_15,
                        color = MediCareCallTheme.colors.gray8
                    )
                    Text(
                        text = "부재중일 경우 5분 단위로 3회 재발신, 전화 설정에서 수정 가능",
                        style = MediCareCallTheme.typography.R_15,
                        color = MediCareCallTheme.colors.gray8
                    )
                    Text(
                        text = "케어콜 번호를 어르신 휴대전화 주소록에 저장해주세요",
                        style = MediCareCallTheme.typography.R_15,
                        color = MediCareCallTheme.colors.gray8
                    )
                }
            }
            Spacer(modifier = modifier.height(30.dp))
            CTAButton(CTAButtonType.GREEN, text = "확인", {}) // 입력여부에 따라 Type 바뀌도록 수정 필요
        }
    }
}

@Preview
@Composable
private fun SetCallPreview() {
    SetCallScreen("김옥자")
}