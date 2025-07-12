package com.konkuk.medicarecall.ui.home.screen

import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.NameBar
import com.konkuk.medicarecall.ui.home.component.HomeGlucoseLevelContainer
import com.konkuk.medicarecall.ui.home.component.HomeMealContainer
import com.konkuk.medicarecall.ui.home.component.HomeMedicineContainer
import com.konkuk.medicarecall.ui.home.component.HomeSleepContainer
import com.konkuk.medicarecall.ui.home.component.HomeStateHealthContainer
import com.konkuk.medicarecall.ui.home.component.HomeStateMentalContainer
import com.konkuk.medicarecall.ui.homedetail.sleep.SleepUiState
import com.konkuk.medicarecall.ui.homedetail.statehealth.HealthUiState
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.main

@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onNavigateToMealDetail: () -> Unit,
    onNavigateToMedicineDetail: () -> Unit,
    onNavigateToSleepDetail: () -> Unit,
    onNavigateToStateHealthDetail: () -> Unit,
    onNavigateToStateMentalDetail: () -> Unit,
    onNavigateToGlucoseDetail: () -> Unit,
) {


    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {

        NameBar()

        Spacer(modifier = Modifier.height(19.dp))

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

                    //말풍선
                    Card(
                        modifier = Modifier
                            //텍스트에 따라 말풍선 늘리기
                            .wrapContentHeight()
                            .width(196.dp)
                            .zIndex(2f), //겹치는 도형 위로 올림
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(10.dp)
                    ) {

                        Text(
                            text = "아침·점심 복약과 식사는 문제 없으나, 저녁 약 복용이 늦어질 우려가 있어요.",
                            style = MediCareCallTheme.typography.R_16,
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
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = (-52.13).dp, y = -56.19.dp)
                        .zIndex(-1f)
                )
                //캐릭터
                Image(
                    painter = painterResource(id = R.drawable.char_medi),
                    contentDescription = null, //캐릭터 이미지
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
                        onClick = { onNavigateToMealDetail() }
                    )
                    Spacer(Modifier.height(12.dp))
                    HomeMedicineContainer(
                        onClick = { onNavigateToMedicineDetail() }
                    )
                    Spacer(Modifier.height(12.dp))
                    HomeSleepContainer(
                        sleeps = SleepUiState(
                            date = "2025-07-07",
                            totalSleepHours = 8,
                            totalSleepMinutes = 12,
                            bedTime = "오후 10:12",
                            wakeUpTime = "오전 06:00"
                        ),
                        onClick = { onNavigateToSleepDetail() }
                    )
                    Spacer(Modifier.height(12.dp))
                    HomeStateHealthContainer(
                        onClick = { onNavigateToStateHealthDetail() }
                    )
                    Spacer(Modifier.height(12.dp))
                    HomeStateMentalContainer(
                        onClick = { onNavigateToStateMentalDetail()}
                    )
                    Spacer(Modifier.height(12.dp))
                    HomeGlucoseLevelContainer(
                        glucoseLevel = 120,
                        onClick = { onNavigateToGlucoseDetail()}
                    )
                    Spacer(Modifier.height(12.dp))
                }
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


@Preview(showBackground = true, heightDp = 1200)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        navController = rememberNavController(),
        onNavigateToMealDetail = {},
        onNavigateToMedicineDetail = {},
        onNavigateToSleepDetail = {},
        onNavigateToStateHealthDetail = {},
        onNavigateToStateMentalDetail = {},
        onNavigateToGlucoseDetail = {},
    )
}