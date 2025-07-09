package com.konkuk.medicarecall.ui.login_payment.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.model.TimeSettingType
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun TimeSettingItem(category : String, timeType : TimeSettingType, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = category, style = MediCareCallTheme.typography.M_17)
        Spacer(modifier = modifier.height(10.dp))
        Box(
            modifier = modifier.fillMaxWidth()
                .background(
                    color = if (timeType == TimeSettingType.FIRST)
                        MediCareCallTheme.colors.g200
                    else
                        MediCareCallTheme.colors.g50,
                    shape = RoundedCornerShape(14.dp)
                )
                .border(
                    width = (1.2).dp,
                    color = MediCareCallTheme.colors.main,
                    shape = RoundedCornerShape(14.dp)
                ).padding(vertical = 12.dp)
            ,
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = modifier.padding(horizontal = (9.5).dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "추가 아이콘",
                    modifier = modifier.size(20.dp),
                    tint = MediCareCallTheme.colors.main
                )
                Spacer(modifier = modifier.width(8.dp))
                Text(
                    text = "시간 설정하기",
                    style = MediCareCallTheme.typography.B_17,
                    color = MediCareCallTheme.colors.main
                )
            }
        }
    }
}