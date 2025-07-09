package com.konkuk.medicarecall.ui.component

import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Text
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun IllnessInfoItem(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val illnessList = remember { mutableStateOf(listOf<String>()) }
    var inputText by remember { mutableStateOf("") }
    Column(modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
        Text(text = "질환 정보", style = MediCareCallTheme.typography.M_17, color = MediCareCallTheme.colors.gray7)
        if (illnessList.value.isNotEmpty()) {
            Spacer(modifier = modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(bottom = 16.dp, top = 10.dp)
            ) {
                illnessList.value.forEach { illness ->
                    ChipItem(
                        text = illness,
                        onRemove = {
                            illnessList.value = illnessList.value.filterNot { it == illness }
                        }
                    )
                    Spacer(Modifier.width(10.dp))
                }
            }
        }
        AddTextField(inputText = inputText, placeHolder = "질환명", onTextChange = {inputText = it}, clickPlus = {if (inputText.trim().isNotBlank()) {
            if (illnessList.value.contains(inputText)) {
                Toast
                    .makeText(context, "이미 등록된 질환입니다", Toast.LENGTH_SHORT)
                    .show()
                inputText = ""
            } else {

                illnessList.value = illnessList.value + inputText
                inputText = ""
            }
        } })
    }
}

@Preview(showBackground = true)
@Composable
private fun IllnessInfoPreview() {
        IllnessInfoItem()

}
