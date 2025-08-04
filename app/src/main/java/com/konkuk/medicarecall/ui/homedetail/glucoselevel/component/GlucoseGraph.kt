package com.konkuk.medicarecall.ui.homedetail.glucoselevel.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GraphDataPoint
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun GlucoseGraph(
    data: List<GraphDataPoint>,
    selectedIndex: Int,
    onPointClick: (Int) -> Unit,
) {
    val colors = MediCareCallTheme.colors
    val lineColor = colors.gray2
    val iconRadiusDp = 4.dp
    val graphDrawingHeightDp = 200.dp
    val labelStyle = MediCareCallTheme.typography.R_14
    val minGlucose = 60f
    val maxGlucose = 200f
    val scrollState = rememberScrollState()
    val sectionWidth: Dp = 60.dp
    val totalGraphWidth = sectionWidth * data.size // 데이터 개수에 따라 그래프의 전체 가로 길이 계산

    LaunchedEffect(data.size) {
        scrollState.scrollTo(scrollState.maxValue)
    }
    // [스크롤되는 그래프 영역 | 고정된 Y축 라벨]
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MediCareCallTheme.colors.white),
        verticalAlignment = Alignment.Top
    ) {

        Row(
            modifier = Modifier
                .weight(1f)
                .horizontalScroll(scrollState)
        ) {

            Column {

                //그래프
                Canvas(
                    modifier = Modifier
                        .width(totalGraphWidth)
                        .height(graphDrawingHeightDp)
                        // pointerInput으로 사용자의 터치(탭) 입력을 감지
                        .pointerInput(data) {
                            detectTapGestures { tapOffset ->
                                val sectionWidthPx = sectionWidth.toPx()
                                val clickedIndex = (tapOffset.x / sectionWidthPx).toInt()
                                if (clickedIndex in data.indices) {
                                    onPointClick(clickedIndex)
                                }
                            }
                        }
                ) {
                    // 혈당 값(value)을 Canvas의 Y좌표로 변환하는 함수
                    fun valueToY(v: Float): Float {
                        val ratio = ((v - minGlucose) / (maxGlucose - minGlucose)).coerceIn(0f, 1f)
                        return size.height * (1f - ratio)
                    }
                    val points = data.mapIndexed { idx, pointData ->
                        val x = (idx * sectionWidth.toPx()) + (sectionWidth.toPx() / 2)
                        val y = valueToY(pointData.value)
                        Offset(x, y)
                    }
                    // 200, 130, 90, 60 가로 가이드라인
                    listOf(200f, 130f, 90f, 60f).forEach { value ->
                        drawLine(
                            color = if (value == 200f || value == 60f) lineColor else lineColor.copy(alpha = 0.5f),
                            start = Offset(0f, valueToY(value)),
                            end = Offset(size.width, valueToY(value)),
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = if (value == 130f || value == 90f) PathEffect.dashPathEffect(floatArrayOf(10f, 10f)) else null
                        )
                    }
                    //선
                    for (i in 0 until points.size - 1) {
                        drawLine(color = lineColor, start = points[i], end = points[i + 1], strokeWidth = 1.5.dp.toPx())
                    }
                    //점
                    points.forEachIndexed { index, point ->
                        val value = data[index].value
                        val color = when {
                            value < 90f -> colors.active
                            value < 130f -> colors.main
                            else -> colors.negative
                        }
                        if (index == selectedIndex) {
                            drawCircle(color = color.copy(alpha = 0.2f), radius = iconRadiusDp.toPx() * 3, center = point)
                        }
                        drawCircle(color = color, radius = iconRadiusDp.toPx(), center = point)
                    }
                }
                // 날짜 라벨
                Row(modifier = Modifier.width(totalGraphWidth)) {
                    data.forEach { pointData ->
                        Text(
                            text = pointData.date.format(DateTimeFormatter.ofPattern("M.d")),
                            modifier = Modifier.width(sectionWidth),
                            style = labelStyle,
                            color = colors.gray4,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        // Y축 라벨 (오른쪽에 고정)
        Canvas(
            modifier = Modifier
                .width(40.dp)
                .height(graphDrawingHeightDp)
        ) {
            // 그래프와 동일한 Y좌표 계산 방식을 사용
            fun valueToY(v: Float): Float {
                val ratio = ((v - minGlucose) / (maxGlucose - minGlucose)).coerceIn(0f, 1f)
                return size.height * (1f - ratio)
            }
            // Paint를 사용하여 Canvas에 직접 글씨를 씀
            val paint = android.graphics.Paint().apply {
                color = android.graphics.Color.GRAY
                textSize = labelStyle.fontSize.toPx()
                textAlign = android.graphics.Paint.Align.LEFT
            }
            val labelX = 8.dp.toPx()
            drawContext.canvas.nativeCanvas.drawText("200", labelX, valueToY(200f) + 5.dp.toPx(), paint)
            drawContext.canvas.nativeCanvas.drawText("130", labelX, valueToY(130f) + 5.dp.toPx(), paint)
            drawContext.canvas.nativeCanvas.drawText("90", labelX, valueToY(90f) + 5.dp.toPx(), paint)
            drawContext.canvas.nativeCanvas.drawText("60", labelX, valueToY(60f) + 5.dp.toPx(), paint)
        }
    }
}


@Preview(showBackground = true, widthDp = 360)
@Composable
fun PreviewGlucoseGraph() {
    // 14일치 가상 데이터
    val sampleData = (0..13).map {
        GraphDataPoint(
            date = LocalDate.now().minusDays(it.toLong()),
            value = (70..210).random().toFloat()
        )
    }.reversed()
    MediCareCallTheme {
        GlucoseGraph(
            data = sampleData,
            selectedIndex = sampleData.lastIndex,
            onPointClick = {}
        )
    }
}