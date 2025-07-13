package com.konkuk.medicarecall.ui.settings.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.settings.component.SettingInfoItem
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow

@Composable
fun SubscribeDetailScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    navController: NavHostController
) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier
        .fillMaxSize()
        .background(MediCareCallTheme.colors.bg)) {
        SettingsTopAppBar(
            modifier = modifier,
            title = "구독 관리",
            leftIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings_back),
                    contentDescription = "go_back",
                    modifier = modifier
                        .size(24.dp)
                        .clickable { onBack() },
                    tint = Color.Black
                )
            }
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 결제 정보 컴포넌트
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .figmaShadow(group = MediCareCallTheme.shadow.shadow03)
                    .clip(RoundedCornerShape(14.dp))
                    .background(MediCareCallTheme.colors.white)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Text(
                    text = "결제 정보",
                    style = MediCareCallTheme.typography.SB_18,
                    color = MediCareCallTheme.colors.gray8
                )
                SettingInfoItem("어르신 성함", "김옥자")
                Column(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "구독 플랜",
                        style = MediCareCallTheme.typography.R_14,
                        color = MediCareCallTheme.colors.gray4
                    )
                    Text(
                        text = "프리미엄 플랜",
                        style = MediCareCallTheme.typography.SB_18,
                        color = MediCareCallTheme.colors.main
                    )
                    Text(
                        text = "월 29,000원",
                        style = MediCareCallTheme.typography.SB_16,
                        color = MediCareCallTheme.colors.gray8
                    )
                }
                SettingInfoItem("결제 예정일", "2025년 7월 10일")
                SettingInfoItem("최초 가입일", "2025년 5월 10일")
            }

            // 결제 수단 변경하기 컴포넌트
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .figmaShadow(
                        group = MediCareCallTheme.shadow.shadow03,
                        cornerRadius = 14.dp
                    )
                    .clip(RoundedCornerShape(14.dp))
                    .background(color = Color.White)
                    .padding(start = 20.dp)
                    .clickable {} // 클릭 이벤트 추가
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "추가 아이콘",
                    modifier = Modifier.size(20.dp),
                    tint = MediCareCallTheme.colors.gray4
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "결제수단 변경하기",
                    style = MediCareCallTheme.typography.SB_14,
                    color = MediCareCallTheme.colors.gray4
                )
            }
            Row(
                modifier = modifier.fillMaxWidth()
            ) {
                Spacer(modifier = modifier.weight(1f))
                Text(
                    text = "해지하기",
                    style = MediCareCallTheme.typography.SB_14,
                    color = MediCareCallTheme.colors.gray3
                )
            }
        }
    }
}
