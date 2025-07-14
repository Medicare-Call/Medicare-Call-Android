package com.konkuk.medicarecall.ui.settings.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.component.IllnessInfoItem
import com.konkuk.medicarecall.ui.component.MedInfoItem
import com.konkuk.medicarecall.ui.component.SpecialNoteItem
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.model.SpecialNoteType
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun HealthDetailScreen(onBack : () -> Unit ={}, navController : NavHostController,modifier: Modifier = Modifier) {
    var noteList by remember { mutableStateOf(listOf<String>()) }
    val scrollState = rememberScrollState()
    Column(modifier = modifier
        .fillMaxSize()
        .background(MediCareCallTheme.colors.bg)) {
        SettingsTopAppBar(
            modifier = modifier,
            title = "어르신 건강정보 설정",
            leftIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings_back),
                    contentDescription = "go_back",
                    modifier = modifier.size(24.dp).clickable{onBack()},
                    tint = Color.Black
                )
            }
        )
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp)
            .verticalScroll(rememberScrollState())
            ,
        ) {
            // 질환 정보
            IllnessInfoItem()
            Spacer(modifier = modifier.height(20.dp))
            // 복약정보
            MedInfoItem()
            Spacer(modifier = modifier.height(20.dp))
            // 특이사항
            SpecialNoteItem(
                enumList = SpecialNoteType.entries.map { it.displayName },
                noteList = noteList,
                onAddNote = { noteList = noteList + it },
                onRemoveNote = { noteList = noteList - it },
                placeHolder = "특이사항 선택하기",
                category = "특이사항",
                scrollState = scrollState
            )
//            Button(
//                modifier = modifier.fillMaxWidth().height(50.dp),
//                shape = RoundedCornerShape(14.dp),
//                onClick = {},
//                colors = ButtonDefaults.buttonColors(
//                    contentColor = MediCareCallTheme.colors.white,
//                    containerColor = MediCareCallTheme.colors.main
//                )
//
//            ) {
//                Text("확인")
//            }
            Spacer(modifier = modifier.height(20.dp))
            CTAButton(
                type = CTAButtonType.GREEN,
                text = "확인",
                onClick = {onBack()},
                modifier = modifier.height(50.dp),
            )
        }
    }
}
