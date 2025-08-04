package com.konkuk.medicarecall.ui.login_payment.screen

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun NaverPayScreen(onBack : () -> Unit, navController: NavHostController,modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .background(MediCareCallTheme.colors.bg)) {
        SettingsTopAppBar(
            modifier = modifier,
            title = "결제하기",
            leftIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings_back),
                    contentDescription = "go_back",
                    modifier = modifier.size(24.dp),
                    tint = Color.Black
                )
            },
            leftIconClick = onBack,
            rightIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_big),
                    contentDescription = "go_next",
                    modifier = modifier.size(24.dp),
                    tint = Color.Black
                )
            },
            rightIconClick = { navController.navigate(Route.FinishSplash.route) }
            // 오른쪽 아이콘은 스플래쉬 이용 위한 것(임시) -> api 연동 후 제거할 예정
        )
//        Box(modifier = modifier.fillMaxSize().background(MediCareCallTheme.colors.gray2).clickable(onClick = {navController.navigate(
//            Route.FinishSplash.route)}),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("네이버 결제 웹",
//                style = MediCareCallTheme.typography.B_26,
//                color = MediCareCallTheme.colors.black,
//            )
//
//        }

        // 웹뷰
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MediCareCallTheme.colors.gray2),
            contentAlignment = Alignment.Center
        ) {
            val context = LocalContext.current
            AndroidView(
                factory = {
                    WebView(context).apply {
                        webViewClient = WebViewClient() // 링크 클릭 시 새 창이 아닌 WebView 내부에서 열리도록 설정
                        settings.javaScriptEnabled = true // JS가 필요한 페이지를 위해 설정
                        loadUrl("https://www.naver.com")
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}