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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.settings.component.PersonalInfoCard
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.settings.viewmodel.EldersHealthViewModel
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HealthInfoScreen(onBack : () -> Unit ={}, navController : NavHostController, healthInfoViewModel: EldersHealthViewModel = hiltViewModel()) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val obs = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                healthInfoViewModel.refresh() // 복귀 시 재조회
            }
        }
        lifecycleOwner.lifecycle.addObserver(obs)
        onDispose { lifecycleOwner.lifecycle.removeObserver(obs) }
    }

    val healthInfo = healthInfoViewModel.eldersInfoList
    val error = healthInfoViewModel.errorMessage

    Log.d("HealthInfoScreen", "어르신 건강정보 수: ${healthInfo.size}")
    Log.d("HealthInfoScreen", "Error Message: $error")
    Log.d("HealthInfoScreen","Elders Info: $healthInfo")
    if (healthInfo.isEmpty() && error != null) {
        Log.e("HealthInfoScreen", "Error fetching elders info: $error")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MediCareCallTheme.colors.bg)
        .statusBarsPadding()) {
        SettingsTopAppBar(
            title = "어르신 건강정보 설정",
            leftIcon = {Icon(painterResource(id = R.drawable.ic_settings_back), contentDescription = "setting back", modifier = Modifier.clickable{onBack()}, tint = MediCareCallTheme.colors.black )},
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .verticalScroll(rememberScrollState())
            ,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            Spacer(modifier = Modifier.height(20.dp))
            healthInfo.forEach {
                PersonalInfoCard(
                    name = it.name,
                    onClick = {
                        val json = Json.encodeToString(it)
                        val encodedJson = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
                        navController.navigate(
                            "${Route.HealthDetail.route}/$encodedJson"
                        ) {
                            launchSingleTop = true // 중복된 화면 방지
                            restoreState = true // 이전 상태 복원
                        }
                    }
                )
            }
//            PersonalInfoCard("김옥자",  onClick = {navController.navigate(Route.HealthDetail.route)})
//            PersonalInfoCard("박막례",  onClick = {navController.navigate(Route.HealthDetail.route)})
            Spacer(modifier = Modifier.height(20.dp))
        }

    }
}
