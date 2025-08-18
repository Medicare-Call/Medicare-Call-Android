package com.konkuk.medicarecall.ui.settings.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.data.api.SettingService
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.settings.viewmodel.MyDataViewModel
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow

@Composable
fun SettingsScreen(
    onNavigateToMyDataSetting : () -> Unit = {},
    onNavigateToAnnouncement : () -> Unit = {},
    onNavigateToCenter : () -> Unit = {},
    onNavigateToSubscribe : () -> Unit = {},
    onNavigateToPersonalInfo : () -> Unit = {},
    onNavigateToHealthInfo : () -> Unit = {},
    onNavigateToSettingAlarm : () -> Unit = {},
    myDataViewModel : MyDataViewModel = hiltViewModel()
) {
    val myInfo = myDataViewModel.myDataInfo
    Column(
        modifier = Modifier.fillMaxSize()
            .background(MediCareCallTheme.colors.bg)
            .statusBarsPadding()
    ){
        SettingsTopAppBar(title = "설정") // 상단 TopAppBar,
        Spacer(modifier = Modifier.height(20.dp))
        Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp).padding(bottom = 20.dp).verticalScroll(rememberScrollState()).systemBarsPadding()
            // .systemBarsPadding() 추가
    ) {
        // 프로필
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable{onNavigateToMyDataSetting()}
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
            ) {
            Image(
                painter = painterResource(id = R.drawable.img_setting_profile),
                contentDescription = "settings profile image",
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = myInfo?.name ?: "어르신 이름이 등록되지 않았습니다.",
                style = MediCareCallTheme.typography.SB_18,
                color = MediCareCallTheme.colors.black

            ) // 나중에 값 받아와서 이름 출력되도록 수정 필요
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "님", style = MediCareCallTheme.typography.R_18, color = MediCareCallTheme.colors.black)
            Spacer(modifier = Modifier.weight(1f))
            Icon(painter = painterResource(id = R.drawable.ic_arrow_big), contentDescription = "화살표 아이콘", modifier = Modifier.size(28.dp), tint = MediCareCallTheme.colors.gray2)

        }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(modifier = Modifier.fillMaxWidth().figmaShadow(
                    group = MediCareCallTheme.shadow.shadow03,
                    cornerRadius = 14.dp
                )
                    .clip(RoundedCornerShape(14.dp))
                    .background(MediCareCallTheme.colors.white).padding(20.dp),
                    ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable{onNavigateToAnnouncement()}
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_announcement),
                            contentDescription = "공지사항 아이콘",
                            modifier = Modifier.size(32.dp),
                            tint = MediCareCallTheme.colors.main
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "공지사항",
                            style = MediCareCallTheme.typography.R_14,
                            color = MediCareCallTheme.colors.gray8,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        modifier = Modifier.clickable{onNavigateToCenter()},
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_service_center),
                            contentDescription = "고객센터 아이콘",
                            modifier = Modifier.size(32.dp),
                            tint = MediCareCallTheme.colors.main
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "고객센터",
                            style = MediCareCallTheme.typography.R_14,
                            color = MediCareCallTheme.colors.gray8,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        modifier = Modifier.clickable{onNavigateToSubscribe()},
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_subscription_management),
                            contentDescription = "구독관리 아이콘",
                            modifier = Modifier.size(32.dp),
                            tint = MediCareCallTheme.colors.main
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "구독관리",
                            style = MediCareCallTheme.typography.R_14,
                            color = MediCareCallTheme.colors.gray8,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        modifier = Modifier.clickable{}, // 결제내역 클릭 시 동작 추가
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_payment_detail),
                            contentDescription = "결제내역 아이콘",
                            modifier = Modifier.size(32.dp),
                            tint = MediCareCallTheme.colors.main
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "결제내역",
                            style = MediCareCallTheme.typography.R_14,
                            color = MediCareCallTheme.colors.gray8,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier.fillMaxWidth().figmaShadow(
                        group = MediCareCallTheme.shadow.shadow03,
                        cornerRadius = 14.dp
                    )
                        .clip(RoundedCornerShape(14.dp))
                        .background(MediCareCallTheme.colors.white)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().clickable(onClick = {onNavigateToPersonalInfo()})
                    ) {
                        Text(text = "어르신 개인정보 설정", style = MediCareCallTheme.typography.R_16, color = MediCareCallTheme.colors.gray8)
                        Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "화살표 아이콘", modifier = Modifier.size(24.dp), tint = MediCareCallTheme.colors.gray2)
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().clickable(onClick = {onNavigateToHealthInfo()}),
                    ) {
                        Text(text = "어르신 건강정보 설정", style = MediCareCallTheme.typography.R_16, color = MediCareCallTheme.colors.gray8)
                        Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "화살표 아이콘", modifier = Modifier.size(24.dp), tint = MediCareCallTheme.colors.gray2)
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().clickable(onClick = {}), // 케어콜 스케줄 설정 클릭 시 동작 추가
                    ) {
                        Text(text = "케어콜 스케줄 설정", style = MediCareCallTheme.typography.R_16, color = MediCareCallTheme.colors.gray8)
                        Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "화살표 아이콘", modifier = Modifier.size(24.dp), tint = MediCareCallTheme.colors.gray2)
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().clickable{onNavigateToSettingAlarm()}
                    ) {
                        Text(text = "푸시 알림 설정", style = MediCareCallTheme.typography.R_16, color = MediCareCallTheme.colors.gray8)
                        Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "화살표 아이콘", modifier = Modifier.size(24.dp), tint = MediCareCallTheme.colors.gray2)
                    }
                }

            }
        }
    }
    }

