package com.konkuk.medicarecall.ui.homedetail.glucoselevel.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseWeeklyState
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseTiming
import com.konkuk.medicarecall.ui.theme.LocalMediCareCallColorsProvider
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

@Composable
fun GlucoseGraph(

    graph: GlucoseWeeklyState,
    selectedTiming: GlucoseTiming,
    weekLabels: List<String> = graph.weekLabels
) {

    val colors = LocalMediCareCallColorsProvider.current
    val density = LocalDensity.current
    val lineColor = colors.gray2

    val graphData = if (selectedTiming == GlucoseTiming.BEFORE_MEAL) {
        graph.beforeMealGraph.map { it.toFloat() }
    } else {
        graph.afterMealGraph.map { it.toFloat() }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MediCareCallTheme.colors.white)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MediCareCallTheme.colors.white)
        ) {

            // ✅ 1) 배경 가이드라인
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
            ) {
                // 200
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Divider(
                        modifier = Modifier.weight(1f),
                        color = lineColor,
                        thickness = 1.dp
                    )
                    Text(
                        text = "200",
                        style = MediCareCallTheme.typography.R_14,
                        color = lineColor
                    )
                }
                Spacer(modifier = Modifier.height(98.dp))

                // 130
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Canvas(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                    ) {
                        drawLine(
                            color = lineColor,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                        )
                    }
                    Text(
                        "130",
                        style = MediCareCallTheme.typography.R_14,
                        color = lineColor
                    )
                }
                Spacer(modifier = Modifier.height(66.dp))

                // 90
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Canvas(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                    ) {
                        drawLine(
                            color = lineColor,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                        )
                    }
                    Spacer(modifier = Modifier.width(9.dp))

                    Text("90", style = MediCareCallTheme.typography.R_14, color = lineColor)
                }
                Spacer(Modifier.height(11.dp))

                // 60
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = lineColor,
                        thickness = 1.dp
                    )
                    Spacer(modifier = Modifier.width(9.dp))
                    Text(
                        "60",
                        style = MediCareCallTheme.typography.R_14,
                        color = lineColor
                    )

                }
            }


            // ✅ 2) 그래프

            val spacingDp = 44.dp         // 점 간 간격 고정
            val iconSizeDp = 8.dp

            val graphHeightDp = 242.dp //고점과 저점의 높이 차이

            val graphHeightPx = with(density) { graphHeightDp.toPx() }
            val iconSizePx = with(density) { iconSizeDp.toPx() }
            val halfIcon = iconSizePx / 2f

            val spacingPx = with(density) { spacingDp.toPx() }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 4.dp, bottom = 4.dp, end = 36.dp)
            ) {
                //TODO: 그래프 스와이프 기능 추가
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(graphHeightDp)
                ) {
                    val canvasWidth = constraints.maxWidth.toFloat()
                    val canvasHeight = with(density) { this@BoxWithConstraints.maxHeight.toPx() }

                    val spacing = canvasWidth / (graphData.size - 1)

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val points = graphData.mapIndexed { index, v ->
                            val x = spacing * index
                            val y = valueToY(v, canvasHeight)
                            Offset(x, y)
                        }

                        // 선
                        for (i in 0 until points.size - 1) {
                            drawLine(
                                color = lineColor,
                                start = points[i],
                                end = points[i + 1],
                                strokeWidth = 1.dp.toPx()
                            )
                        }

                        // 점
                        points.forEachIndexed { index, point ->
                            val value = graphData[index]
                            val color = when {
                                value < 90f -> colors.active
                                value < 130f -> colors.main
                                else -> colors.negative
                            }

                            drawCircle(
                                color = color,
                                radius = 4.dp.toPx(),
                                center = point
                            )
                        }
                    }
                }
            }

        }
            Spacer(modifier = Modifier.height(10.dp))


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
        }
    }




// ✅ height == 그래프 Box 높이
private fun valueToY(v: Float, height: Float): Float {
    val ratio = (v - 60f) / (200f - 60f)
    return height * (1f - ratio) // 값이 클수록 위로
}


@Preview(showBackground = true)
@Composable
fun PreviewGlucoseGraph() {
    GlucoseGraph(
        graph = GlucoseWeeklyState(
            beforeMealGraph = listOf(80, 110, 129, 180, 131, 125, 115),
            afterMealGraph = listOf(80, 110, 129, 180, 131, 125, 115),
            weekLabels = listOf("7.21", "7.22", "7.23", "7.24", "7.25", "7.26", "7.27")
        ),
        selectedTiming = GlucoseTiming.BEFORE_MEAL
    )
}
