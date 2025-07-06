package com.konkuk.medicarecall.ui.homedetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun MonthYearSelector(
    year: Int,                  // 년
    month: Int,                 // 월
    onMonthClick: () -> Unit,   // 상단 드롭다운 클릭 시 월 선택창 열기
) {


    // 상단 년/월 드롭다운
    Row(
        modifier = Modifier
            .fillMaxWidth(),

        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Text(
            text = "${year}년 ${month}월",
            style = MediCareCallTheme.typography.SB_20,
            color = MediCareCallTheme.colors.gray9,
            modifier = Modifier
                .clickable { onMonthClick() }
                .padding(end = 4.dp)
        )
        Icon(
            modifier = Modifier.size(16.dp),
            tint = MediCareCallTheme.colors.gray4,
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = "Month Select"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMonthYearSelector() {
    MonthYearSelector(
        year = 2025,
        month = 5,
        onMonthClick = {}
    )

}