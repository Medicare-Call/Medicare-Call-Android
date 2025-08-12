package com.konkuk.medicarecall.ui.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Text
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun DiseaseNamesItem(inputText: MutableState<String>, diseaseList: MutableList<String>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "질환 정보", style = MediCareCallTheme.typography.M_17, color = MediCareCallTheme.colors.gray7)
        if (diseaseList.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                diseaseList.forEachIndexed { index, disease ->
                    ChipItem(
                        text = disease,
                        onRemove = {
                            diseaseList.removeAt(index)
                        }
                    )
                    Spacer(Modifier.width(10.dp))
                }
            }
        }
        AddTextField(inputText = inputText.value, placeHolder = "질환명", onTextChange = {inputText.value = it}, clickPlus = {if (inputText.value.trim().isNotBlank()) {
            if (diseaseList.contains(inputText.value)) {
                inputText.value = ""
            } else {

                diseaseList.add(inputText.value)
                inputText.value = ""
            }
        } })
    }
}