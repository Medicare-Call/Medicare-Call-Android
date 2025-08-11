package com.konkuk.medicarecall.ui.login.login_senior.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.component.DefaultDropdown
import com.konkuk.medicarecall.ui.component.DefaultTextField
import com.konkuk.medicarecall.ui.component.GenderToggleButton
import com.konkuk.medicarecall.ui.login.login_senior.LoginSeniorViewModel
import com.konkuk.medicarecall.ui.model.RelationshipType
import com.konkuk.medicarecall.ui.model.SeniorLivingType
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow
import com.konkuk.medicarecall.ui.util.DateOfBirthVisualTransformation
import com.konkuk.medicarecall.ui.util.PhoneNumberVisualTransformation

@Composable
fun SeniorInputForm(
    loginSeniorViewModel: LoginSeniorViewModel,
    scrollState: ScrollState,
    isExpanded: Boolean,
    index: Int,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        isExpanded,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            DefaultTextField(
                value = loginSeniorViewModel.nameList[index],
                onValueChange = { loginSeniorViewModel.onNameChanged(index, it) },
                category = "이름",
                placeHolder = "이름"
            )
            Spacer(Modifier.height(20.dp))
            DefaultTextField(
                loginSeniorViewModel.dateOfBirthList[index],
                { input ->
                    val filtered = input.filter { it.isDigit() }.take(8)
                    loginSeniorViewModel.onDOBChanged(index, filtered)
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

                GenderToggleButton(loginSeniorViewModel.isMaleBoolList[index]) {
                    loginSeniorViewModel.onGenderChanged(
                        index, it
                    )
                }
            }
            Spacer(Modifier.height(20.dp))
            DefaultTextField(
                loginSeniorViewModel.phoneNumberList[index],
                { input ->
                    val filtered = input.filter { it.isDigit() }.take(11)
                    loginSeniorViewModel.onPhoneNumberChanged(index, filtered)
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
                scrollState,
                { loginSeniorViewModel.onRelationshipChanged(index, it) },
                loginSeniorViewModel.relationshipList[index]
            )


            Spacer(Modifier.height(20.dp))

            DefaultDropdown(
                enumList = SeniorLivingType.entries.map { it.displayName }
                    .toList(),
                placeHolder = "거주방식을 선택해주세요",
                category = "어르신 거주 방식",
                scrollState,
                { loginSeniorViewModel.onLivingTypeChanged(index, it) },
                loginSeniorViewModel.livingTypeList[index]
            )

            Spacer(Modifier.height(20.dp))
        }
    }
    if (!isExpanded) {
        Box(
            Modifier
                .fillMaxWidth()
                .figmaShadow(MediCareCallTheme.shadow.shadow03)
                .clip(
                    RoundedCornerShape(14.dp)
                )
                .background(MediCareCallTheme.colors.white)
                .clickable { loginSeniorViewModel.expandedFormIndex = index }
        ) {
            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "${loginSeniorViewModel.nameList[index]} 님",
                        color = MediCareCallTheme.colors.gray8,
                        style = MediCareCallTheme.typography.SB_18
                    )
                    Text(
                        "삭제하기",
                        color = MediCareCallTheme.colors.negative,
                        style = MediCareCallTheme.typography.R_14,
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = {
                                loginSeniorViewModel.elders--
                                loginSeniorViewModel.expandedFormIndex = 0
                            }
                        )
                    )
                }
                Text(
                    loginSeniorViewModel.phoneNumberList[index].replaceFirst(
                        Regex("(\\d{3})(\\d{4})(\\d{4})"),
                        "$1-$2-$3"
                    ),
                    color = MediCareCallTheme.colors.gray4,
                    style = MediCareCallTheme.typography.R_14
                )
            }
        }
        Spacer(Modifier.height(30.dp))
    }
}