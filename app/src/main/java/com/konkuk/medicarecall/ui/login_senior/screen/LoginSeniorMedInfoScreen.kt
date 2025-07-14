package com.konkuk.medicarecall.ui.login_senior.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.component.ChipItem
import com.konkuk.medicarecall.ui.component.DefaultDropdown
import com.konkuk.medicarecall.ui.component.IllnessInfoItem
import com.konkuk.medicarecall.ui.component.MedInfoItem
import com.konkuk.medicarecall.ui.login_info.component.TopBar
import com.konkuk.medicarecall.ui.login_senior.LoginSeniorViewModel
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.model.HealthIssueType
import com.konkuk.medicarecall.ui.model.SeniorData
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun LoginSeniorMedInfoScreen(
    navController: NavController,
    loginSeniorViewModel: LoginSeniorViewModel,
    modifier: Modifier = Modifier
) {
    var scrollState = rememberScrollState()
    Column(
        modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.bg)
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp)
            .verticalScroll(scrollState)
            .systemBarsPadding()
            .imePadding()
    ) {
        TopBar(onClick = {
            navController.popBackStack()
        })
        Spacer(Modifier.height(30.dp))
        Text(
            "건강정보 등록하기",
            style = MediCareCallTheme.typography.B_26,
            color = MediCareCallTheme.colors.black
        )
        Spacer(Modifier.height(20.dp))
        var seniorList = listOf(
            SeniorData(
                1, "김옥자"
            ),
            SeniorData(
                2, "박막례"
            )
        ) // 임시, TODO: 추후 서버에서 데이터 받아와야 함

        // 상단 어르신 선택 Row
        Row {
            seniorList.forEachIndexed { index, senior ->

                Box(
                    Modifier
                        .clip(shape = CircleShape)
                        .background(
                            if (index == loginSeniorViewModel.selectedSenior)
                                MediCareCallTheme.colors.main
                            else MediCareCallTheme.colors.white
                        )
                        .border(
                            width = 1.2.dp,
                            color = if (index == loginSeniorViewModel.selectedSenior)
                                MediCareCallTheme.colors.main
                            else MediCareCallTheme.colors.gray2,
                            shape = CircleShape
                        )
                        .clickable(
                            interactionSource = null,
                            indication = null,
                            onClick = {
                                loginSeniorViewModel.onSeniorChanged(index)

                            }
                        )

                ) {
                    Text(
                        text = senior.name,
                        style = if (index == loginSeniorViewModel.selectedSenior)
                            MediCareCallTheme.typography.SB_14
                        else MediCareCallTheme.typography.R_14,
                        color = if (index == loginSeniorViewModel.selectedSenior)
                            MediCareCallTheme.colors.white
                        else MediCareCallTheme.colors.gray5,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp)) // 버튼 간격
            }
        }
        Spacer(Modifier.height(20.dp))
        IllnessInfoItem()
        Spacer(Modifier.height(20.dp))

        MedInfoItem()

        Spacer(Modifier.height(20.dp))
        Text(
            "특이사항",
            color = MediCareCallTheme.colors.gray7,
            style = MediCareCallTheme.typography.M_17
        )
        Spacer(Modifier.height(10.dp))

        var healthIssueList = remember { mutableStateListOf<String>() }
        Log.d("hel", "테스트")
        if (healthIssueList.isNotEmpty()) {
            Row(
                Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                healthIssueList.forEach { healthIssue ->
                    ChipItem(healthIssue) {
                        healthIssueList.remove(healthIssue)
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
        }


        DefaultDropdown(
            HealthIssueType.entries.map { it.displayName }.toList(),
            "특이사항 선택하기",
            null,
            scrollState,
            { healthIssueList.add(it) }
        )
        CTAButton(
            CTAButtonType.GREEN,
            "다음",
            { navController.navigate(Route.SetCall.route) },
            Modifier.padding(top = 30.dp, bottom = 20.dp)
        )
    }
}