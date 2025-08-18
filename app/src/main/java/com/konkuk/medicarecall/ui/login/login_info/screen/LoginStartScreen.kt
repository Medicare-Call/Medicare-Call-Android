package com.konkuk.medicarecall.ui.login.login_info.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.login.login_info.viewmodel.LoginViewModel
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.model.NavigationDestination
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun LoginStartScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    val navigationDestination by loginViewModel.navigationDestination.collectAsState()

    LaunchedEffect(navigationDestination) {
        navigationDestination?.let { destination ->
            val route = when (destination) {
                is NavigationDestination.GoToLogin -> Route.LoginPhone.route
                is NavigationDestination.GoToRegisterElder -> Route.LoginElderInfoScreen.route
                is NavigationDestination.GoToTimeSetting -> Route.SetCall.route
                is NavigationDestination.GoToPayment -> Route.Payment.route
                is NavigationDestination.GoToHome -> Route.Home.route
            }
            navController.navigate(route)
            loginViewModel.onNavigationHandled()
        }
    }

    Box(
        modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.main)
            .navigationBarsPadding()
    ) {
        Image(
            painter = painterResource(R.drawable.bg_login_start),
            "로그인 시작 배경 이미지",
            modifier
                .fillMaxSize()
                .align(Alignment.Center),
            contentScale = ContentScale.FillBounds

        )

        CTAButton(
            type = CTAButtonType.WHITE,
            "시작하기",
            {
                loginViewModel.checkStatus()
            },
            modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
                .padding(horizontal = 20.dp)

        )

    }
}