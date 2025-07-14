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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.konkuk.medicarecall.ui.homedetail.TopAppBar
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.GlucoseGraphState
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.GlucoseStatusUtil
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.GlucoseTiming
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.GlucoseUiState
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.component.GlucoseGraph
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.component.GlucoseStatusChip
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.component.GlucoseStatusItem
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.component.GlucoseTimingButton
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GlucoseDetail(

    navController: NavController,
    glucose: GlucoseUiState,
    graph: GlucoseGraphState


) {
    // ✅ 현재 선택된 공복/식후 상태를 remember로 관리
    val (selectedTiming, setSelectedTiming) = remember {
        mutableStateOf(glucose.selectedTiming)
    }

    // ✅ 선택된 상태가 공복인지 여부
    val isBeforeMeal = selectedTiming == GlucoseTiming.BEFORE_MEAL

    // ✅ 선택된 상태에 따라 오늘 하루 평균 혈당 값 가져오기
    val averageValue = if (isBeforeMeal) {
        glucose.dailyAverageBeforeMeal
    } else {
        glucose.dailyAverageAfterMeal
    }

    // ✅ 선택된 상태에 따라 최근 혈당 값 가져오기
    val recentValue = if (isBeforeMeal) {
        glucose.recentBeforeMeal
    } else {
        glucose.recentAfterMeal
    }

    // ✅ 선택된 상태에 따라 혈당 상태(낮음/정상/높음) 계산하기
    val averageStatus = GlucoseStatusUtil.getStatus(averageValue)
    val recentStatus = GlucoseStatusUtil.getStatus(recentValue)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MediCareCallTheme.colors.white)

    ) {
        TopAppBar(
            title = "혈당"
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
                selected = selectedTiming == GlucoseTiming.BEFORE_MEAL,
                onClick = { setSelectedTiming(GlucoseTiming.BEFORE_MEAL) }
            )

            GlucoseTimingButton(
                text = "식후",
                selected = selectedTiming == GlucoseTiming.AFTER_MEAL,
                onClick = { setSelectedTiming(GlucoseTiming.AFTER_MEAL) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {

            if (glucose.isRecorded) {
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
                        GlucoseGraph(

                            glucose = GlucoseUiState(
                                selectedTiming = GlucoseTiming.BEFORE_MEAL,
                                dailyAverageBeforeMeal = 120,
                                dailyAverageAfterMeal = 120,
                                recentBeforeMeal = 127,
                                recentAfterMeal = 127,
                                glucoseLevelStatusBeforeMeal = "정상",
                                glucoseLevelStatusAfterMeal = "정상",
                                isRecorded = true
                            ),
                            graph = GlucoseGraphState(
                                beforeMealGraph = listOf(80, 110, 129, 180, 131, 125, 115),
                                afterMealGraph = listOf(80, 110, 129, 180, 131, 125, 115),
                                weekLabels = listOf("일", "월", "화", "수", "목", "금", "토")
                            ),
                            selectedTiming = GlucoseTiming.BEFORE_MEAL
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))


                    val weekLabels = listOf("5.15", "5.16", "5.17", "5.18", "5.19", "5.20", "5.21")



                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        weekLabels.forEach {
                            Text(
                                text = it,
                                style = MediCareCallTheme.typography.R_14,
                                color = MediCareCallTheme.colors.gray4,
                                modifier = Modifier.weight(1f, fill = false)
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
                        .wrapContentHeight()
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
                            GlucoseGraph(

                                glucose = GlucoseUiState(
                                    selectedTiming = GlucoseTiming.BEFORE_MEAL,
                                    dailyAverageBeforeMeal = 120,
                                    dailyAverageAfterMeal = 120,
                                    recentBeforeMeal = 127,
                                    recentAfterMeal = 127,
                                    glucoseLevelStatusBeforeMeal = "정상",
                                    glucoseLevelStatusAfterMeal = "정상",
                                    isRecorded = true
                                ),
                                graph = GlucoseGraphState(
                                    beforeMealGraph = listOf(80, 110, 129, 180, 131, 125, 115),
                                    afterMealGraph = listOf(80, 110, 129, 180, 131, 125, 115),
                                    weekLabels = listOf("일", "월", "화", "수", "목", "금", "토")
                                ),
                                selectedTiming = GlucoseTiming.BEFORE_MEAL
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))


                        val weekLabels = listOf("5.15", "5.16", "5.17", "5.18", "5.19", "5.20", "5.21")



                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween

                        ) {
                            weekLabels.forEach {
                                Text(
                                    text = it,
                                    style = MediCareCallTheme.typography.R_14,
                                    color = MediCareCallTheme.colors.gray4,
                                    modifier = Modifier.weight(1f, fill = false)
                                )
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
        navController = rememberNavController(),

        glucose = GlucoseUiState(
            selectedTiming = GlucoseTiming.BEFORE_MEAL,   // 공복 기본 선택

            dailyAverageBeforeMeal = 120,   // 오늘 하루 평균 공복 혈당
            dailyAverageAfterMeal = 120,    // 오늘 하루 평균 식후 혈당
            recentBeforeMeal = 127,         // 어제 마지막 공복 혈당
            recentAfterMeal = 127,          // 어제 마지막 식후 혈당
            glucoseLevelStatusBeforeMeal = "정상",   // 공복 상태
            glucoseLevelStatusAfterMeal = "정상",     // 식후 상태
            isRecorded = true           // 기록 여부
        ),

        graph = GlucoseGraphState(

            beforeMealGraph = listOf(60, 75, 90, 110, 200, 130, 100),  // 공복 주간 데이터
            afterMealGraph = listOf(60, 75, 90, 110, 200, 130, 100),   // 식후 주간 데이터
            weekLabels = listOf("일", "월", "화", "수", "목", "금", "토")

        )


    )


}



@Preview(showBackground = true)
@Composable
fun PreviewGlucoseDetailEmpty() {
    GlucoseDetail(
        navController = rememberNavController(),
        glucose = GlucoseUiState(
            selectedTiming = GlucoseTiming.BEFORE_MEAL,
            dailyAverageBeforeMeal = 0,
            dailyAverageAfterMeal = 0,
            recentBeforeMeal = 0,
            recentAfterMeal = 0,
            glucoseLevelStatusBeforeMeal = "",
            glucoseLevelStatusAfterMeal = "",
            isRecorded = false
        ),
        graph = GlucoseGraphState(
            beforeMealGraph = emptyList(),
            afterMealGraph = emptyList(),
            weekLabels = emptyList()
        )
    )
}