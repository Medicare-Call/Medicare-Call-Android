package com.konkuk.medicarecall.ui.login_payment.screen

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun NaverPayScreen(payUrl: String,onBack : () -> Unit, navController: NavHostController,modifier: Modifier = Modifier) {
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
                    modifier = modifier.size(24.dp).clickable{onBack()},
                    tint = Color.Black
                )
            }
        )

        var isLoading by remember { mutableStateOf(true) }
        val content = LocalContext.current

        // Webview
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            isLoading = true
                        }
                        override fun onPageFinished(view: WebView?, url: String?) {
                            isLoading = false
                            // 예시: URL에 따라 결제 성공/실패 분기
                            if (url?.contains("success") == true) { /* 처리 */ }
                        }

                    }
                    loadUrl(payUrl)
                }
            },
            update = { /* 필요시 추가 업데이트 */ },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }


    }
}

//        Box(modifier = modifier.fillMaxSize().background(MediCareCallTheme.colors.gray2).clickable(onClick = {navController.navigate(
//            Route.FinishSplash.route)}),
//            contentAlignment = Alignment.Center
//            ) {
//            Text("네이버 결제 웹",
//                style = MediCareCallTheme.typography.B_26,
//                color = MediCareCallTheme.colors.black,
//            )
//
//        }
//        }
//}