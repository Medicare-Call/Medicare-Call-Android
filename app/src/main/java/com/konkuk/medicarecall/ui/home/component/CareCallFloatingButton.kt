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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.home.HomeViewModel
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun CareCallFloatingButton(
    modifier: Modifier = Modifier,
    careCallOption: String,
    text: String,
    onClick: () -> Unit = {},
) {



    Button(
        modifier = modifier,
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MediCareCallTheme.colors.main
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
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = MediCareCallTheme.typography.SB_16,
                color = Color.White
            )
        }
    }
}


//@Preview
//@Composable
//private fun PreviewCareCallFloatingButton() {
//
//    CareCallFloatingButton(onClick = {})
//
//}