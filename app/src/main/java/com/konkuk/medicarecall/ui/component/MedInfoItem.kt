package com.konkuk.medicarecall.ui.component

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import kotlinx.coroutines.selects.select

enum class MedicationTime(val time: String) {
    MORNING("아침"),
    LUNCH("점심"),
    DINNER("저녁")
}

@Composable
fun MedInfoItem(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }
    // 복약 주기 선택 상태
    val selectedPeriods = remember { mutableStateOf(setOf<MedicationTime>()) }
    // 주기별 복약 리스트
    val medsByPeriod = remember {
        mutableStateMapOf<MedicationTime, SnapshotStateList<String>>().apply {
            MedicationTime.values().forEach { period ->
                // 각 주기별로 'mutableStateListOf()'를 할당
                this[period] = mutableStateListOf()
            }
        }
    }

    // 모든 주기의 리스트 중 하나라도 비어 있지 않은지 체크
    val hasAnyMeds = medsByPeriod.values.any { it.isNotEmpty() }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            "복약 정보",
            style = MediCareCallTheme.typography.M_17,
            color = MediCareCallTheme.colors.gray7
        )
        MedicationTime.entries.forEach { period ->
            val list = medsByPeriod[period]!!
            if (list.isNotEmpty()) {
                Spacer(modifier = modifier.height(20.dp))
                Text(
                    period.time,
                    style = MediCareCallTheme.typography.R_15,
                    color = MediCareCallTheme.colors.gray5
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(bottom = 16.dp, top = 10.dp)
                ) {
                    list.forEach { name ->
                        ChipItem(
                            text = name,
                            onRemove = {
                                medsByPeriod[period]?.remove(name)
                            },
                        )
                        Spacer(Modifier.width(10.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(if (hasAnyMeds) 20.dp else 10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            MedicationTime.entries.forEach { period ->
                val selected = period in selectedPeriods.value
                Box(
                    Modifier
                        .clip(CircleShape)
                        .background(
                            if (selected) MediCareCallTheme.colors.main
                            else MediCareCallTheme.colors.white
                        )
                        .border(
                            width = 1.2.dp,
                            color = if (selected) MediCareCallTheme.colors.main
                            else MediCareCallTheme.colors.gray2,
                            shape = CircleShape
                        )
                        .clickable(
                            interactionSource = null,
                            indication = null,
                            onClick = {
                                val new = selectedPeriods.value.toMutableSet()
                                if (selected) new.remove(period) else new += period
                                selectedPeriods.value = new
                            }
                        )
                ) {
                    Text(
                        text = period.time,
                        color = if (selected) MediCareCallTheme.colors.g50
                                else MediCareCallTheme.colors.gray5,
                        style =
                            if (selected) MediCareCallTheme.typography.SB_14
                            else MediCareCallTheme.typography.R_14,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp)
                    )

                }
//
//                OutlinedButton(
//                    onClick = {
//
//                    },
//                    colors = ButtonDefaults.outlinedButtonColors(
//                        containerColor = if (selected) MediCareCallTheme.colors.main else MediCareCallTheme.colors.white,
//                        contentColor = if (selected) MediCareCallTheme.colors.g50 else MediCareCallTheme.colors.gray5,
//
//                        ),
//                    shape = RoundedCornerShape(100.dp),
//                    border = BorderStroke(
//                        width = if (selected) 0.dp else (1.2).dp,
//                        color = MediCareCallTheme.colors.gray2
//                    ),
//                ) {
//                    Text(text = period.time, style = MediCareCallTheme.typography.R_14)
//                }
                Spacer(modifier = Modifier.width(8.dp)) // 버튼 간격
            }
        }
        // 입력필드 +  추가 버튼
        AddTextField(
            inputText = inputText,
            placeHolder = "예시) 당뇨약",
            onTextChange = { inputText = it },
            clickPlus = {
                if (inputText.trim().isNotBlank() && selectedPeriods.value.isNotEmpty()) {
                    val isDuplicate = selectedPeriods.value.any { period ->
                        medsByPeriod[period]?.contains(inputText.trim()) == true
                    }
                    if (isDuplicate) {
                        // 중복된 복약 정보가 있을 경우 처리 (예: Toast 메시지 표시)
                        Toast.makeText(context, "이미 등록된 약입니다", Toast.LENGTH_SHORT).show()
                        inputText = "" // 입력 필드 초기화
                        selectedPeriods.value = emptySet()
                    } else {
                        selectedPeriods.value.forEach { p ->
                            medsByPeriod[p]?.add(inputText.trim())
                        }
                        inputText = "" // 입력 필드 초기화
                    }
                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun MedicationScreenPreview() {
    MedInfoItem()
}