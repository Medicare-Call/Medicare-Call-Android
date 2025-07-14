package com.konkuk.medicarecall.ui.login_care_call.component

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerBottomSheet(visible: Boolean,
                          initialFirstAmPm: Int = 0,
                          initialFirstHour: Int = 1,
                          initialFirstMinute: Int = 0,
                          initialSecondAmPm: Int = 0,
                          initialSecondHour: Int = 1,
                          initialSecondMinute: Int = 0,
                          onDismiss: () -> Unit,
                          onConfirm: (
                              firstAmPm: Int, firstHour: Int, firstMinute: Int,
                              secondAmPm: Int, secondHour: Int, secondMinute: Int
                          ) -> Unit) {
    if (!visible) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    // 1차, 2차 각각의 시간 상태 관리
    var firstAmPm   by remember { mutableStateOf(initialFirstAmPm) }
    var firstHour   by remember { mutableStateOf(initialFirstHour) }
    var firstMinute by remember { mutableStateOf(initialFirstMinute) }

    var secondAmPm   by remember { mutableStateOf(initialSecondAmPm) }
    var secondHour   by remember { mutableStateOf(initialSecondHour) }
    var secondMinute by remember { mutableStateOf(initialSecondMinute) }

    // 탭 구성
    var tabIndex by remember {mutableStateOf(0)}

    val tabs = listOf("1차", "2차")
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MediCareCallTheme.colors.bg,
        dragHandle = { BottomSheetDefaults.DragHandle(
            color = Color.Transparent,
        )},
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MediCareCallTheme.colors.bg)
                .padding(vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "시간 설정", style = MediCareCallTheme.typography.M_20, color = MediCareCallTheme.colors.black)
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
                    onClick = {tabIndex = i},
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
                    TimeWheelPicker (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(206.dp),
                        initialAmPm = firstAmPm,
                        initialHour = firstHour,
                        initialMinute = firstMinute,
                    ) {
                        amPm, hour, minute ->
                        firstAmPm = amPm; firstHour = hour; firstMinute = minute
                    }
                } else {
                    TimeWheelPicker (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(206.dp),
                        initialAmPm = secondAmPm,
                        initialHour = secondHour,
                        initialMinute = secondMinute,
                    ) {
                        amPm, hour, minute ->
                        secondAmPm = amPm; secondHour = hour; secondMinute = minute
                    }
                }
            }
            Spacer(modifier = Modifier.height(38.dp))
            if (tabIndex == 0) {
                CTAButton(
                    CTAButtonType.GREEN, "다음", {tabIndex = 1}, modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp
                        )
                )
            } else {
                CTAButton(
                    CTAButtonType.GREEN, "확인",{
                        onConfirm(
                            firstAmPm, firstHour, firstMinute,
                            secondAmPm, secondHour, secondMinute
                        )
                        onDismiss()
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