package com.konkuk.medicarecall.ui.login.login_care_call.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.login_care_call.component.TimeWheelPicker
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerBottomSheet(
    visible: Boolean,
    initialTabIndex: Int = 0,
    initialFirstAmPm: Int = 0,
    initialFirstHour: Int = 1,
    initialFirstMinute: Int = 0,
    initialSecondAmPm: Int = 0,
    initialSecondHour: Int = 1,
    initialSecondMinute: Int = 0,
    initialThirdAmPm: Int = 0,
    initialThirdHour: Int = 1,
    initialThirdMinute: Int = 0,
    onDismiss: () -> Unit,
    onConfirm: (
        firstAmPm: Int, firstHour: Int, firstMinute: Int,
        secondAmPm: Int, secondHour: Int, secondMinute: Int,
        thirdAmPm: Int, thirdHour: Int, thirdMinute: Int
    ) -> Unit
) {
    if (!visible) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    // 1차, 2차 각각의 시간 상태 관리
    var firstAmPm by remember { mutableStateOf(initialFirstAmPm) }
    var firstHour by remember { mutableStateOf(initialFirstHour) }
    var firstMinute by remember { mutableStateOf(initialFirstMinute) }

    var secondAmPm by remember { mutableStateOf(initialSecondAmPm) }
    var secondHour by remember { mutableStateOf(initialSecondHour) }
    var secondMinute by remember { mutableStateOf(initialSecondMinute) }

    var thirdAmPm by remember { mutableStateOf(initialThirdAmPm) }
    var thirdHour by remember { mutableStateOf(initialThirdHour) }
    var thirdMinute by remember { mutableStateOf(initialThirdMinute) }

    // --- 스낵바 & 코루틴 ---
    val snackbarHostState = remember { androidx.compose.material3.SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun toMinutes(amPm: Int, h: Int, m: Int): Int {
        // amPm: 0=오전, 1=오후 / 시: 1~12
        val hour24 = (h % 12) + if (amPm == 1) 12 else 0
        return hour24 * 60 + m
    }

    fun validate12(): Pair<Boolean, String?> {
        val t1 = toMinutes(firstAmPm, firstHour, firstMinute)
        val t2 = toMinutes(secondAmPm, secondHour, secondMinute)
        return if (t2 <= t1) false to "2차 시간은 1차보다 늦어야 합니다." else true to null
    }

    fun validate123(): Pair<Boolean, String?> {
        val t1 = toMinutes(firstAmPm, firstHour, firstMinute)
        val t2 = toMinutes(secondAmPm, secondHour, secondMinute)
        val t3 = toMinutes(thirdAmPm, thirdHour, thirdMinute)
        return when {
            t2 <= t1 -> false to "2차 케어콜 시간은 1차 케이콜 시간보다 늦어야 해요."
            t3 <= t2 -> false to "3차 케이콜 시간은 2차 케이콜 시간보다 늦어야 해요."
//            t3 <= t1 -> false to "3차 시간은 1차보다도 늦어야 해요."
            else -> true to null
        }
    }

    suspend fun showSnack(msg: String) {
        snackbarHostState.showSnackbar(message = msg, withDismissAction = true)
    }


    // 탭 구성
    var tabIndex by remember { mutableStateOf(initialTabIndex) }

    val tabs = listOf("1차", "2차", "3차")
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MediCareCallTheme.colors.bg,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = Color.Transparent,
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MediCareCallTheme.colors.bg)
                .padding(bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "시간 설정",
                style = MediCareCallTheme.typography.M_20,
                color = MediCareCallTheme.colors.black
            )
            Spacer(modifier = Modifier.height(18.dp))

            TabRow(
                selectedTabIndex = tabIndex,
                containerColor = MediCareCallTheme.colors.bg,
                contentColor = MediCareCallTheme.colors.main,
                modifier = Modifier.fillMaxWidth(),
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[tabIndex])
                            .height(2.dp),
                        color = MediCareCallTheme.colors.main
                    )
                },
                divider = {}
            ) {
                tabs.forEachIndexed { i, title ->
                    Tab(
                        selected = (i == tabIndex),
                        onClick = {
                            // 앞으로(0->1, 1->2) 이동 시 검증
                            if (i > tabIndex) {
                                when (i) {
                                    1 -> { // 2차 탭으로 이동
                                        val (ok, msg) = validate12()
                                        if (!ok) {
                                            scope.launch { showSnack(msg!!) }
                                            return@Tab
                                        }
                                    }

                                    2 -> { // 3차 탭으로 이동
                                        val (ok, msg) = validate123()
                                        if (!ok) {
                                            scope.launch { showSnack(msg!!) }
                                            return@Tab
                                        }
                                    }
                                }
                            }
                            tabIndex = i
                        },
                        text = {
                            Text(
                                text = title,
                                color = if (i == tabIndex)
                                    MediCareCallTheme.colors.main
                                else
                                    MediCareCallTheme.colors.gray2
                            )
                        },
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            key(tabIndex) {
                if (tabIndex == 0) {
                    TimeWheelPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(206.dp),
                        initialAmPm = firstAmPm,
                        initialHour = firstHour,
                        initialMinute = firstMinute,
                    ) { amPm, hour, minute ->
                        firstAmPm = amPm; firstHour = hour; firstMinute = minute
                    }
                } else if (tabIndex == 1) {
                    TimeWheelPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(206.dp),
                        initialAmPm = secondAmPm,
                        initialHour = secondHour,
                        initialMinute = secondMinute,
                    ) { amPm, hour, minute ->
                        secondAmPm = amPm; secondHour = hour; secondMinute = minute
                    }
                } else if (tabIndex == 2) {
                    TimeWheelPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(206.dp),
                        initialAmPm = thirdAmPm,
                        initialHour = thirdHour,
                        initialMinute = thirdMinute,
                    ) { amPm, hour, minute ->
                        thirdAmPm = amPm; thirdHour = hour; thirdMinute = minute
                    }
                }
            }
            Spacer(modifier = Modifier.height(38.dp))
            if (tabIndex == 0) {
                CTAButton(
                    CTAButtonType.GREEN, "다음", onClick = {
                        val (ok, msg) = validate12()
                        if (!ok) {
                            scope.launch { showSnack(msg!!) }
                        } else {
                            tabIndex = 1
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp
                        )
                )
            } else if (tabIndex == 1) {
                CTAButton(
                    CTAButtonType.GREEN, "다음", onClick = {
                        val (ok, msg) = validate123()
                        if (!ok) {
                            scope.launch { showSnack(msg!!) }
                        } else {
                            tabIndex = 2
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp
                        )
                )
            } else if (tabIndex == 2) {
                CTAButton(
                    CTAButtonType.GREEN, "확인",
                    onClick = {
                        val (ok, msg) = validate123()
                        if (!ok) {
                            scope.launch { showSnack(msg!!) }
                        } else {
                            onConfirm(
                                firstAmPm, firstHour, firstMinute,
                                secondAmPm, secondHour, secondMinute,
                                thirdAmPm, thirdHour, thirdMinute,
                            )
                            onDismiss()
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp
                        )
                )
            }
        }
    }

}

@Preview
@Composable
private fun TimePickerBottomSheetPreview() {
    MediCareCallTheme {
        TimePickerBottomSheet(
            visible = true,
            onDismiss = {},
            onConfirm = { _, _, _, _, _, _, _, _, _ -> }
        )
    }
}