package com.konkuk.medicarecall.ui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.navigation.Route
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun NameBar(
    name: String,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onDropdownClick: () -> Unit
) {


    Box(modifier = modifier.background(Color.White)) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 13.5.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {


            Row(
                Modifier.clickable(
                    indication = null,
                    interactionSource = null
                ) { onDropdownClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = MediCareCallTheme.typography.SB_24,
                    color = MediCareCallTheme.colors.black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    modifier = Modifier

                        .size(18.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = "arrow down",
                    tint = MediCareCallTheme.colors.gray3
                )

            }

            Icon(
                painter = painterResource(id = R.drawable.ic_bell),
                "bell",
                tint = Color.Unspecified,
                modifier = modifier.clickable { navController.navigate(route = Route.Alarm.route) }
            )
        }

    }
}


@Preview
@Composable
fun PreviewNameBar() {

    MediCareCallTheme {

        NameBar(
            name = "김옥자",
            navController = rememberNavController(),
            onDropdownClick = {}
        )
    }

}
