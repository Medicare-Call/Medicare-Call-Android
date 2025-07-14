package com.konkuk.medicarecall.ui.login_senior.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
            .imePadding()
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
        Spacer(Modifier.height(40.dp))
        Column {
            DefaultTextField(
                value = loginSeniorViewModel.name,
                onValueChange = { loginSeniorViewModel.onNameChanged(it) },
                category = "이름",
                placeHolder = "이름"
            )
            Spacer(Modifier.height(20.dp))
            DefaultTextField(
                loginSeniorViewModel.dateOfBirth,
                { input ->
                    val filtered = input.filter { it.isDigit() }.take(8)
                    loginSeniorViewModel.onDOBChanged(filtered)
                },
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

                GenderToggleButton(loginSeniorViewModel.isMale) {
                    loginSeniorViewModel.onGenderChanged(
                        it
                    )
                }
            }
            Spacer(Modifier.height(20.dp))
            DefaultTextField(
                loginSeniorViewModel.phoneNumber,
                { input ->
                    val filtered = input.filter { it.isDigit() }.take(11)
                    loginSeniorViewModel.onPhoneNumberChanged(filtered)
                },
                category = "휴대폰 번호",
                placeHolder = "010-1234-5678",
                keyboardType = KeyboardType.Number,
                visualTransformation = PhoneNumberVisualTransformation()
            )
            Spacer(Modifier.height(20.dp))


            DefaultDropdown(
                enumList = RelationshipType.entries.map { it.displayName }
                    .toList(),
                placeHolder = "관계 선택하기",
                category = "어르신과의 관계",
                scrollState
            )


            Spacer(Modifier.height(20.dp))

            DefaultDropdown(
                enumList = SeniorLivingType.entries.map { it.displayName }
                    .toList(),
                placeHolder = "거주방식을 선택해주세요",
                category = "어르신 거주 방식",
                scrollState
            )

            Spacer(Modifier.height(20.dp))

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
}