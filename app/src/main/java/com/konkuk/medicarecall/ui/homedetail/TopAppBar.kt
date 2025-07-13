package com.konkuk.medicarecall.ui.homedetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
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
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String,

    ) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 11.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,


        ) {
        // 뒤로 가기+ 상세 화면 제목
        Icon(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(id = R.drawable.ic_arrow_big_back),
            contentDescription = "big arrow back",
        )


        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MediCareCallTheme.typography.SB_20
            )
        }

        Box(modifier = Modifier.size(24.dp))


    }
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = MediCareCallTheme.colors.gray2,  // 원하는 색상
        thickness = 1.dp                         // 원하는 두께
    )
}


@Preview(showBackground = true)
@Composable
private fun PreviewTopAppBar() {
    TopAppBar(
        title = "식사"
        )

}
