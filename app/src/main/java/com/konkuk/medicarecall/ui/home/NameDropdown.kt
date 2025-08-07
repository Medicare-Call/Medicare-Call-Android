package com.konkuk.medicarecall.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun NameDropdown(
    items: List<String>,
    selectedName: String,
    onDismiss: () -> Unit,
    onItemSelected: (String) -> Unit
) {
    Popup(
        alignment = Alignment.TopStart,
        onDismissRequest = { onDismiss() }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onDismiss()
                    }
            )

            // 드롭다운 메뉴

            Column(
                modifier = Modifier
                    .padding(start = 10.dp, top = 44.dp)
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
            ) {
                items.forEach { item ->
                    DropdownItem(
                        name = item,
                        selected = item == selectedName,
                        onClick = {
                            onItemSelected(item)
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun DropdownItem(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(start = 10.dp, bottom = 10.dp, top = 10.dp, end = 82.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MediCareCallTheme.typography.SB_18,
            color = if (selected) MediCareCallTheme.colors.black else MediCareCallTheme.colors.gray4
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNameDropdown() {
    MediCareCallTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            NameDropdown(
                items = listOf("김옥자", "박막례"),
                selectedName = "김옥자",
                onDismiss = {},
                onItemSelected = {}
            )
        }
    }
}