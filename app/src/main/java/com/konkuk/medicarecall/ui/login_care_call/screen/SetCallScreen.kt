package com.konkuk.medicarecall.ui.login_care_call.screen

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.login_care_call.component.BenefitItem
import com.konkuk.medicarecall.ui.login_care_call.component.TimePickerBottomSheet
import com.konkuk.medicarecall.ui.login_care_call.component.TimeSettingItem
import com.konkuk.medicarecall.ui.login_info.component.TopBar
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.model.TimeSettingType
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme


@Composable
fun SetCallScreen(
    modifier: Modifier = Modifier,
    name: String,
    onBack: () -> Unit = {},
    navController: NavHostController
) {
    val scrollState = rememberScrollState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var editingSlot by remember { mutableStateOf(TimeSettingType.FIRST) }

    var firstTime by remember { mutableStateOf<Triple<Int, Int, Int>?>(null) }
    var secondTime by remember { mutableStateOf<Triple<Int, Int, Int>?>(null) }

    // helper: Triple을 "오전/오후 hh시 mm분" 형태로 바꿔주는 함수
    fun Triple<Int, Int, Int>.toDisplayString(): String {
        val (amPm, h, m) = this
        val period = if (amPm == 0) "오전" else "오후"
        return "${period} ${h.toString().padStart(2, '0')}시 ${m.toString().padStart(2, '0')}분"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.bg)
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 20.dp)

    ) {
        TopBar(onClick = onBack)
        Spacer(modifier = modifier.height(20.dp))
        Column(
            modifier = modifier.verticalScroll(scrollState)
        ) {
            Text(
                text = "케이콜 설정",
                style = MediCareCallTheme.typography.M_17,
                color = MediCareCallTheme.colors.gray8
            )
            Spacer(modifier = modifier.height(20.dp))
            Text(
                text = "$name 님께 맞는\n 맞춤형 케어플랜을 만들었어요",
                style = MediCareCallTheme.typography.B_26,
                color = MediCareCallTheme.colors.black,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = modifier.height(24.dp))
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(MediCareCallTheme.colors.white, shape = RoundedCornerShape(20.dp))
                    .border(
                        width = (1.2).dp,
                        color = MediCareCallTheme.colors.gray2,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = modifier
                        .background(
                            color = MediCareCallTheme.colors.g100,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = "매일 2회 풀케어",
                        style = MediCareCallTheme.typography.R_14,
                        color = MediCareCallTheme.colors.main
                    )
                }

                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "프리미엄",
                        color = MediCareCallTheme.colors.gray7,
                        style = MediCareCallTheme.typography.B_26
                    )
                    Spacer(modifier = modifier.weight(1f))
                    Text(
                        text = "₩29,000/월",
                        color = MediCareCallTheme.colors.main,
                        style = MediCareCallTheme.typography.B_17
                    )
                }

                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .width(1.dp)
                        .background(MediCareCallTheme.colors.gray2)
                )
                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BenefitItem("매일 2회 케어콜 제공")
                    BenefitItem("건강 리포트 제공")
                    BenefitItem("무제한 보호자 지정")
                }
            }
            Spacer(modifier = modifier.height(30.dp))

            // 전화 시간대 설정
            Text(
                text = "전화 시간대",
                style = MediCareCallTheme.typography.M_17,
                color = MediCareCallTheme.colors.gray8
            )
            Spacer(modifier = modifier.height(30.dp))
            TimeSettingItem(
                category = "1차",
                timeType = TimeSettingType.FIRST,
                timeText = firstTime?.toDisplayString(),
                modifier = Modifier.clickable {
                    editingSlot = TimeSettingType.FIRST
                    showBottomSheet = true
                }
            )
            Spacer(modifier = modifier.height(20.dp))

//            TimeSettingItem(
//                category = "2차",
//                timeType = TimeSettingType.SECOND,
//                timeText = secondTime?.toDisplayString(),
//                modifier = Modifier.clickable {
//                    editingSlot = TimeSettingType.SECOND
//                    showBottomSheet = true
//                }
//            ) // 여기 없앨 수도 있음
            Spacer(modifier = modifier.height(30.dp))

            // 안내 사항
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = MediCareCallTheme.colors.gray1,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 12.dp)
            ) {
                Text(
                    text = "안내사항",
                    style = MediCareCallTheme.typography.M_17,
                    color = MediCareCallTheme.colors.gray8
                )
                Spacer(modifier = modifier.height(12.dp))
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "부재중일 경우 5분 단위로 3회 재발신, 전화 설정에서 수정 가능",
                        style = MediCareCallTheme.typography.R_15,
                        color = MediCareCallTheme.colors.gray8
                    )
                    Text(
                        text = "AI 특성상 인식 오류가 있을 수 있습니다",
                        style = MediCareCallTheme.typography.R_15,
                        color = MediCareCallTheme.colors.gray8
                    )
                    Text(
                        text = "케어콜 번호를 어르신 휴대전화 주소록에 저장해주세요",
                        style = MediCareCallTheme.typography.R_15,
                        color = MediCareCallTheme.colors.gray8
                    )
                }
            }
            Spacer(modifier = modifier.height(30.dp))
            CTAButton(
                CTAButtonType.GREEN,
                text = "확인",
                { navController.navigate(Route.Payment.route) }) // 입력여부에 따라 Type 바뀌도록 수정 필요
            if (showBottomSheet) {
                TimePickerBottomSheet(
                    visible = true,
                    // 기존에 선택됐던 값을 다시 초기값으로 넘겨주면 UX가 매끄러워집니다.
                    initialFirstAmPm   = firstTime?.first  ?: 0,
                    initialFirstHour   = firstTime?.second ?: 12,
                    initialFirstMinute = firstTime?.third  ?: 0,
                    initialSecondAmPm   = secondTime?.first  ?: 0,
                    initialSecondHour   = secondTime?.second ?: 6,
                    initialSecondMinute = secondTime?.third  ?: 30,
                    onDismiss = { showBottomSheet = false },
                    onConfirm = { fAm, fH, fM, sAm, sH, sM ->
                        firstTime  = Triple(fAm,  fH,  fM)
                        secondTime = Triple(sAm,  sH,  sM)
                        showBottomSheet = false
                    },
                )
            }
        }
    }
}

