package com.konkuk.medicarecall.ui.home.component

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.home.HomeViewModel
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun CareCallFloatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onError: (String) -> Unit = {}
) {


    val viewModel: HomeViewModel = androidx.hilt.navigation.compose.hiltViewModel()
    val context = LocalContext.current

    Button(
        modifier = modifier,
        onClick = {
            viewModel.callCareCallImmediate(
                onSuccess = {
                    Toast.makeText(context, "케어콜 요청이 전송되었습니다.", Toast.LENGTH_SHORT).show()
                    onClick()
                },
                onFailure = { msg ->
                    Toast.makeText(context, "요청 실패: $msg", Toast.LENGTH_SHORT).show()
                    onError(msg)
                }
            )
        },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = MediCareCallTheme.colors.positive
        ),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_carecall),
                contentDescription = "Care Call",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "케어콜 걸기",
                style = MediCareCallTheme.typography.SB_16,
                color = Color.White
            )
        }
    }
}


@Preview
@Composable
private fun PreviewCareCallFloatingButton() {

    CareCallFloatingButton(onClick = {})

}