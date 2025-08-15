package com.konkuk.medicarecall.ui.login.login_senior.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.component.ChipItem
import com.konkuk.medicarecall.ui.component.DefaultDropdown
import com.konkuk.medicarecall.ui.component.DefaultSnackBar
import com.konkuk.medicarecall.ui.component.DiseaseNamesItem
import com.konkuk.medicarecall.ui.component.MedicationItem
import com.konkuk.medicarecall.ui.login.login_info.component.LoginBackButton
import com.konkuk.medicarecall.ui.login.login_senior.LoginSeniorViewModel
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.model.HealthIssueType
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun LoginSeniorMedInfoScreen(
    navController: NavController,
    loginSeniorViewModel: LoginSeniorViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val snackBarState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier
            .background(MediCareCallTheme.colors.bg)
            .statusBarsPadding(),
        snackbarHost = { DefaultSnackBar(snackBarState) },
        topBar = {
            LoginBackButton({
                navController.popBackStack()
            }, Modifier.padding(start = 20.dp, top = 30.dp))
        },
        containerColor = MediCareCallTheme.colors.bg

    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(Modifier.height(30.dp))
            Text(
                "건강정보 등록하기",
                style = MediCareCallTheme.typography.B_26,
                color = MediCareCallTheme.colors.black
            )
            Spacer(Modifier.height(20.dp))


            // 상단 어르신 선택 Row
            Row {
                loginSeniorViewModel.seniorDataList.forEachIndexed { index, senior ->

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
                                    loginSeniorViewModel.onSelectedSeniorChanged(index)

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
            DiseaseNamesItem(
                loginSeniorViewModel.diseaseInputText[loginSeniorViewModel.selectedSenior],
                loginSeniorViewModel.diseaseList[loginSeniorViewModel.selectedSenior]
            )
            Spacer(Modifier.height(20.dp))

            MedicationItem(
                loginSeniorViewModel.medMap[loginSeniorViewModel.selectedSenior],
                loginSeniorViewModel.medInputText[loginSeniorViewModel.selectedSenior],
            )

            Spacer(Modifier.height(20.dp))
            Text(
                "특이사항",
                color = MediCareCallTheme.colors.gray7,
                style = MediCareCallTheme.typography.M_17
            )
            Spacer(Modifier.height(10.dp))

            if (loginSeniorViewModel.healthIssueList[loginSeniorViewModel.selectedSenior].isNotEmpty()) {
                Row(
                    Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    loginSeniorViewModel.healthIssueList[loginSeniorViewModel.selectedSenior].forEach { healthIssue ->
                        ChipItem(healthIssue) {
                            loginSeniorViewModel.healthIssueList[loginSeniorViewModel.selectedSenior].remove(
                                healthIssue
                            )
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
                {
                    loginSeniorViewModel.healthIssueList[loginSeniorViewModel.selectedSenior].add(
                        it
                    )
                }
            )
            CTAButton(
                CTAButtonType.GREEN,
                "다음",
                {
                    if (loginSeniorViewModel.getElderIds().isEmpty()) {
                        loginSeniorViewModel.createSeniorHealthDataList()
                        loginSeniorViewModel.postElderAndHealth()
                    } else {
                        loginSeniorViewModel.createSeniorHealthDataList()
                        loginSeniorViewModel.updateAllElders()
                        loginSeniorViewModel.updateAllEldersHealthInfo()
                    }
                    navController.navigate(Route.SetCall.route)
                },
                Modifier.padding(top = 30.dp, bottom = 20.dp)
            )
        }
        DefaultSnackBar(snackBarState)
    }
}