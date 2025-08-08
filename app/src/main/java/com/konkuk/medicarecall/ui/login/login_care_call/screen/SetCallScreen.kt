package com.konkuk.medicarecall.ui.login.login_care_call.screen

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.login.login_care_call.component.BenefitItem
import com.konkuk.medicarecall.ui.login.login_care_call.component.TimePickerBottomSheet
import com.konkuk.medicarecall.ui.login.login_care_call.component.TimeSettingItem
import com.konkuk.medicarecall.ui.login.login_info.component.TopBar
import com.konkuk.medicarecall.ui.login.login_senior.LoginSeniorViewModel
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.model.TimeSettingType
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import kotlinx.coroutines.launch

// 어르신별 세 번의 콜 타임을 저장할 데이터 클래스
data class CallTimes(
    val first: Triple<Int, Int, Int>? = null,
    val second: Triple<Int, Int, Int>? = null,
    val third: Triple<Int, Int, Int>? = null
)

// helper: Triple을 "오전/오후 hh시 mm분" 형태로 바꿔주는 함수
fun Triple<Int, Int, Int>.toDisplayString(): String {
    val (amPm, h, m) = this
    val period = if (amPm == 0) "오전" else "오후"
    return "${period} ${h.toString().padStart(2, '0')}시 ${m.toString().padStart(2, '0')}분"
}

@Composable
fun SetCallScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    navController: NavHostController,
    loginSeniorViewModel: LoginSeniorViewModel
) {
    val scrollState = rememberScrollState() // 스크롤 상태
    var showBottomSheet by remember { mutableStateOf(false) } // 하단 시트 제어
    val seniors = loginSeniorViewModel.seniorDataList.map { it.name } // 어르신 이름 리스트

    var selectedIndex by remember { mutableStateOf(0) } // 선택된 어르신 인덱스

    // 어르신별 시간 저장 맵
    val timeMap = remember {
        mutableStateMapOf<String, CallTimes>().apply {
            seniors.forEach { put(it, CallTimes()) }
        }
    }

    val allComplete = seniors.isNotEmpty() && timeMap.values.all { it.first != null && it.second != null && it.third != null }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.bg)
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 20.dp)
            .statusBarsPadding()
    ) {
        TopBar(onClick = onBack)
        Spacer(modifier = modifier.height(20.dp))
        Column(
            modifier = modifier.verticalScroll(scrollState)
        ) {
            Text(
                text = "케이콜 설정하기",
                style = MediCareCallTheme.typography.M_17,
                color = MediCareCallTheme.colors.gray8
            )
            Spacer(modifier = modifier.height(20.dp))
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
            Spacer(modifier = modifier.height(10.dp))
            val listState = rememberLazyListState()
            val scope = rememberCoroutineScope()

            LazyRow(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(seniors) { idx, name ->
                    Text(
                        text = name,
                        modifier = Modifier
                            .border(
                                width = if (idx == selectedIndex) 0.dp else (1.2).dp,
                                color = if (idx == selectedIndex) MediCareCallTheme.colors.main else MediCareCallTheme.colors.gray2,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .background(
                                color = if (idx == selectedIndex) MediCareCallTheme.colors.main else Color.Transparent,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .clickable {
                                selectedIndex = idx
                                scope.launch {
                                    listState.animateScrollToItem(idx)
                                }
                            }
                            .padding(vertical = 6.dp, horizontal = 24.dp),
                        color = if (idx == selectedIndex) MediCareCallTheme.colors.g50 else MediCareCallTheme.colors.gray5,
                        style = if (idx == selectedIndex) MediCareCallTheme.typography.SB_14 else MediCareCallTheme.typography.R_14
                    )
                }
            }
            Spacer(modifier = modifier.height(30.dp))

            // 시간 설정 항목
            val selectedName = seniors[selectedIndex]
            val callTimes = timeMap[selectedName]!!


            if (callTimes.first == null) {
                TimeSettingItem(
                    category = "1차",
                    timeType = TimeSettingType.FIRST,
                    timeText = null,
                    modifier = Modifier.clickable {
                        showBottomSheet = true
                    }
                )
            } else {
                TimeSettingItem(
                    category = "1차",
                    timeType = TimeSettingType.FIRST,
                    timeText = callTimes.first.toDisplayString(),
                    modifier = Modifier.clickable {
                        showBottomSheet = true
                    }
                )
                    Spacer(modifier = modifier.height(20.dp))
                    TimeSettingItem(
                        category = "2차",
                        timeType = TimeSettingType.SECOND,
                        timeText = callTimes.second?.toDisplayString(),
                        modifier = Modifier.clickable {
                            showBottomSheet = true
                        }
                    )
                    Spacer(modifier = modifier.height(20.dp))
                    TimeSettingItem(
                        category = "3차",
                        timeType = TimeSettingType.THIRD,
                        timeText = callTimes.third?.toDisplayString(),
                        modifier = Modifier.clickable {
                            showBottomSheet = true
                        }
                    )
            }
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
                        text = "부재중일 경우 5분 단위로 3회 재발신, 전화 설정에서 수정 가능합니다",
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
                if (allComplete) CTAButtonType.GREEN else CTAButtonType.DISABLED,
                text = "확인",
                { if (allComplete) navController.navigate(Route.Payment.route) }) // 입력여부에 따라 Type 바뀌도록 수정 필요
            if (showBottomSheet) {
                TimePickerBottomSheet(
                    visible = true,
                    // 기존에 선택됐던 값을 다시 초기값으로 넘겨주면 UX가 매끄러워집니다.
                    initialFirstAmPm   = callTimes.first?.first  ?: 0,
                    initialFirstHour   = callTimes.first?.second ?: 12,
                    initialFirstMinute = callTimes.first?.third  ?: 0,
                    initialSecondAmPm   = callTimes.second?.first  ?: 0,
                    initialSecondHour   = callTimes.second?.second ?: 12,
                    initialSecondMinute = callTimes.second?.third  ?: 0,
                    initialThirdAmPm   = callTimes.third?.first  ?: 0,
                    initialThirdHour   = callTimes.third?.second ?: 12,
                    initialThirdMinute = callTimes.third?.third  ?: 0,
                    onDismiss = { showBottomSheet = false },
                    onConfirm = { fAm, fH, fM, sAm, sH, sM, tAm,tH,tM ->
                        timeMap[selectedName] = CallTimes(
                            first  = Triple(fAm, fH, fM),
                            second = Triple(sAm, sH, sM),
                            third  = Triple(tAm, tH, tM)
                        )
                        showBottomSheet = false
                    },
                )
            }
        }
    }
}

