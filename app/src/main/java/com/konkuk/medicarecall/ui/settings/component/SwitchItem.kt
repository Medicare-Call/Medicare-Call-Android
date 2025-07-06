package com.konkuk.medicarecall.ui.settings.component

import android.R.attr.checked
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R

@Composable
fun SwitchItem(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val imageRes = if (checked) R.drawable.img_switch_on else R.drawable.img_switch_off

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = modifier
            .size(width = 52.dp, height = 32.dp)
            .clickable { onCheckedChange(!checked) }
    )
}

@Preview(showBackground = true, widthDp = 100, heightDp = 60)
@Composable
fun SwitchItemOffPreview() {
    SwitchItem(
        checked = false,
        onCheckedChange = {}
    )
}

@Preview(showBackground = true, widthDp = 100, heightDp = 60)
@Composable
fun SwitchItemOnPreview() {
    SwitchItem(
        checked = true,
        onCheckedChange = {}
    )
}

@Preview(showBackground = true, widthDp = 100, heightDp = 60)
@Composable
fun SwitchItemInteractivePreview() {
    var checked by remember { mutableStateOf(false) }
    SwitchItem(
        checked = checked,
        onCheckedChange = { checked = it }
    )
}

