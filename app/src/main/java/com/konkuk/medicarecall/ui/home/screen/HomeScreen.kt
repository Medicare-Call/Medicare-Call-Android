package com.konkuk.medicarecall.ui.home.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.component.NameBar
import com.konkuk.medicarecall.ui.component.NameDropdown
import com.konkuk.medicarecall.ui.home.HomeViewModel
import com.konkuk.medicarecall.ui.home.component.CareCallFloatingButton
import com.konkuk.medicarecall.ui.home.component.CareCallSnackBar
import com.konkuk.medicarecall.ui.home.component.HomeGlucoseLevelContainer
import com.konkuk.medicarecall.ui.home.component.HomeMealContainer
import com.konkuk.medicarecall.ui.home.component.HomeMedicineContainer
import com.konkuk.medicarecall.ui.home.component.HomeSleepContainer
import com.konkuk.medicarecall.ui.home.component.HomeStateHealthContainer
import com.konkuk.medicarecall.ui.home.component.HomeStateMentalContainer
import com.konkuk.medicarecall.ui.home.model.HomeResponseDto
import com.konkuk.medicarecall.ui.home.model.HomeUiState
import com.konkuk.medicarecall.ui.home.model.MedicineUiState
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.main
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigateToMealDetail: () -> Unit,
    onNavigateToMedicineDetail: () -> Unit,
    onNavigateToSleepDetail: () -> Unit,
    onNavigateToStateHealthDetail: () -> Unit,
    onNavigateToStateMentalDetail: () -> Unit,
    onNavigateToGlucoseDetail: () -> Unit,
) {
    val homeUiState by homeViewModel.homeUiState.collectAsState()
    val elderNameList by homeViewModel.elderNameList.collectAsState()
    var dropdownOpened by remember { mutableStateOf(false) }


    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    HomeScreenLayout(
        modifier = modifier,
        navController = navController,
        homeUiState = homeUiState,
        elderNameList = elderNameList,
        dropdownOpened = dropdownOpened,
        onDropdownClick = { dropdownOpened = true },
        onDropdownDismiss = { dropdownOpened = false },
        onDropdownItemSelected = { selectedName ->
            homeViewModel.selectElder(selectedName)
            dropdownOpened = false
        },
        onNavigateToMealDetail = onNavigateToMealDetail,
        onNavigateToMedicineDetail = onNavigateToMedicineDetail,
        onNavigateToSleepDetail = onNavigateToSleepDetail,
        onNavigateToStateHealthDetail = onNavigateToStateHealthDetail,
        onNavigateToStateMentalDetail = onNavigateToStateMentalDetail,
        onNavigateToGlucoseDetail = onNavigateToGlucoseDetail,
                snackbarHostState = snackbarHostState,
        onFabClick = {
            scope.launch {
                snackbarHostState.showSnackbar("케어콜이 곧 연결됩니다. 잠시만 기다려 주세요.")
            }
        }
    )
}


