package com.konkuk.medicarecall.ui.login_senior.screen

import android.R.attr.onClick
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.component.ChipItem
import com.konkuk.medicarecall.ui.component.DefaultDropdown
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

        Row {
            seniorList.forEach { senior ->
                val selected = false
                OutlinedButton(
                    onClick = {

                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selected) MediCareCallTheme.colors.main else MediCareCallTheme.colors.white,
                        contentColor = if (selected) MediCareCallTheme.colors.g50 else MediCareCallTheme.colors.gray2,

                        ),
                    shape = RoundedCornerShape(100.dp),
                    border = BorderStroke(
                        width = if (selected) 0.dp else (1.2).dp,
                        color = if (selected) MediCareCallTheme.colors.main else MediCareCallTheme.colors.gray2
                    ),
                ) {
                    Text(text = senior.name, style = MediCareCallTheme.typography.R_14)
                }
                Spacer(modifier = Modifier.width(8.dp)) // 버튼 간격
            }
        }
        Spacer(Modifier.height(20.dp))

        MedInfoItem()

        Spacer(Modifier.height(20.dp))
        Text(
            "특이사항",
            color = MediCareCallTheme.colors.gray7,
            style = MediCareCallTheme.typography.M_17
        )
        Spacer(Modifier.height(10.dp))
        ChipItem("수면문제", {})
        Spacer(Modifier.height(10.dp))


        DefaultDropdown(
            HealthIssueType.values().map { it.displayName }.toList(),
            "특이사항 선택하기",
            null,
            scrollState
        )
        CTAButton(CTAButtonType.GREEN, "다음", {}, Modifier.padding(top = 30.dp, bottom = 20.dp))
    }
}