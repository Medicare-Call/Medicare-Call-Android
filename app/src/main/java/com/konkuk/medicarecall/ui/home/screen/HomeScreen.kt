package com.konkuk.medicarecall.ui.home.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Text("Home Screen", modifier = modifier)
    
}



@Preview(showBackground = true, heightDp = 1400)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}