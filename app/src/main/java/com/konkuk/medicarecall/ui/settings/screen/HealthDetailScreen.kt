package com.konkuk.medicarecall.ui.settings.screen

import android.util.Log.d
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.data.dto.request.MedicationSchedule
import com.konkuk.medicarecall.data.dto.response.EldersHealthResponseDto
import com.konkuk.medicarecall.ui.component.CTAButton
import com.konkuk.medicarecall.ui.component.IllnessInfoItem
import com.konkuk.medicarecall.ui.component.MedInfoItem
import com.konkuk.medicarecall.ui.component.SpecialNoteItem
import com.konkuk.medicarecall.ui.homedetail.statehealth.model.HealthResponseDto
import com.konkuk.medicarecall.ui.model.CTAButtonType
import com.konkuk.medicarecall.ui.model.HealthIssueType
import com.konkuk.medicarecall.ui.settings.component.SettingsTopAppBar
import com.konkuk.medicarecall.ui.settings.viewmodel.DetailHealthViewModel
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun HealthDetailScreen(modifier: Modifier = Modifier,onBack : () -> Unit ={},
                       healthInfoResponseDto : EldersHealthResponseDto,
                       detailViewModel : DetailHealthViewModel = hiltViewModel()
) {
    val noteList = remember { mutableStateListOf<String>() }
    val scrollState = rememberScrollState()
    val diseaseList = remember { mutableStateListOf<String>() }
    val medications = remember { mutableStateListOf<MedicationSchedule>() }


    // dto가 바뀔 때마다 리스트 동기화
    LaunchedEffect(healthInfoResponseDto.elderId, healthInfoResponseDto.diseases, healthInfoResponseDto.medications, healthInfoResponseDto.specialNotes) {
        diseaseList.clear()
        diseaseList.addAll(healthInfoResponseDto.diseases)
        medications.clear()
        medications.addAll(healthInfoResponseDto.medications)
        noteList.clear()
        noteList.addAll(healthInfoResponseDto.specialNotes.map { it.displayName })

    }

    Column(modifier = modifier
        .fillMaxSize()
        .background(MediCareCallTheme.colors.bg)
        .statusBarsPadding()) {
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
            IllnessInfoItem(
                diseaseList = diseaseList,
                onAddDisease = { diseaseList.add(it) },
                onRemoveDisease = { diseaseList.remove(it) },
            )
            Spacer(modifier = modifier.height(20.dp))
            // 복약정보
            MedInfoItem(
                medications = medications,
                onAddMedication = { medications.add(it) },
                onRemoveMedication = { medications.remove(it) },
            )
            Spacer(modifier = modifier.height(20.dp))
            // 특이사항
            SpecialNoteItem(
                enumList = HealthIssueType.entries.map { it.displayName }.toList(),
                noteList = noteList,
                onAddNote = { noteList.add(it) },
                onRemoveNote = { noteList.remove(it) },
                placeHolder = "특이사항 선택하기",
                category = "특이사항",
                scrollState = scrollState,
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
                onClick = {
                    val noteEnums: List<HealthIssueType> = noteList.mapNotNull { display ->
                        HealthIssueType.entries.firstOrNull { it.displayName == display }
                    }
                    detailViewModel.updateElderHealth(
                        healthInfo = EldersHealthResponseDto(
                            elderId = healthInfoResponseDto.elderId,
                            name = healthInfoResponseDto.name,
                            diseases = diseaseList,
                            medications = medications,
                            specialNotes = noteEnums
                        )
                    )
                    onBack()
                          },
                modifier = modifier.height(50.dp),
            )
        }
    }
}
