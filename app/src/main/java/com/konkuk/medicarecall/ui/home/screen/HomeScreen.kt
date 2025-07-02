package com.konkuk.medicarecall.ui.home.screen

import android.system.Os.close
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.medicarecall.R
import com.konkuk.medicarecall.ui.NameBar
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme
import com.konkuk.medicarecall.ui.theme.main

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {


    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()

    ) {
        NameBar()

        Spacer(modifier = Modifier.height(19.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
        ) {

                //초록카드
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height ( 220.dp)
                        .background(main) // 배경색

                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 40.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        //말풍선
                        Card(
                            modifier = Modifier

                                .size(196.dp, 94.dp),
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


                        )
                    }
                    //캐릭터 그림자
                    Image(
                        painter = painterResource(id = R.drawable.char_medi_shadow),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = (-52.13).dp, y = -56.19.dp)
                    )
                    //캐릭터
                    Image(
                        painter = painterResource(id = R.drawable.char_medi),
                        contentDescription = null, //캐릭터이미지
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = (-7.75).dp, y = -55.12.dp)

                    )
                }


                //흰색카드
                Box(
                    Modifier
                        .fillMaxWidth()
                        .offset(y = 165.dp)
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(Color.White)
                ) {
                    // 카드 내용
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .wrapContentHeight()
                    ) {
                        Text("여기는 흰카드 내용!", color = Color.Black)
                        Spacer(Modifier.height(100.dp)) // 테스트로 내용 길이 늘려보기
                        Text("끝", color = Color.Black)
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


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}