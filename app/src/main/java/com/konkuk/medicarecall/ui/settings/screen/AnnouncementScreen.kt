package com.konkuk.medicarecall.ui.settings.screen

import android.R.string.no
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.settings.component.AnnouncementCard
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.settings.viewmodel.NoticeViewModel
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun AnnouncementScreen( modifier: Modifier = Modifier, onBack : () -> Unit = {}, navController: NavHostController, viewModel: NoticeViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val notices = viewModel.noticeList
    val error = viewModel.errorMessage

    Log.d("AnnouncementScreen(notice)", "현재 공지 수: ${notices.size}")
    error?.let {
        Log.d("AnnouncementScreen(notice)", "에러 메시지: $it")
    }

    Column(modifier = modifier
        .fillMaxSize()
        .background(MediCareCallTheme.colors.bg)
        .statusBarsPadding()
    ) {
        SettingsTopAppBar(
            modifier = modifier,
            title = "공지사항",
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
            modifier = modifier.verticalScroll(scrollState)
        ) {
            if (error != null) {
                AnnouncementCard("공지사항 오류 발생", error, onClick = {})
            } else {
                notices.forEach { notice ->
                    AnnouncementCard(
                        title = notice.title,
                        date = notice.publishedAt.replace("-", "."),
                        onClick = {
                            val json = Json.encodeToString(notice)
                            val encodedJson = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
                            navController.navigate("announcement_detail/$encodedJson")
                        }
                    )
                }
            }
        }
        }

}