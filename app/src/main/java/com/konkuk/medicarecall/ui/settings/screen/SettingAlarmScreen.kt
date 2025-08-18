package com.konkuk.medicarecall.ui.settings.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.data.dto.response.MyInfoResponseDto
import com.konkuk.medicarecall.data.dto.response.PushNotificationDto
import com.konkuk.medicarecall.ui.model.NotificationStateType
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.settings.component.SwitchButton
import com.konkuk.medicarecall.ui.settings.viewmodel.DetailMyDataViewModel
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun SettingAlarmScreen(modifier: Modifier = Modifier,myDataViewModel: DetailMyDataViewModel = hiltViewModel(),myDataInfo : MyInfoResponseDto, onBack: () -> Unit = {}) {
    // 1) 상태 선언
    var complete by remember { mutableStateOf((myDataInfo.pushNotification.carecallCompleted == NotificationStateType.ON) ||(myDataInfo.pushNotification.all == NotificationStateType.ON)) }
    var health by remember { mutableStateOf((myDataInfo.pushNotification.healthAlert == NotificationStateType.ON)||(myDataInfo.pushNotification.all == NotificationStateType.ON)) }
    var missed by remember { mutableStateOf((myDataInfo.pushNotification.carecallMissed == NotificationStateType.ON)||(myDataInfo.pushNotification.all == NotificationStateType.ON)) }
    var all by remember { mutableStateOf(myDataInfo.pushNotification.all == NotificationStateType.ON) }

    var masterChecked by remember { mutableStateOf(all) }
    var completeChecked by remember { mutableStateOf(complete) }
    var abnormalChecked by remember { mutableStateOf(health) }
    var missedChecked by remember { mutableStateOf(missed) }

    Column(modifier = modifier
        .fillMaxSize()
        .background(MediCareCallTheme.colors.bg)
        .statusBarsPadding()) {
        SettingsTopAppBar(
            modifier = modifier,
            title = "푸시 알림 설정",
            leftIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings_back),
                    contentDescription = "go_back",
                    modifier = modifier.size(24.dp).clickable{
                        myDataViewModel.updateUserData(
                            userInfo = MyInfoResponseDto(
                                name = myDataInfo.name,
                                birthDate = myDataInfo.birthDate,
                                gender = myDataInfo.gender,
                                phone = myDataInfo.phone,
                                pushNotification = PushNotificationDto(
                                    all = if (masterChecked) NotificationStateType.ON else NotificationStateType.OFF,
                                    carecallCompleted = if (completeChecked) NotificationStateType.ON else NotificationStateType.OFF,
                                    healthAlert = if (abnormalChecked) NotificationStateType.ON else NotificationStateType.OFF,
                                    carecallMissed = if (missedChecked) NotificationStateType.ON else NotificationStateType.OFF
                                )
                            )
                        )
                        onBack()
                    },
                    tint = Color.Black
                )
            }
        )
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
            Row(modifier = modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                Text("전체 푸시 알림", style = MediCareCallTheme.typography.SB_16, color = Color.Black)
                SwitchButton(masterChecked, onCheckedChange = { isChecked ->
                    masterChecked = isChecked
                    completeChecked = isChecked
                    abnormalChecked = isChecked
                    missedChecked = isChecked
                },
                    modifier = modifier.clip(CircleShape)
                )
            }
            Row(modifier = modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                Text("케어콜 완료 알림", style = MediCareCallTheme.typography.R_16, color = MediCareCallTheme.colors.gray8)
                SwitchButton(completeChecked, onCheckedChange = { isChecked ->
                    completeChecked = isChecked
                    if (!isChecked) {
                        masterChecked = false
                    }
                },
                    modifier = modifier.clip(CircleShape)
                )
            }
            Row(modifier = modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                Text("건강 이상 징후 알림", style = MediCareCallTheme.typography.R_16, color = MediCareCallTheme.colors.gray8)
                SwitchButton(abnormalChecked, onCheckedChange = { isChecked ->
                    abnormalChecked = isChecked
                    if (!isChecked) {
                        masterChecked = false
                    }
                },
                    modifier = modifier.clip(CircleShape))
            }
            Row(modifier = modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                Text("케어콜 부재중 알림", style = MediCareCallTheme.typography.R_16, color = MediCareCallTheme.colors.gray8)
                SwitchButton(missedChecked, onCheckedChange = { isChecked ->
                    missedChecked = isChecked
                    if (!isChecked) {
                        masterChecked = false
                    }
                },
                    modifier = modifier.clip(CircleShape))
            }
        }
    }
}