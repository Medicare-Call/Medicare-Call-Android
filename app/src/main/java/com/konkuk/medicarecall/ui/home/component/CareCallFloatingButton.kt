package com.konkuk.medicarecall.ui.home.component

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import android.Manifest

@Composable
fun CareCallFloatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val context = LocalContext.current
    val phoneNumber = remember { "01012345678" } // 테스트 번호

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
                context.startActivity(intent)
                onClick()
            } else {
                Toast.makeText(context, "전화 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }

    Button(
        modifier = modifier,
        onClick = {
            val hasPermission = ContextCompat.checkSelfPermission(
                context, Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED

            if (hasPermission) {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
                context.startActivity(intent)
                onClick()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
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