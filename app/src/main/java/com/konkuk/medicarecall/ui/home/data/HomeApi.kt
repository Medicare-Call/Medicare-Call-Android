package com.konkuk.medicarecall.ui.home.data

import com.konkuk.medicarecall.ui.home.model.HomeResponseDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeApi {
    @GET("elders/{elderId}/home")
    suspend fun getHomeSummary(
        @Path("elderId") elderId: Int
    ): HomeResponseDto

    @POST("test-care-call")
    suspend fun startTestCall(
        @Body body: TestCallRequest
    ): Response<ResponseBody>
}

    data class TestCallRequest(
        val phoneNumber: String,
        val prompt: String
    )

    data class TestCallError(
        val status: Int,
        val error: String?,
        val message: String?
    )