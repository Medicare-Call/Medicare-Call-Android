package com.konkuk.medicarecall.ui.homedetail.glucoselevel.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.medicarecall.ui.homedetail.TopAppBar
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.GlucoseViewModel
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseTiming
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.component.GlucoseGraph
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.component.GlucoseStatusChip
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.component.GlucoseStatusItem
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.component.GlucoseTimingButton
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GlucoseDetail(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: GlucoseViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { uiState.weeklyData.size }
    )


    val currentWeekState = uiState.weeklyData.getOrNull(pagerState.currentPage)
    val glucose = currentWeekState?.let { viewModel.dailyData(it) }
    val selectedTiming = remember { mutableStateOf(glucose?.selectedTiming ?: GlucoseTiming.BEFORE_MEAL) }


    val isBeforeMeal = selectedTiming.value == GlucoseTiming.BEFORE_MEAL
    val averageValue = if (isBeforeMeal) glucose?.dailyAverageBeforeMeal ?: 0 else glucose?.dailyAverageAfterMeal ?: 0
    val recentValue = if (isBeforeMeal) glucose?.recentBeforeMeal ?: 0 else glucose?.recentAfterMeal ?: 0
    LaunchedEffect(Unit) {
        viewModel.loadDummyWeek()
        // 테스트용 ID
        // viewModel.loadNextWeek(guardianId = 1)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MediCareCallTheme.colors.white)

    ) {
        TopAppBar(
            title = "혈당",
            navController = navController
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GlucoseTimingButton(
                text = "공복",
                selected = selectedTiming.value == GlucoseTiming.BEFORE_MEAL,
                onClick = { selectedTiming.value = GlucoseTiming.BEFORE_MEAL }
            )

            GlucoseTimingButton(
                text = "식후",
                selected = selectedTiming.value == GlucoseTiming.AFTER_MEAL,
                onClick = { selectedTiming.value = GlucoseTiming.AFTER_MEAL }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {

            if (glucose != null && glucose.isRecorded == true) {
                Column(
                    modifier = Modifier
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GlucoseStatusItem()
                    }

                    Spacer(modifier = Modifier.height(10.dp))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        HorizontalPager(
                            state = pagerState,
                        ) { pageIndex ->

                            GlucoseGraph(
                                graph = uiState.weeklyData[pageIndex],
                                selectedTiming = selectedTiming.value
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(40.dp))


                    //하루 평균 혈당 + 최근(어제) 혈당
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally

                        ) {
                            Text(
                                text = "하루 평균 혈당",
                                style = MediCareCallTheme.typography.R_16,
                                color = MediCareCallTheme.colors.gray6,

                                )
                            Spacer(modifier = Modifier.height(4.dp))

                            GlucoseStatusChip(value = averageValue)

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = "$averageValue",
                                    style = MediCareCallTheme.typography.SB_22,
                                    color = MediCareCallTheme.colors.gray8,
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "mg/dL",
                                    style = MediCareCallTheme.typography.R_16,
                                    color = MediCareCallTheme.colors.gray8,

                                    )
                            }

                        }
                        Spacer(modifier = Modifier.width(40.dp))

                        // 세로 구분선
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(80.dp)
                                .background(MediCareCallTheme.colors.gray2)
                        )


                        /*VerticalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = MediCareCallTheme.colors.gray2,
                            thickness = 1.dp
                        )*/

                        Spacer(modifier = Modifier.width(40.dp))

                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "최근 혈당",
                                style = MediCareCallTheme.typography.R_16,
                                color = MediCareCallTheme.colors.gray6,

                                )
                            Spacer(modifier = Modifier.height(4.dp))

                            GlucoseStatusChip(value = recentValue)


                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = "$recentValue",
                                    style = MediCareCallTheme.typography.SB_22,
                                    color = MediCareCallTheme.colors.gray8,
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "mg/dL",
                                    style = MediCareCallTheme.typography.R_16,
                                    color = MediCareCallTheme.colors.gray8,

                                    )
                            }

                        }
                    }
                }
            } else {
                // ✅ 기록 없을 때 : Empty View
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    // 1️⃣ 원래 그래프 그대로 그리기
                    Column(
                        modifier = Modifier
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GlucoseStatusItem()
                        }

                        Spacer(modifier = Modifier.height(10.dp))


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier.height(200.dp)
                            ) { pageIndex ->

                            }
                        }
                    } // 실제로는 원본 그대로 or 배경 컨텐츠

                    // 2️⃣ 흐림 + 반투명 배경 덮기
                    Box(
                        Modifier
                            .matchParentSize()
                            .background(Color.White.copy(alpha = 0.5f))
                            .blur(3.dp)
                    )

                        Text(
                            text = "기록이 없습니다.",
                            modifier = Modifier.align(Alignment.Center),
                            style = MediCareCallTheme.typography.M_20,
                            color = MediCareCallTheme.colors.black,

                            )

                }


                Spacer(modifier = Modifier.height(40.dp))


                //하루 평균 혈당 + 최근(어제) 혈당
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text(
                            text = "하루 평균 혈당",
                            style = MediCareCallTheme.typography.R_16,
                            color = MediCareCallTheme.colors.gray6,

                            )
                        Spacer(modifier = Modifier.height(4.dp))


                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = "mg/dL",
                                style = MediCareCallTheme.typography.R_16,
                                color = MediCareCallTheme.colors.gray8,

                                )
                        }

                    }
                    Spacer(modifier = Modifier.width(40.dp))

                    // 세로 구분선

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(80.dp)
                            .background(MediCareCallTheme.colors.gray2)
                    )

                    Spacer(modifier = Modifier.width(40.dp))

                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "최근 혈당",
                            style = MediCareCallTheme.typography.R_16,
                            color = MediCareCallTheme.colors.gray6,

                            )
                        Spacer(modifier = Modifier.height(4.dp))



                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.Bottom
                        ) {
                             Text(
                                text = "mg/dL",
                                style = MediCareCallTheme.typography.R_16,
                                color = MediCareCallTheme.colors.gray8,
                                )
                        }

                    }
                }

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewGlucoseDetail() {
    GlucoseDetail(
        navController = rememberNavController()
    )


}



@Preview(showBackground = true)
@Composable
fun PreviewGlucoseDetailEmpty() {
    GlucoseDetail(
        navController = rememberNavController()
    )
}