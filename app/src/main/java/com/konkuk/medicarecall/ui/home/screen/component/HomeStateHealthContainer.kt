package com.konkuk.medicarecall.ui.home.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun HomeStateHealthContainer(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {


    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),

        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp)
        //섀도우
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            //1) Title: 건강징후
            Row(
                verticalAlignment = Alignment.CenterVertically

            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_state_health),
                    contentDescription = "health state icon",

                    )
                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    "건강징후",
                    style = MediCareCallTheme.typography.SB_18,
                    color = MediCareCallTheme.colors.main,
                )

            }

            Spacer(modifier = Modifier.height(8.dp))

            //2) 상태
            Column(
                modifier = Modifier
                    .fillMaxWidth(),

                ) {

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "좋음",
                        style = MediCareCallTheme.typography.SB_22,
                    )



                }
            }


        }

    }
}

@Preview
@Composable
fun PreviewHomeStateHealthContainer() {

    HomeStateHealthContainer()
}