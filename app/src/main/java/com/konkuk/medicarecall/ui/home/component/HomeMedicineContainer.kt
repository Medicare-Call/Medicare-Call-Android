package com.konkuk.medicarecall.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.theme.LocalMediCareCallShadowProvider
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow

@Composable
fun HomeMedicineContainer(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {


    Card(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .figmaShadow(
                group = LocalMediCareCallShadowProvider.current.shadow03,
                cornerRadius = 14.dp
            ),

        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp)

    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            //1) Title: 복약
            Row(
                verticalAlignment = Alignment.CenterVertically

            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_pills),
                    contentDescription = "pills icon",

                    )

                Text(
                    "복약",
                    style = MediCareCallTheme.typography.SB_18,
                    color = MediCareCallTheme.colors.main,
                )

            }

            Spacer(modifier = Modifier.height(8.dp))

            //2) 복용횟수


            // 전체
            Column(
                modifier = Modifier
                    .fillMaxWidth(),

                ) {

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        "0/5",
                        style = MediCareCallTheme.typography.SB_22,
                    )


                    Text(
                        "회 하루 복용",
                        style = MediCareCallTheme.typography.R_16,
                        color = MediCareCallTheme.colors.gray6,
                    )


                }
            }

            Spacer(modifier = Modifier.height(8.dp))


            //첫번째 약
            Column(
                modifier = Modifier
                    .fillMaxWidth(),

                ) {

                Text(
                    "당뇨약",
                    style = MediCareCallTheme.typography.R_16,
                )

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        "0/3",
                        style = MediCareCallTheme.typography.SB_22,
                    )


                    Text(
                        "회 복용",
                        style = MediCareCallTheme.typography.R_16,
                        color = MediCareCallTheme.colors.gray6,
                    )


                }

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Text(
                        "다음 복약 09:00",
                        style = MediCareCallTheme.typography.R_14,
                        color = MediCareCallTheme.colors.gray4,
                    )


                }
            }

            Spacer(modifier = Modifier.height(8.dp))


            //두번째약
            Column(
                modifier = Modifier
                    .fillMaxWidth(),

                ) {

                Text(
                    "혈압약",
                    style = MediCareCallTheme.typography.R_16,
                )

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        "0/2",
                        style = MediCareCallTheme.typography.SB_22,
                    )


                    Text(
                        "회 복용",
                        style = MediCareCallTheme.typography.R_16,
                        color = MediCareCallTheme.colors.gray6,
                    )


                }

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.Bottom
                ) {


                    Text(
                        "다음 복약 09:00",
                        style = MediCareCallTheme.typography.R_14,
                        color = MediCareCallTheme.colors.gray4,
                    )


                }
            }


        }

    }
}

@Preview
@Composable
fun PreviewHomeMedicineContainer() {


    HomeMedicineContainer(onClick = {})
}