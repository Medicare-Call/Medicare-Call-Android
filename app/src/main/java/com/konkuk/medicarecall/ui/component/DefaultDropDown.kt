package com.konkuk.medicarecall.ui.component

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay

@Composable
fun <T> DefaultDropdown(
    enumList: List<T>, // Enum.values().map { it. displayName }.toList() 전달
    placeHolder: String,
    category: String? = null,
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {


    var showDropdown by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(showDropdown) {
        if (showDropdown) {
            delay(250L)
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    if (category != null) {
        Text(
            category,
            color = MediCareCallTheme.colors.gray7,
            style = MediCareCallTheme.typography.M_17
        )
        Spacer(Modifier.height(10.dp))
    }

    Column {
        OutlinedTextField(
            value = selectedOption ?: "",
            onValueChange = { },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = {
                        showDropdown = !showDropdown
                    }
                ),
            placeholder = {
                Text(placeHolder, style = MediCareCallTheme.typography.M_16)
            },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledContainerColor = MediCareCallTheme.colors.white,
                disabledPlaceholderColor = MediCareCallTheme.colors.gray3,
                disabledBorderColor = MediCareCallTheme.colors.gray2,
                disabledTextColor = MediCareCallTheme.colors.black,
            ),
            readOnly = true,
            trailingIcon = {
                Icon(
                    painterResource(if (showDropdown) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down),
                    contentDescription = "드롭다운 화살표",
                    tint = MediCareCallTheme.colors.black
                )
            },
            singleLine = true,
            textStyle = MediCareCallTheme.typography.M_17
        )


        AnimatedVisibility(visible = showDropdown) {
            val dropdownScrollState = rememberScrollState()


            Box(
                Modifier
                    .fillMaxHeight()
                    .padding(top = 8.dp)
                    .figmaShadow(
                        group = MediCareCallTheme.shadow.shadow01,
                        cornerRadius = 14.dp
                    )
                    .clip(RoundedCornerShape(14.dp))
                    .background(MediCareCallTheme.colors.white)
                    .border(
                        1.2.dp,
                        shape = RoundedCornerShape(14.dp),
                        color = MediCareCallTheme.colors.gray1
                    )
                    .heightIn(max = 215.dp)
                    .verticalScroll(dropdownScrollState)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MediCareCallTheme.colors.white)
                ) {


                    enumList.forEach { item ->
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .drawBehind {
                                    val strokeWidth = 1.2.dp.toPx()
                                    val y = size.height - strokeWidth / 2f

                                    drawLine(
                                        color = Color(0xFFECECEC), // NavigationBar의 상단 테두리
                                        start = Offset(0f, y),
                                        end = Offset(size.width, y),
                                        strokeWidth = strokeWidth,
                                    )
                                }
                                .clickable(onClick = {
                                    selectedOption = item.toString()
                                    showDropdown = false
                                }),
                        ) {
                            Text(
                                item.toString(),
                                color = MediCareCallTheme.colors.gray8,
                                style = MediCareCallTheme.typography.M_16,
                                modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                            )
                        }
                    }

                }
            }
        }
    }
}