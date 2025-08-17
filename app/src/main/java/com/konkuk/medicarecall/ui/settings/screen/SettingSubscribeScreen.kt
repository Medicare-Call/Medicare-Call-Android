package com.konkuk.medicarecall.ui.settings.screen

import android.util.Log
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.settings.component.SubscribeCard
import com.konkuk.medicarecall.ui.settings.viewmodel.SubscribeViewModel
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SettingSubscribeScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    navController: NavHostController,
    viewModel: SubscribeViewModel = hiltViewModel()
) {

    val eldersInfo = viewModel.subscriptions
    Log.d("SettingSubscribeScreen", "Elders Info: $eldersInfo")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.bg)
            .statusBarsPadding()
    ) {
        SettingsTopAppBar(
            modifier = modifier,
            title = "구독관리",
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
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = modifier.height(20.dp))
            eldersInfo.forEach {
                SubscribeCard(
                    elderInfo = it,
                    onClick = {
                        val json = Json.encodeToString(it)
                        val encodedJson = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
                        navController.navigate("subscribe_detail/$encodedJson")
                    },
                )
            }
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
                    text = "어르신 더 추가하기",
                    style = MediCareCallTheme.typography.SB_14,
                    color = MediCareCallTheme.colors.gray4,
                )
            }
            Spacer(modifier = modifier.height(20.dp))
        }
    }
}