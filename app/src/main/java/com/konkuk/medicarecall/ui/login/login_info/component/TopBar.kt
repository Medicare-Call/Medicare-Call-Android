package com.konkuk.medicarecall.ui.login.login_info.component

import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun TopBar(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier.fillMaxWidth().background(MediCareCallTheme.colors.bg)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_settings_back),
            contentDescription = null,
            modifier = Modifier.clickable(
                onClick = onClick
            ),
            tint = MediCareCallTheme.colors.black
        )
    }
}