@Composable
fun HomeScreenLayout(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    homeUiState: HomeUiState,
    elderNameList: List<String>,
    dropdownOpened: Boolean,
    onDropdownClick: () -> Unit,
    onDropdownDismiss: () -> Unit,
    onDropdownItemSelected: (String) -> Unit,
    onNavigateToMealDetail: () -> Unit,
    onNavigateToMedicineDetail: () -> Unit,
    onNavigateToSleepDetail: () -> Unit,
    onNavigateToStateHealthDetail: () -> Unit,
    onNavigateToStateMentalDetail: () -> Unit,
    onNavigateToGlucoseDetail: () -> Unit,
    snackbarHostState: SnackbarHostState,
    onFabClick: () -> Unit
) {

    val selectedElderName = remember(homeUiState.elderName, elderNameList) {

        homeUiState.elderName.ifEmpty {
            elderNameList.firstOrNull() ?: "어르신 선택"
        }
    }
    Scaffold(

        contentWindowInsets = WindowInsets(0),
        floatingActionButton = {
            CareCallFloatingButton(
                modifier = modifier,
                onClick = onFabClick
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { innerPadding ->
        Box(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White).padding(innerPadding)
            ) {
                NameBar(
                    name = selectedElderName,
                    modifier = Modifier.statusBarsPadding(),
                    navController = navController,
                    onDropdownClick = onDropdownClick
                )

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                ) {


                    //1. 초록 카드
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .heightIn(min = 220.dp)
                            .background(main)


                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 40.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {


                            //val balloonText = "아침·점심 복약과 식사는 문제 없으나, 저녁 약 복용이 늦어질 우려가 있어요."
                            val balloonText = homeUiState.balloonMessage
                            val trimmedText = balloonText.take(45) // 글자 수 제한


                            //말풍선
                            Card(
                                modifier = Modifier
                                    //텍스트에 따라 말풍선 늘리기
                                    .width(196.dp)
                                    .heightIn(min = 94.dp)
                                    .zIndex(2f), //겹치는 도형 위로 올림
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                shape = RoundedCornerShape(10.dp)
                            ) {

                                Text(
                                    text = trimmedText,
                                    style = MediCareCallTheme.typography.R_16,
                                    color = MediCareCallTheme.colors.gray8,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .background(Color.White)
                                )
                            }
                            // 꼬리
                            Box(
                                modifier = Modifier
                                    .size(width = 14.dp, height = 13.dp)
                                    .offset(x = -2.dp, y = 20.dp)
                                    .clip(SpeechTail)
                                    .background(Color.White)
                                    .zIndex(2f)


                            )
                        }
                        //캐릭터 그림자
                        Image(
                            painter = painterResource(id = R.drawable.char_medi_shadow),
                            contentDescription = "캐릭터 그림자",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .offset(x = (-52.13).dp, y = -56.19.dp)
                                .zIndex(-1f)
                        )
                        //캐릭터
                        Image(
                            painter = painterResource(id = R.drawable.char_medi),
                            contentDescription = "캐릭터 이미지",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .offset(x = (-7.75).dp, y = -55.12.dp)
                                .zIndex(3f)
                        )
                    }


                    //2. 흰색 카드
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .offset(y = -40.dp)
                            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                            .background(Color.White)


                    ) {
                        // 카드 내용
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(20.dp)
                        ) {
                            Spacer(Modifier.height(12.dp))
                            HomeMealContainer(
                                breakfastEaten = homeUiState.breakfastEaten,
                                lunchEaten = homeUiState.lunchEaten,
                                dinnerEaten = homeUiState.dinnerEaten,
                                onClick = { onNavigateToMealDetail() }
                            )
                            Spacer(Modifier.height(12.dp))
                            HomeMedicineContainer(
                                medicines = homeUiState.medicines,
                                onClick = { onNavigateToMedicineDetail() }
                            )
                            Spacer(Modifier.height(12.dp))
                            val sleepData = homeUiState.sleep
                            HomeSleepContainer(
                                totalSleepHours = sleepData.meanHours,
                                totalSleepMinutes = sleepData.meanMinutes,
                                isRecorded = sleepData.meanHours > 0 || sleepData.meanMinutes > 0,
                                onClick = { onNavigateToSleepDetail() }
                            )
                            Spacer(Modifier.height(12.dp))
                            HomeStateHealthContainer(
                                healthStatus = homeUiState.healthStatus,
                                onClick = { onNavigateToStateHealthDetail() }
                            )
                            Spacer(Modifier.height(12.dp))
                            HomeStateMentalContainer(
                                mentalStatus = homeUiState.mentalStatus,
                                onClick = { onNavigateToStateMentalDetail() }
                            )
                            Spacer(Modifier.height(12.dp))
                            HomeGlucoseLevelContainer(
                                glucoseLevelAverageToday = homeUiState.glucoseLevelAverageToday,
                                onClick = { onNavigateToGlucoseDetail() }
                            )
                            Spacer(Modifier.height(12.dp))
                        }
                    }
                }

            }

            if (dropdownOpened) {
                NameDropdown(
                    items = elderNameList,
                    selectedName = selectedElderName,
                    onDismiss = onDropdownDismiss,
                    onItemSelected = onDropdownItemSelected
                )
            }
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = -(60.dp))
            ) { data ->
                CareCallSnackBar(snackBarData = data)
            }
        }
    }
}

val SpeechTail = GenericShape { size, _ ->
    // 90도 회전된 삼각형
    moveTo(0f, 0f) // 왼쪽 위
    lineTo(size.width, size.height / 2) // 오른쪽 중간
    lineTo(0f, size.height) // 왼쪽 아래
    close()
}

@Preview(showBackground = true, heightDp = 1500)
@Composable
fun PreviewHomeScreen() {

    val previewUiState = HomeUiState(
        elderName = "김옥자",
        balloonMessage = "아침·점심 복약과 식사는 문제 없으나, 저녁 약 복용이 늦어질 우려가 있어요.",
        breakfastEaten = true,
        lunchEaten = true,
        dinnerEaten = false,
        medicines = listOf(
            MedicineUiState("혈압약", 2, 3, "저녁"),
            MedicineUiState("당뇨약", 1, 2, "저녁")
        ),
        sleep = HomeResponseDto.SleepDto(meanHours = 8, meanMinutes = 15),
        healthStatus = "좋음",
        mentalStatus = "좋음",
        glucoseLevelAverageToday = 120
    )

    val previewNameList = listOf("김옥자", "박막례", "최이순")

    MediCareCallTheme {
        HomeScreenLayout(
            navController = rememberNavController(),
            homeUiState = previewUiState,
            elderNameList = previewNameList,
            dropdownOpened = false,
            onDropdownClick = {},
            onDropdownDismiss = {},
            onDropdownItemSelected = {},
            onNavigateToMealDetail = {},
            onNavigateToMedicineDetail = {},
            onNavigateToSleepDetail = {},
            onNavigateToStateHealthDetail = {},
            onNavigateToStateMentalDetail = {},
            onNavigateToGlucoseDetail = {},
            snackbarHostState = SnackbarHostState(),
            onFabClick = {}
        )
    }
}