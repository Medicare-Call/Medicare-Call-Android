package com.konkuk.medicarecall.ui.homedetail.glucoselevel.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun GlucoseTimingButton(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .height(40.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(40.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = MediCareCallTheme.colors.main
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) MediCareCallTheme.colors.main else Color.White
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = text,
                style = MediCareCallTheme.typography.SB_18,
                color = if (selected) Color.White else MediCareCallTheme.colors.main
            )
        }
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