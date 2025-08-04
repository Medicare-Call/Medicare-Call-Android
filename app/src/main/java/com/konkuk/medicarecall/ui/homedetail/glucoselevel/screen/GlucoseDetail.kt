package com.konkuk.medicarecall.ui.homedetail.glucoselevel.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.homedetail.TopAppBar
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.GlucoseViewModel
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.component.GlucoseGraph
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.component.GlucoseListItem
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.component.GlucoseStatusItem
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.component.GlucoseTimingButton
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseTiming
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseUiState
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GraphDataPoint
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GlucoseDetail(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: GlucoseViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val selectedIndex = remember { mutableIntStateOf(-1) }

    // 선택된 점(selectedIndex)을 자동으로 업데이트해주는 역할
    LaunchedEffect(uiState.graphDataPoints) {
        if (uiState.graphDataPoints.isNotEmpty()) {
            selectedIndex.intValue = uiState.graphDataPoints.lastIndex
        } else {
            selectedIndex.intValue = -1
            // 아무것도 선택되지 않았다는 의미로 -1을 저장 (오류 방지용)
        }
    }
    // 더미 데이터 요청
    LaunchedEffect(Unit) {
        viewModel.loadDummyWeek()
    }

    GlucoseDetailLayout(
        modifier = modifier,
        uiState = uiState,

        selectedTiming = uiState.selectedTiming,
        selectedIndex = selectedIndex.intValue,

        // '공복'/'식후' 버튼
        onTimingChange = { newTiming ->
            viewModel.updateTiming(newTiming)
        },
        // 그래프 점
        onPointClick = { newIndex -> selectedIndex.intValue = newIndex },
        navController = navController
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GlucoseDetailLayout(
    modifier: Modifier = Modifier,
    uiState: GlucoseUiState,
    selectedTiming: GlucoseTiming,
    selectedIndex: Int,
    onTimingChange: (GlucoseTiming) -> Unit,
    onPointClick: (Int) -> Unit,
    navController: NavHostController,
) {


    val isDataAvailable = uiState.graphDataPoints.isNotEmpty()

    Column(
        modifier = modifier
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
            modifier = Modifier .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            GlucoseTimingButton(
                modifier = Modifier.weight(1f),
                text = "공복",
                selected = selectedTiming == GlucoseTiming.BEFORE_MEAL,
                onClick = { onTimingChange(GlucoseTiming.BEFORE_MEAL) }
            )
            GlucoseTimingButton(
                modifier = Modifier.weight(1f),
                text = "식후",
                selected = selectedTiming == GlucoseTiming.AFTER_MEAL,
                onClick = { onTimingChange(GlucoseTiming.AFTER_MEAL) }
            )
        }


        if (isDataAvailable) {

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlucoseStatusItem()
                }

                Spacer(modifier = Modifier.height(20.dp))

                //혈당 그래프
                GlucoseGraph(
                    data = uiState.graphDataPoints,
                    selectedIndex = selectedIndex,
                    onPointClick = onPointClick
                )
                Spacer(modifier = Modifier.height(24.dp))

                //그래프 점 클릭시
                val selectedPoint = uiState.graphDataPoints.getOrNull(selectedIndex)
                if (selectedPoint != null) {
                    val timingLabel = if (selectedTiming == GlucoseTiming.BEFORE_MEAL) "아침 | 공복" else "저녁 | 식후"

                    // 날짜 표시
                    val dateText = selectedPoint.date.format(
                        DateTimeFormatter.ofPattern("M월 d일 (E)")
                    )
                    //혈당 상세 정보
                    GlucoseListItem(
                        date = dateText,
                        timingLabel = timingLabel,
                        value = selectedPoint.value.toInt()
                    )
                }
            }
        } else {
            // EMPTY VIEW
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 177.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_no_record),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "아직 기록이 없어요", style = MediCareCallTheme.typography.R_18, color = MediCareCallTheme.colors.gray6)
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, name = "데이터 있을 때")
@Composable
fun PreviewGlucoseDetail_DataAvailable() {
    // 프리뷰 용 더미 데이터
    val today = LocalDate.now()
    val sampleData = (0..6).map { i ->
        GraphDataPoint(
            date = today.minusDays(i.toLong()),
            value = (100..200).random().toFloat()
        )
    }.reversed()
    val dummyUiState = GlucoseUiState(graphDataPoints = sampleData)

    MediCareCallTheme {
        GlucoseDetailLayout(
            uiState = dummyUiState,
            selectedTiming = GlucoseTiming.BEFORE_MEAL,
            selectedIndex = sampleData.lastIndex,
            onTimingChange = {},
            onPointClick = {},
            navController = rememberNavController()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, name = "데이터 없을 때 (Empty View)")
@Composable
fun PreviewGlucoseDetail_Empty() {
    // Empty View 프리뷰
    val dummyUiState = GlucoseUiState(graphDataPoints = emptyList())

    MediCareCallTheme {
        GlucoseDetailLayout(
            uiState = dummyUiState,
            selectedTiming = GlucoseTiming.AFTER_MEAL,
            selectedIndex = -1,
            onTimingChange = {},
            onPointClick = {},
            navController = rememberNavController()
        )
    }
}