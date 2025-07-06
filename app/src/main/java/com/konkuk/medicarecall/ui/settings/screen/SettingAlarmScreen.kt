package com.konkuk.medicarecall.ui.settings.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.settings.component.SwitchItem
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun SettingAlarmScreen(modifier: Modifier = Modifier) {
    // 1) 상태 선언
    var masterChecked by remember { mutableStateOf(false) }
    var completeChecked by remember { mutableStateOf(false) }
    var abnormalChecked by remember { mutableStateOf(false) }
    var missedChecked by remember { mutableStateOf(false) }

    Column(modifier = modifier
        .fillMaxSize()
        .background(MediCareCallTheme.colors.bg)) {
        SettingsTopAppBar(
            modifier = modifier,
            title = "푸시 알림 설정",
            leftIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings_back),
                    contentDescription = "go_back",
                    modifier = modifier.size(24.dp),
                    tint = Color.Black
                )
            }
        )
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
            Row(modifier = modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                Text("전체 푸시 알림", style = MediCareCallTheme.typography.SB_16, color = Color.Black)
                SwitchItem(masterChecked, onCheckedChange = { isChecked ->
                    masterChecked = isChecked
                    completeChecked = isChecked
                    abnormalChecked = isChecked
                    missedChecked = isChecked
                })
            }
            Row(modifier = modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                Text("케어콜 완료 알림", style = MediCareCallTheme.typography.R_16, color = MediCareCallTheme.colors.gray8)
                SwitchItem(completeChecked, onCheckedChange = { isChecked ->
                    completeChecked = isChecked
                    if (!isChecked) {
                        masterChecked = false
                    }
                })
            }
            Row(modifier = modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                Text("건강 이상 징후 알림", style = MediCareCallTheme.typography.R_16, color = MediCareCallTheme.colors.gray8)
                SwitchItem(abnormalChecked, onCheckedChange = { isChecked ->
                    abnormalChecked = isChecked
                    if (!isChecked) {
                        masterChecked = false
                    }
                })
            }
            Row(modifier = modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                Text("케어콜 부재중 알림", style = MediCareCallTheme.typography.R_16, color = MediCareCallTheme.colors.gray8)
                SwitchItem(missedChecked, onCheckedChange = { isChecked ->
                    missedChecked = isChecked
                    if (!isChecked) {
                        masterChecked = false
                    }
                })
            }
        }
    }
}

@Preview
@Composable
fun SettingPreview(modifier: Modifier = Modifier) {
    SettingAlarmScreen()
}