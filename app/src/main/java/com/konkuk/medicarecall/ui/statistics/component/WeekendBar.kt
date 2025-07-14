package com.konkuk.medicarecall.ui.statistics.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme


@Composable
fun WeekendBar(
    modifier: Modifier = Modifier,
    title: String,

    ) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,


        ) {
        // 뒤로 가기 + 이번주/저번주 + 앞으로 가기 TODO: 스와이프 적용 및 right arrow 색상처리
        Icon(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(id = R.drawable.ic_arrow_big_back),
            contentDescription = "big arrow back",
            tint = MediCareCallTheme.colors.gray3
        )


        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MediCareCallTheme.typography.SB_20,
                color = Color(0xFF444444)
            )
        }

        Icon(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(id = R.drawable.ic_arrow_big_forward),
            contentDescription = "big arrow forward",
            tint = MediCareCallTheme.colors.gray1
        )


    }

}


@Preview(showBackground = true)
@Composable
fun PreviewWeekendBar() {
    WeekendBar(
        title = "이번주"
    )

}
