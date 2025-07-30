package com.konkuk.medicarecall.ui.login_payment.screen

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

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
                    modifier = modifier.size(24.dp).clickable{onBack()},
                    tint = Color.Black
                )
            }
        )
        Box(modifier = modifier.fillMaxSize().background(MediCareCallTheme.colors.gray2).clickable(onClick = {navController.navigate(
            Route.FinishSplash.route)}),
            contentAlignment = Alignment.Center
        ) {
            Text("네이버 결제 웹",
                style = MediCareCallTheme.typography.B_26,
                color = MediCareCallTheme.colors.black,
            )

        }
    }
}