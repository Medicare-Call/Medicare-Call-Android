package com.konkuk.medicarecall.data

import android.content.Context
import android.util.Log
import com.konkuk.medicarecall.BuildConfig
import com.konkuk.medicarecall.data.api.NoticeService
import com.konkuk.medicarecall.data.api.VerificationService
import com.konkuk.medicarecall.data.repository.DataStoreRepository
import com.konkuk.medicarecall.data.repository.NoticeRepository
import com.konkuk.medicarecall.data.repository.VerificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
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
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository {
        return DataStoreRepository(context)
    }

    @Provides
    @Singleton
    fun provideVerificationService(retrofit: Retrofit): VerificationService {
        return retrofit.create(VerificationService::class.java)
    }

    @Provides
    @Singleton
    fun provideVerificationRepository(
        service: VerificationService
    ): VerificationRepository {
        return VerificationRepository(service)
    }

    @Provides
    @Singleton
    fun provideNoticeService(retrofit: Retrofit): NoticeService {
        return retrofit.create(NoticeService::class.java)
    }

    @Provides
    @Singleton
    fun provideNoticeRepository(
        service: NoticeService
    ): NoticeRepository {
        return NoticeRepository(service)
    }
}
