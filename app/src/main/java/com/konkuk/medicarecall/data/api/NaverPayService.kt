package com.konkuk.medicarecall.data.api

import com.konkuk.medicarecall.data.dto.request.PayCaptureRequestDto
import com.konkuk.medicarecall.data.dto.request.PayRequestDto
import com.konkuk.medicarecall.data.dto.response.PayCaptureResponseDto
import com.konkuk.medicarecall.data.dto.response.PayResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NaverPayService {
    @POST("naverpay/reserve")
    suspend fun postPayData(
        @Body payRequestDto: PayRequestDto
    ): Response<PayResponseDto>
    // 결제창 호출은 아래와 같이 할 수 있습니다.
    //https://m.pay.naver.com/payments/{reserveId}

    @POST("naverpay/capture")
    suspend fun postPayCaptureData(
        @Body payCaptureRequestDto: PayCaptureRequestDto
    ): Response<PayCaptureResponseDto>
}