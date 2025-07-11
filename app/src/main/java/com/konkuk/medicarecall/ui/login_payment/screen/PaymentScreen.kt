package com.konkuk.medicarecall.ui.login_payment.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.login_info.component.TopBar
import com.konkuk.medicarecall.ui.login_payment.component.PayResultItem
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.gray1

@Composable
fun PaymentScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    var isClicked by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.bg)
            .padding(top = 16.dp, bottom = 20.dp)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 20.dp)
        ) {
            TopBar({})
            Spacer(modifier = modifier.height(20.dp))
            Text(
                text = "결제하기",
                style = MediCareCallTheme.typography.B_26,
                color = MediCareCallTheme.colors.black
            )
            Spacer(modifier = modifier.height(26.dp))
        }
        Column(
            modifier = modifier
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(MediCareCallTheme.colors.gray1)
            )

            Column(
                modifier = modifier.padding(horizontal = 20.dp)
                    .padding(vertical = 20.dp)
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "선택한 어르신",
                        style = MediCareCallTheme.typography.B_17,
                        color = MediCareCallTheme.colors.black
                    )
                    Spacer(modifier = modifier.width(8.dp))
                    Text(
                        text = "3명",
                        style = MediCareCallTheme.typography.R_14,
                        color = MediCareCallTheme.colors.gray5
                    )
                }
                Spacer(modifier = modifier.height(20.dp))
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MediCareCallTheme.colors.gray3,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .background(
                            MediCareCallTheme.colors.white, shape = RoundedCornerShape(14.dp)
                        )
                        .padding(16.dp)
                ) {
                    PayResultItem("김옥자", "29,000")
                    PayResultItem("박막례", "29,000")
                }
            }

            Column(
                modifier = modifier.padding(horizontal = 20.dp)
                    .padding(top = 20.dp)
            ) {
                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "결제 금액",
                        style = MediCareCallTheme.typography.B_17,
                        color = MediCareCallTheme.colors.black
                    )
                    Spacer(modifier = modifier.weight(1f))
                    Text(
                        text = "₩58,000/월",
                        style = MediCareCallTheme.typography.SB_14,
                        color = MediCareCallTheme.colors.main
                    )
                }
                Spacer(modifier = modifier.height(20.dp))
                Button(
                    modifier = modifier.fillMaxWidth().padding(vertical = 16.dp),
                    onClick = { if (isClicked) isClicked = false else isClicked = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MediCareCallTheme.colors.bg
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(
                        (1.2).dp,
                        color = if (isClicked) MediCareCallTheme.colors.main else MediCareCallTheme.colors.gray3
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_naver_pay),
                        contentDescription = "네이버페이 아이콘",
                        modifier = modifier.height(18.dp).width(61.dp)
                    )
                }
                Spacer(modifier = modifier.height(88.dp))
                CTAButton(
                    type = if (isClicked) CTAButtonType.GREEN else CTAButtonType.DISABLED,
                    text = "결제하기",
                    onClick = {})
            }
        }
    }
}

@Preview
@Composable
private fun PaymentScreenPreview() {
    PaymentScreen()
}