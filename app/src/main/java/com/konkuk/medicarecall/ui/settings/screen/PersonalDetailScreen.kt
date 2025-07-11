package com.konkuk.medicarecall.ui.settings.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.component.DefaultDropdown
import com.konkuk.medicarecall.ui.component.GenderToggleButton
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.model.RelationshipType
import com.konkuk.medicarecall.ui.model.SeniorLivingType
import com.konkuk.medicarecall.ui.settings.component.SettingTextField
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun PersonalDetailScreen(modifier: Modifier = Modifier) {
    var isMale by remember { mutableStateOf<Boolean?>(false) }
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MediCareCallTheme.colors.bg)) {
        SettingsTopAppBar(
            title = "어르신 건강정보 설정",
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
                .verticalScroll(scrollState)
                .padding(20.dp),
        ) {
            Row() {
                Spacer(modifier = modifier.weight(1f))
                Text(
                    text = "삭제",
                    color = MediCareCallTheme.colors.negative,
                    style = MediCareCallTheme.typography.SB_16
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                SettingTextField("성함", "김옥자", "성함")
                SettingTextField("생년월일","1939 / 09 / 18", "YYYY / MM / DD")
                Column() {
                    Text("성별", style = MediCareCallTheme.typography.M_17, color = MediCareCallTheme.colors.gray7)
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
                SettingTextField("휴대폰 번호","010-1111-1111","010-1234-5678")
                DefaultDropdown(
                    enumList = RelationshipType.values().map { it.displayName }
                        .toList(),
                    placeHolder = "관계 선택하기",
                    category = "어르신과의 관계",
                    scrollState
                )
                DefaultDropdown(
                    enumList = SeniorLivingType.values().map { it.displayName }
                        .toList(),
                    placeHolder = "거주방식을 선택해주세요",
                    category = "어르신 거주 방식",
                    scrollState
                )

//                Button(
//                    modifier = modifier.fillMaxWidth().height(50.dp),
//                    shape = RoundedCornerShape(14.dp),
//                    onClick = {},
//                    colors = ButtonDefaults.buttonColors(
//                        contentColor = MediCareCallTheme.colors.white,
//                        containerColor = MediCareCallTheme.colors.main
//                    )
//
//                ) {
//                    Text("확인")
//                }

                CTAButton(
                    type = CTAButtonType.GREEN,
                    text = "확인",
                    onClick = {},
                    modifier = modifier.height(50.dp),
                )

            }


        }
    }
}

@Preview
@Composable
private fun PersonalDetailPreview() {
    PersonalDetailScreen()

}