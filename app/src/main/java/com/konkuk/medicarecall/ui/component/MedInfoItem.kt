package com.konkuk.medicarecall.ui.component

import android.R.attr.onClick
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

enum class MedicationTime(val time: String) {
    MORNING("아침"),
    LUNCH("점심"),
    DINNER("저녁")
}

@Composable
fun MedInfoItem(modifier: Modifier = Modifier) {
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

    // ① 모든 주기의 리스트 중 하나라도 비어 있지 않은지 체크
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
                Spacer(modifier = modifier.height(10.dp))
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
        // 입력필드 +  추가 버튼
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = modifier
                    .weight(1f)
                    .height(55.dp),
                value = inputText,
                shape = RoundedCornerShape(14.dp),
                onValueChange = { inputText = it },
                placeholder = {
                    Text(
                        text = "예) 당뇨약",
                        color = MediCareCallTheme.colors.gray3,
                        style = MediCareCallTheme.typography.M_17
                    )
                },
                textStyle = MediCareCallTheme.typography.M_16.copy(
                    color = MediCareCallTheme.colors.black
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MediCareCallTheme.colors.white,
                    unfocusedContainerColor = MediCareCallTheme.colors.white,
                    focusedIndicatorColor = MediCareCallTheme.colors.gray2,
                    unfocusedIndicatorColor = MediCareCallTheme.colors.gray2,
                ),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = {
                        if (inputText.isNotBlank() && selectedPeriods.value.isNotEmpty()) {
                            selectedPeriods.value.forEach { p ->
                                medsByPeriod[p]?.add(inputText.trim())
                            }
                            inputText = "" // 입력 필드 초기화
                            selectedPeriods.value = emptySet()
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = "추가 아이콘",
                            modifier = modifier.size(20.dp),
                            tint = MediCareCallTheme.colors.black
                        )
                    }
                }
            )
        }

        // 복약 주기 토글 버튼
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "복약 주기",
            style = MediCareCallTheme.typography.R_15,
            color = MediCareCallTheme.colors.gray7
        )
        Row(modifier = Modifier.padding(top = 10.dp)) {
            MedicationTime.entries.forEach { period ->
                val selected = period in selectedPeriods.value
                OutlinedButton(
                    onClick = {
                        val new = selectedPeriods.value.toMutableSet()
                        if (selected) new.remove(period) else new += period
                        selectedPeriods.value = new
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selected) MediCareCallTheme.colors.main else MediCareCallTheme.colors.white,
                        contentColor = if (selected) MediCareCallTheme.colors.g50 else MediCareCallTheme.colors.gray2,

                        ),
                    shape = RoundedCornerShape(100.dp),
                    border = BorderStroke(
                        width = if (selected) 0.dp else (1.2).dp,
                        color = if (selected) MediCareCallTheme.colors.main else MediCareCallTheme.colors.gray2
                    ),
                ) {
                    Text(text = period.time, style = MediCareCallTheme.typography.R_14)
                }
                Spacer(modifier = Modifier.width(8.dp)) // 버튼 간격
            }
        }
    }
}

@Composable
fun ChipItem(text: String, onRemove: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(100.dp),
        color = MediCareCallTheme.colors.g50,
        modifier = Modifier
            .height(33.dp)
            .border((1.2).dp, MediCareCallTheme.colors.main, shape = RoundedCornerShape(100.dp))
            .padding(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text,
                color = MediCareCallTheme.colors.main,
                style = MediCareCallTheme.typography.R_14,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "remove",
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onRemove() },
                tint = MediCareCallTheme.colors.main
            )
            Spacer(Modifier.width(12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MedicationScreenPreview() {
    MedInfoItem()
}