package com.konkuk.medicarecall.data

import android.util.Log
import com.konkuk.medicarecall.BuildConfig
import com.konkuk.medicarecall.data.api.NoticeService
import com.konkuk.medicarecall.data.repository.NoticeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        Log.d("Retrofit", "Base URL: ${BuildConfig.BASE_URL}")
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // BuildConfig에서 baseUrl 가져오는 경우
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(OkHttpClient.Builder().build())
            .build()
    }

    @Provides
    fun provideNoticeService(retrofit: Retrofit): NoticeService {
        return retrofit.create(NoticeService::class.java)
    }

    @Provides
    fun provideNoticeRepository(
        service: NoticeService
    ): NoticeRepository {
        return NoticeRepository(service)
    }
}
