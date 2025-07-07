package com.konkuk.medicarecall.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
fun HomeMealContainer(
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

            //1) Title: 식사
            Row(
                verticalAlignment = Alignment.CenterVertically

            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_ricebowl),
                    contentDescription = "ricebowl icon",

                    )

                Text(
                    "식사",
                    style = MediCareCallTheme.typography.SB_18,
                    color = MediCareCallTheme.colors.main,
                )

            }

            Spacer(modifier = Modifier.height(8.dp))

            //2) 아침 점심 저녁

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //아침
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .padding(horizontal = 7.dp)
                            .size(48.dp),
                        painter = painterResource(id = R.drawable.ic_ricebowl_eat),
                        contentDescription = "ricebowl icon_eat",

                        )

                    Text(
                        "아침",
                        style = MediCareCallTheme.typography.R_16,
                        color = MediCareCallTheme.colors.gray6,
                    )

                }


                //점심
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .padding(horizontal = 7.dp)
                            .size(48.dp),
                        painter = painterResource(id = R.drawable.ic_ricebowl_skip),
                        contentDescription = "ricebowl icon_eat",

                        )

                    Text(
                        "점심",
                        style = MediCareCallTheme.typography.R_16,
                        color = MediCareCallTheme.colors.gray6,
                    )

                }


                //저녁
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .padding(horizontal = 7.dp)
                            .size(48.dp),
                        painter = painterResource(id = R.drawable.ic_ricebowl_uncheck),
                        contentDescription = "ricebowl icon_eat",

                        )

                    Text(
                        "저녁",
                        style = MediCareCallTheme.typography.R_16,
                        color = MediCareCallTheme.colors.gray6,
                    )

                }


            }

        }
    }
}


@Preview()
@Composable
fun PreviewHomeMealContainer() {


    HomeMealContainer(onClick = {})

}