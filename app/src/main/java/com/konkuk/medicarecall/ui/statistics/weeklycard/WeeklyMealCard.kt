package com.konkuk.medicarecall.ui.statistics.weeklycard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.statistics.WeeklyMealUiState
import com.konkuk.medicarecall.ui.statistics.WeeklySummaryUtil
import com.konkuk.medicarecall.ui.theme.LocalMediCareCallShadowProvider
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.figmaShadow

@Composable
fun WeeklyMealCard(
    modifier: Modifier = Modifier,
    meal: List<WeeklyMealUiState>
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .figmaShadow(
                group = LocalMediCareCallShadowProvider.current.shadow03,
                cornerRadius = 14.dp
            ),

        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp)

    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)

        ) {

            //1) Title: 식사 통계

                Text(
                    "식사통계",
                    style = MediCareCallTheme.typography.R_15,
                    color = MediCareCallTheme.colors.gray5,
                )


            Spacer(modifier = Modifier.height(8.dp))


            //2) 아침+점심+저녁
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                meal.forEach { weeklyMeal ->
                    val iconColor =
                        WeeklySummaryUtil.getMealIconColor(weeklyMeal, MediCareCallTheme.colors)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = weeklyMeal.mealType,
                            style = MediCareCallTheme.typography.R_15,
                            color = MediCareCallTheme.colors.gray4
                        )
                        Spacer(modifier = Modifier.width(10.dp))


                            Icon(
                                modifier = Modifier
                                    .size(16.dp),
                                painter = painterResource(id = R.drawable.ic_filledbowl),
                                contentDescription = null,
                                tint = iconColor
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "${weeklyMeal.eatenCount}/${weeklyMeal.totalCount}",
                                style = MediCareCallTheme.typography.R_14,
                                color = MediCareCallTheme.colors.gray6
                            )

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewWeeklyMealCard() {
        WeeklyMealCard(
            modifier = Modifier
                .size(150.dp,140.dp),
            meal = listOf(
                WeeklyMealUiState("아침", 7, 7),
                WeeklyMealUiState("점심", 5, 7),
                WeeklyMealUiState("저녁", 0, 7)
            )
        )
}