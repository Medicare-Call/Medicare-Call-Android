package com.konkuk.medicarecall.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun NameBar(
    modifier: Modifier = Modifier

    ) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        // 왼쪽 화살표+텍스트
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = "arrow down",
                tint = MediCareCallTheme.colors.gray3
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "김옥자",
                style = MediCareCallTheme.typography.SB_24
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "님",
                style = MediCareCallTheme.typography.R_18
            )

        }

        Icon(
            painter = painterResource(id = R.drawable.ic_bell),
            "bell",
        )
    }


}

@Preview
@Composable
private fun NameBarPreview() {
    NameBar()

}
