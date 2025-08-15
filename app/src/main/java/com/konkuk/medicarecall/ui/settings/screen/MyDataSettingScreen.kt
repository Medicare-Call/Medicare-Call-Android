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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.settings.component.LogoutConfirmDialog
import com.konkuk.medicarecall.ui.settings.component.SettingInfoItem
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.settings.viewmodel.MyDataViewModel
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow

@Composable
fun MyDataSettingScreen(onBack: () -> Unit,navController: NavHostController,modifier: Modifier = Modifier, myDataViewModel: MyDataViewModel = hiltViewModel()) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.bg)
            .statusBarsPadding()
    ) {

        SettingsTopAppBar(
            modifier = modifier,
            title = "내 정보 설정",
            leftIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings_back),
                    contentDescription = "go_back",
                    modifier = modifier.size(24.dp).clickable{onBack()},
                    tint = Color.Black
                )
            }
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .figmaShadow(
                        group = MediCareCallTheme.shadow.shadow03,
                        cornerRadius = 14.dp
                    )
                    .clip(RoundedCornerShape(14.dp))
                    .background(MediCareCallTheme.colors.white)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "내 정보",
                        style = MediCareCallTheme.typography.SB_18,
                        color = MediCareCallTheme.colors.gray8
                    )
                    Spacer(modifier = modifier.weight(1f))
                    Text(
                        text = "편집",
                        style = MediCareCallTheme.typography.R_16,
                        color = MediCareCallTheme.colors.active,
                        modifier = modifier.clickable(onClick = {navController.navigate(Route.MyDetail.route)})
                    )
                }
                SettingInfoItem("이름", "김미연")
                SettingInfoItem("생일", "1970년 5월 29일")
                SettingInfoItem("성별", "여성")
                SettingInfoItem("휴대폰번호", "010-0000-0000")
            }

            Spacer(modifier = modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .figmaShadow(
                        group = MediCareCallTheme.shadow.shadow03,
                        cornerRadius = 14.dp
                    )
                    .clip(RoundedCornerShape(14.dp))
                    .background(MediCareCallTheme.colors.white)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Text(
                    "계정 관리",
                    style = MediCareCallTheme.typography.SB_18,
                    color = MediCareCallTheme.colors.gray8
                )
                Text(
                    text = "로그아웃",
                    style = MediCareCallTheme.typography.R_16,
                    color = MediCareCallTheme.colors.gray8,
                    modifier = Modifier.clickable {
                        showLogoutDialog = true
                    }
                )

                Text(
                    text = "서비스 탈퇴",
                    style = MediCareCallTheme.typography.R_16,
                    color = MediCareCallTheme.colors.gray8
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

    }
    if (showLogoutDialog) {
        LogoutConfirmDialog(
            onDismiss = { showLogoutDialog = false },
            onLogout = {
                myDataViewModel.logout(
                    onSuccess = {
                        // 로그아웃 성공 후 동작
                        navController.navigate("login") {
                            popUpTo("main") { inclusive = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                        showLogoutDialog = false
                    },
                    onError = { error ->
                        // 로그아웃 실패 처리 (예: 에러 메시지 표시)
                        println("로그아웃 실패: ${error.message}")
                    }
                )

    }
        )
    }
}

