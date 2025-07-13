package com.konkuk.medicarecall.ui.homedetail.glucoselevel.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun GlucoseTimingButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .clip(RoundedCornerShape(40))
            .background(
                if (selected) MediCareCallTheme.colors.main
                else Color.White
            )
            .border(
                1.5.dp, MediCareCallTheme.colors.main,
                RoundedCornerShape(40)
            )
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MediCareCallTheme.typography.SB_18,
            color = if (selected) Color.White else MediCareCallTheme.colors.main
        )
    }

}


@Preview
@Composable
private fun PreviewGlucoseTimingButtonSelected() {
    GlucoseTimingButton(
        text = "공복",
        selected = true
    )
}

@Preview
@Composable
private fun PreviewGlucoseTimingButtonUnselected() {
    GlucoseTimingButton(
        text = "식후",
        selected = false
    )
}