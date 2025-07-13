package com.konkuk.medicarecall.ui.settings.screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.settings.component.PersonalInfoCard
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow

@Composable
fun PersonalInfoScreen(onBack : () -> Unit ={},navController : NavHostController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MediCareCallTheme.colors.bg)) {
        SettingsTopAppBar(
            title = "어르신 개인정보 설정",
            leftIcon = {Icon(painterResource(id = R.drawable.ic_settings_back), contentDescription = "setting back", modifier = Modifier.clickable{onBack()}, tint = MediCareCallTheme.colors.black)},
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
            PersonalInfoCard("김옥자", onClick = {navController.navigate(Route.PersonalDetail.route)})
            PersonalInfoCard("박막례", onClick = {navController.navigate(Route.PersonalDetail.route)})
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .figmaShadow(
                        group = MediCareCallTheme.shadow.shadow03,
                        cornerRadius = 14.dp
                    )
                    .clip(RoundedCornerShape(14.dp))
                    .background(color = Color.White)
                    .padding(start = 20.dp)
                    .clickable {} // 클릭 이벤트 추가
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "추가 아이콘",
                    modifier = Modifier.size(20.dp),
                    tint = MediCareCallTheme.colors.gray4
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "어르신 더 추가하기",
                    style = MediCareCallTheme.typography.SB_14,
                    color = MediCareCallTheme.colors.gray4
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

    }
}