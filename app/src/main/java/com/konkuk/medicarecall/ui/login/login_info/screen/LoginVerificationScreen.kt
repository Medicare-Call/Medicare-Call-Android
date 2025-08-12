package com.konkuk.medicarecall.ui.login.login_info.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.component.DefaultTextField
import com.konkuk.medicarecall.ui.login.login_info.component.TopBar
import com.konkuk.medicarecall.ui.login.login_info.uistate.LoginEvent
import com.konkuk.medicarecall.ui.login.login_info.viewmodel.LoginViewModel
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import kotlinx.coroutines.launch
import kotlin.text.isDigit

@Composable
fun LoginVerificationScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    var scrollState = rememberScrollState()
    val snackBarState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val focusRequester = remember { FocusRequester() }


    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        loginViewModel.events.collect { event ->
            when (event) {
                is LoginEvent.VerificationSuccessNew -> {
                    // 인증 성공 시 회원정보 화면으로 이동
                    navController.navigate(Route.LoginMyInfo.route)
                }

                is LoginEvent.VerificationSuccessExisting -> {
                    navController.navigate(Route.LoginSeniorInfoScreen.route)
                }

                is LoginEvent.VerificationFailure -> {
                    // 인증 실패 시 스낵바 표시
                    coroutineScope.launch {
                        snackBarState.showSnackbar(
                            message = "인증번호가 올바르지 않습니다",
                            duration = SnackbarDuration.Long
                        )
                    }
                }

                else -> { /* 다른 이벤트 무시 */
                }
            }
        }
    }


    Column(
        modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.bg)
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp)
            .verticalScroll(scrollState)
            .statusBarsPadding()
    ) {
        TopBar({
            navController.popBackStack()
        })
        Spacer(Modifier.height(20.dp))
        Text(
            "인증번호를\n입력해주세요",
            style = MediCareCallTheme.typography.B_26,
            color = MediCareCallTheme.colors.black
        )
        Spacer(Modifier.height(40.dp))
        DefaultTextField(
            loginViewModel.verificationCode,
            { input ->
                val filtered = input.filter { it.isDigit() }.take(6)
                loginViewModel.onVerificationCodeChanged(filtered)
            },
            placeHolder = "인증번호 입력",
            keyboardType = KeyboardType.Number,
            textFieldModifier = Modifier.focusRequester(focusRequester)
        )

        Spacer(Modifier.height(30.dp))

        CTAButton(
            type = if (loginViewModel.verificationCode.length == 6) CTAButtonType.GREEN else CTAButtonType.DISABLED,
            "확인",
            onClick = {
                // TODO: 서버에 인증번호 보내서 확인하기
                loginViewModel.confirmPhoneNumber(
                    loginViewModel.phoneNumber,
                    loginViewModel.verificationCode
                )

                loginViewModel.onVerificationCodeChanged("")


            })
    }
    SnackbarHost(snackBarState)
}