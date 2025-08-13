package com.konkuk.medicarecall.ui.splash.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.konkuk.medicarecall.MainActivity
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.splash.viewmodel.SplashViewModel
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val viewModel: SplashViewModel = hiltViewModel()

    LaunchedEffect(key1 = Unit) {

        delay(1500L)
        navController.navigate(Route.LoginStart.route) {
            popUpTo(Route.AppSplash.route) {
                inclusive = true
            }
        }

    }

    Box(
        Modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.main),
    ) {
        Image(
            painterResource(R.drawable.bg_splash),
            "Medicare Call 스플래시",
            Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}