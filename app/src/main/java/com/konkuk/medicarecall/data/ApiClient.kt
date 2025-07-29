package com.konkuk.medicarecall.data

import com.konkuk.medicarecall.BuildConfig
import com.konkuk.medicarecall.data.api.VerificationService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object ApiClient {
    private val client = OkHttpClient.Builder().build()

    val contentType = "application/json".toMediaType()
    val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()
    val verificationService: VerificationService = retrofit.create(VerificationService::class.java)
}