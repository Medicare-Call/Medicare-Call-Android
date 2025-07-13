package com.konkuk.medicarecall.ui.settings.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun SettingInfoItem(category : String, value : String,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = category, style = MediCareCallTheme.typography.R_14, color = MediCareCallTheme.colors.gray4)
        Text(text = value, style = MediCareCallTheme.typography.R_16, color = MediCareCallTheme.colors.gray8)
    }
}