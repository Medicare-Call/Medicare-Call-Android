package com.konkuk.medicarecall.ui.login_senior.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.konkuk.medicarecall.ui.component.DefaultDropdown
import com.konkuk.medicarecall.ui.component.DefaultTextField
import com.konkuk.medicarecall.ui.component.GenderToggleButton
import com.konkuk.medicarecall.ui.login_info.component.TopBar
import com.konkuk.medicarecall.ui.login_info.uistate.LoginUiState
import com.konkuk.medicarecall.ui.login_info.viewmodel.LoginViewModel
import com.konkuk.medicarecall.ui.model.RelationshipType
import com.konkuk.medicarecall.ui.model.SeniorLivingType
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.util.DateOfBirthVisualTransformation
import com.konkuk.medicarecall.ui.util.PhoneNumberVisualTransformation

@Composable
fun LoginSeniorInfoScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
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
        TopBar({
            loginViewModel.updateLoginUiState(LoginUiState.Start)
            navController.popBackStack()
        })
        Spacer(Modifier.height(20.dp))
        Text(
            "어르신 등록하기",
            style = MediCareCallTheme.typography.B_26,
            color = MediCareCallTheme.colors.black
        )
        Spacer(Modifier.height(40.dp))
        Column {
            DefaultTextField(
                value = "",
                onValueChange = {},
                category = "이름",
                placeHolder = "이름"
            )
            Spacer(Modifier.height(20.dp))
            DefaultTextField(
                "",
                { },
                category = "생년월일",
                placeHolder = "YYYY / MM / DD",
                keyboardType = KeyboardType.Number,
                visualTransformation = DateOfBirthVisualTransformation()
            )
            Spacer(Modifier.height(20.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    "성별",
                    color = MediCareCallTheme.colors.gray7,
                    style = MediCareCallTheme.typography.M_17
                )

                GenderToggleButton(null) { }
            }
            Spacer(Modifier.height(20.dp))
            DefaultTextField(
                "",
                { },
                category = "휴대폰 번호",
                placeHolder = "010-1234-5678",
                keyboardType = KeyboardType.Number,
                visualTransformation = PhoneNumberVisualTransformation()
            )
            Spacer(Modifier.height(20.dp))


            DefaultDropdown(
                enumList = RelationshipType.values().map { it.displayName }
                    .toList(),
                placeHolder = "관계 선택하기",
                category = "어르신과의 관계",
                scrollState
            )


            Spacer(Modifier.height(20.dp))

            DefaultDropdown(
                enumList = SeniorLivingType.values().map { it.displayName }
                    .toList(),
                placeHolder = "거주방식을 선택해주세요",
                category = "어르신 거주 방식",
                scrollState
            )


        }
    }
}