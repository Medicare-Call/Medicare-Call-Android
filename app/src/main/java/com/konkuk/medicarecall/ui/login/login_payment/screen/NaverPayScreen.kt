package com.konkuk.medicarecall.ui.login.login_payment.screen

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.login.login_payment.viewmodel.NaverPayViewModel
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.settings.viewmodel.EldersInfoViewModel
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import androidx.core.net.toUri
import com.konkuk.medicarecall.ui.model.PaymentResult

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun NaverPayScreen(
    onBack: () -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    naverPayViewModel: NaverPayViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.bg)
            .statusBarsPadding()
    ) {
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
                // 임시 이동 버튼 (실배포 전 제거)
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_big),
                    contentDescription = "go_next",
                    modifier = modifier.size(24.dp),
                    tint = Color.Black
                )
            },
            rightIconClick = { navController.navigate(Route.FinishSplash.route) }
        )

        val context = LocalContext.current
        val baseHost = "medicare-call.shop"

        // ViewModel 상태 스냅샷
        val orderCode = naverPayViewModel.orderCode
        val accessToken = naverPayViewModel.accessToken

        // 중복 네비 방지
        var navigated by rememberSaveable { mutableStateOf(false) }

        // WebView 인스턴스 재사용
        val webView = remember { WebView(context) }
        var loaded by remember { mutableStateOf(false) }


        LaunchedEffect(orderCode, accessToken) {
            if (!loaded && !orderCode.isNullOrBlank() && !accessToken.isNullOrBlank()) {
                val url = "https://$baseHost/api/payments/page/$orderCode"
                val authHeaders = mapOf("Authorization" to "Bearer $accessToken")
                Log.d("NaverPayScreen", "Loading Naver Pay URL: $url with headers: $authHeaders")
                webView.loadUrl(url, authHeaders)
                loaded = true
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MediCareCallTheme.colors.gray2),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    webView.apply {
                        // 1) 쿠키 허용 + 3rd-party 쿠키 허용 (네이버 도메인 세션 유지)
                        val cm = android.webkit.CookieManager.getInstance()
                        cm.setAcceptCookie(true)
                        cm.setAcceptThirdPartyCookies(this, true)

                        // 2) JS/스토리지 + 팝업 허용
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.javaScriptCanOpenWindowsAutomatically = true
                        settings.setSupportMultipleWindows(true)

                        // 3) 새창 요청을 같은 WebView에서 처리
                        webChromeClient = object : android.webkit.WebChromeClient() {
                            override fun onCreateWindow(
                                view: WebView?,
                                isDialog: Boolean,
                                isUserGesture: Boolean,
                                resultMsg: android.os.Message?
                            ): Boolean {
                                val transport = resultMsg?.obj as WebView.WebViewTransport
                                transport.webView = this@apply
                                resultMsg.sendToTarget()
                                return true
                            }
                            override fun onConsoleMessage(msg: android.webkit.ConsoleMessage?): Boolean {
                                android.util.Log.d("NaverPayWebView", "CONSOLE: ${msg?.message()}")
                                return super.onConsoleMessage(msg)
                            }
                        }

                        // 4) 네비게이션 처리: 우리 도메인은 Authorization 재부착, intent 스킴 처리
                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView,
                                request: android.webkit.WebResourceRequest
                            ): Boolean {
                                val url = request.url.toString()
                                val host = request.url.host ?: ""
                                val scheme = request.url.scheme ?: ""

                                // intent://, market:// 등 외부 스킴 처리
                                if (scheme.equals("intent", true)) {
                                    try {
                                        val intent = android.content.Intent.parseUri(url, android.content.Intent.URI_INTENT_SCHEME)
                                        try { view.context.startActivity(intent) }
                                        catch (_: Exception) {
                                            val fallback = intent.getStringExtra("browser_fallback_url")
                                            if (!fallback.isNullOrEmpty()) {
                                                view.loadUrl(fallback)
                                            } else {
                                                val pkg = intent.`package`
                                                if (!pkg.isNullOrEmpty()) {
                                                    val market = android.content.Intent(
                                                        android.content.Intent.ACTION_VIEW,
                                                        "market://details?id=$pkg".toUri()
                                                    )
                                                    view.context.startActivity(market)
                                                }
                                            }
                                        }
                                    } catch (e: Exception) {
                                        android.util.Log.e("NaverPayWebView", "intent scheme error: ${e.message}")
                                    }
                                    return true
                                }
                                if (scheme in listOf("tel","mailto","sms")) {
                                    try {
                                        val i = android.content.Intent(android.content.Intent.ACTION_VIEW, request.url)
                                        view.context.startActivity(i)
                                    } catch (_: Exception) {}
                                    return true
                                }

                                // 우리 서버 이동일 땐 토큰 다시 붙여서 로드
                                if (host == "medicare-call.shop") {
                                    val token = naverPayViewModel.accessToken
                                    if (!token.isNullOrBlank()) {
                                        view.loadUrl(url, mapOf("Authorization" to "Bearer $token"))
                                        return true
                                    }
                                }
                                // 그 외(네이버 포함)는 WebView가 자체 처리
                                return false
                            }

                            override fun onPageFinished(view: WebView, url: String) {
                                Log.d("NaverPayWebView", "FINISHED: $url")

                                // 우리 서버의 결제 완료 페이지에 도달했을 때만 시도
                                if (url.contains("/api/payments/result")) {
                                    view.evaluateJavascript(
                                        """
            (function() {
              try {
                if (window.AndroidBridge && AndroidBridge.onPaymentComplete && window.paymentResult) {
                  AndroidBridge.onPaymentComplete(JSON.stringify(window.paymentResult));
                  return 'bridged';
                }
                return 'no-bridge-or-result';
              } catch (e) {
                return 'error:' + (e && e.message);
              }
            })();
            """.trimIndent()
                                    ) { ret -> Log.d("NaverPayWebView", "fallback bridge: $ret") }
                                }
                            } // 서버가 호출안하면 우리가 호출 (결과 페이지 도달)
                            override fun onReceivedHttpError(
                                view: WebView,
                                request: android.webkit.WebResourceRequest,
                                errorResponse: android.webkit.WebResourceResponse
                            ) {
                                android.util.Log.e("NaverPayWebView", "HTTP ${errorResponse.statusCode} on ${request.url}")
                            }
                            override fun onReceivedError(
                                view: WebView,
                                request: android.webkit.WebResourceRequest,
                                error: android.webkit.WebResourceError
                            ) {
                                android.util.Log.e("NaverPayWebView", "ERR ${error.errorCode} on ${request.url}: ${error.description}")
                            }
                        }

                        addJavascriptInterface(object {
                            @android.webkit.JavascriptInterface
                            fun onPaymentComplete(json: String) {
                                android.os.Handler(android.os.Looper.getMainLooper()).post {
                                    try {
                                        val o = org.json.JSONObject(json)
                                        val result = PaymentResult(
                                            success = o.optBoolean("success", false),
                                            orderCode = o.optString("orderCode", null),
                                            paymentId = o.optString("paymentId", null),
                                            resultCode = o.optString("resultCode", null),
                                            message = o.optString("message", null)
                                        )
                                        Log.d("NaverPayScreen", "Payment result: $result")

                                        if (result.success && !navigated) {
                                            navigated = true
                                            navController.navigate(Route.FinishSplash.route) {
                                                // 결제 화면 스택 정리(원치 않으면 제거)
                                                popUpTo(Route.NaverPay.route) { inclusive = true }
                                            }
                                        } else if (!result.success) {
                                            Log.w("NaverPayScreen", "Payment failed: ${result.message ?: "unknown"}")
                                            // TODO: 실패 UI/토스트 등
                                        }
                                    } catch (e: Exception) {
                                        Log.e("NaverPayScreen", "Parse error: ${e.message}")
                                    }
                                }
                            }
                        }, "AndroidBridge")

                    }
                }
            )

            if (orderCode.isNullOrBlank() || accessToken.isNullOrBlank()) {
                Log.d("NaverPayScreen", "Waiting for orderCode/accessToken...")
                androidx.compose.material3.CircularProgressIndicator()
            }
        }
    }
}
