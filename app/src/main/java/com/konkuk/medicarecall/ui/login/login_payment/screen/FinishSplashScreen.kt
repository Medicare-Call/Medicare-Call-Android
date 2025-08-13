package com.konkuk.medicarecall.ui.login.login_payment.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.Text
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.navigation.navigateToMainAfterLogin
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.login.login_senior.LoginSeniorViewModel
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun FinishSplashScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.main)
            .padding(top = 146.dp)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_complete),
            contentDescription = "complete",
            tint = Color.Unspecified,
            modifier = modifier.size(64.dp)
        )
        Spacer(modifier = modifier.size(47.dp))
        Text(
            "모든 설정이 끝났어요!",
            style = MediCareCallTheme.typography.B_26,
            color = MediCareCallTheme.colors.white
        )
        Spacer(modifier = modifier.height(59.dp))
        Image(
            painter = painterResource(id = R.drawable.char_medi),
            contentDescription = "medi character",
            modifier = modifier
                .height(266.dp)
                .width(316.dp),
        )
        Spacer(modifier = modifier.weight(1f))
        CTAButton(
            CTAButtonType.WHITE, "확인",
            onClick = { navController.navigate(Route.Home.route) },
            onClick = {  navController.navigateToMainAfterLogin()
                      },
            modifier = modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = modifier.height(30.dp))
    }
}
