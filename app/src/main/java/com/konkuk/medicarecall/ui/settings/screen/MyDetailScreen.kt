package com.konkuk.medicarecall.ui.settings.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.component.DefaultTextField
import com.konkuk.medicarecall.ui.component.GenderToggleButton
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.util.DateOfBirthVisualTransformation

@Composable
fun MyDetailScreen(modifier: Modifier = Modifier) {
    var isMale by remember { mutableStateOf<Boolean?>(false) }
    var name by remember { mutableStateOf("김미연") }
    var birth by remember { mutableStateOf("19700529") }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.bg)
    ) {
        SettingsTopAppBar(
            title = "내 정보 설정",
            leftIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_settings_back),
                    contentDescription = "setting back"
                )
            },
        )

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(scrollState),
        ) {
            DefaultTextField(
                value = name,
                onValueChange = { name = it },
                category = "이름",
                placeHolder = "이름"
            )
            Spacer(modifier = modifier.height(20.dp))
            DefaultTextField(
                value = birth,
                onValueChange = { birth = it },
                category = "생년월일",
                placeHolder = "YYYY / MM / DD",
                keyboardType = KeyboardType.Number,
                visualTransformation = DateOfBirthVisualTransformation()
            )
            Spacer(modifier = modifier.height(20.dp))
            Column() {
                Text(
                    "성별",
                    style = MediCareCallTheme.typography.M_17,
                    color = MediCareCallTheme.colors.gray7
                )
                Spacer(modifier = modifier.height(10.dp))
                GenderToggleButton(
                    isMale = isMale,
                    onGenderChange =
                        { newValue ->
                            isMale = newValue
                            // TODO: 필요하다면 여기서 ViewModel 호출 등 추가 로직 실행
                        }
                )
            }
            Spacer(modifier = modifier.height(30.dp))
            CTAButton(
                type = if (name.isNotEmpty() && birth.isNotEmpty()) CTAButtonType.GREEN else CTAButtonType.DISABLED,
                text = "확인",
                onClick = { /* TODO: 저장 로직 */ }
            )
        }
    }
}

@Preview
@Composable
private fun MyDetailPreview() {
    MyDetailScreen()
}