package com.konkuk.medicarecall.ui.login_senior.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.component.DefaultDropdown
import com.konkuk.medicarecall.ui.component.DefaultTextField
import com.konkuk.medicarecall.ui.component.GenderToggleButton
import com.konkuk.medicarecall.ui.login_info.component.TopBar
import com.konkuk.medicarecall.ui.login_info.uistate.LoginUiState
import com.konkuk.medicarecall.ui.login_info.viewmodel.LoginViewModel
import com.konkuk.medicarecall.ui.login_senior.LoginSeniorViewModel
import com.konkuk.medicarecall.ui.login_senior.component.SeniorInputForm
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.model.RelationshipType
import com.konkuk.medicarecall.ui.model.SeniorLivingType
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.util.DateOfBirthVisualTransformation
import com.konkuk.medicarecall.ui.util.PhoneNumberVisualTransformation

@Composable
fun LoginSeniorInfoScreen(
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
            .imePadding(),
    ) {


        TopBar({
            navController.popBackStack()
        })
        Spacer(Modifier.height(20.dp))
        Text(
            "어르신 등록하기",
            style = MediCareCallTheme.typography.B_26,
            color = MediCareCallTheme.colors.black
        )
        Spacer(Modifier.height(30.dp))
        repeat(loginSeniorViewModel.elders) { index ->
            SeniorInputForm(
                loginSeniorViewModel,
                scrollState,
                index == loginSeniorViewModel.expandedFormIndex,
                index
            )
        }



        Box(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(MediCareCallTheme.colors.g50)
                .border(
                    1.2.dp,
                    color = MediCareCallTheme.colors.main,
                    shape = RoundedCornerShape(14.dp)
                )
                .clickable {
                    if (loginSeniorViewModel.elders < 5) {
                        loginSeniorViewModel.elders++
                        loginSeniorViewModel.expandedFormIndex = loginSeniorViewModel.elders - 1
                        loginSeniorViewModel.onNameChanged(
                            loginSeniorViewModel.expandedFormIndex,
                            ""
                        )
                        loginSeniorViewModel.onDOBChanged(
                            loginSeniorViewModel.expandedFormIndex,
                            ""
                        )
                        loginSeniorViewModel.onRelationshipChanged(
                            loginSeniorViewModel.expandedFormIndex,
                            ""
                        )
                        loginSeniorViewModel.onGenderChanged(
                            loginSeniorViewModel.expandedFormIndex,
                            null
                        )
                        loginSeniorViewModel.onLivingTypeChanged(
                            loginSeniorViewModel.expandedFormIndex,
                            ""
                        )
                        loginSeniorViewModel.onPhoneNumberChanged(
                            loginSeniorViewModel.expandedFormIndex,
                            ""
                        )


                    }
                }
        ) {
            Row(
                Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.Center)

            ) {
                Icon(
                    painterResource(R.drawable.ic_plus), contentDescription = "플러스 아이콘",
                    tint = MediCareCallTheme.colors.main
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "어르신 더 추가하기",
                    color = MediCareCallTheme.colors.main,
                    style = MediCareCallTheme.typography.B_17
                )
            }
        }

        Spacer(Modifier.height(30.dp))
        CTAButton(CTAButtonType.GREEN, "다음", {
            navController.navigate(Route.LoginSeniorMedInfoScreen.route)
        }, modifier.padding(bottom = 20.dp))
    }
}
