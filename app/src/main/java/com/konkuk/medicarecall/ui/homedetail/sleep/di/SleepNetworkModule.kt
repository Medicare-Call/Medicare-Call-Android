package com.konkuk.medicarecall.ui.homedetail.sleep.di

import com.konkuk.medicarecall.ui.homedetail.sleep.data.SleepApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SleepNetworkModule {

    @Provides
    @Singleton
    fun provideSleepApi(retrofit: Retrofit): SleepApi {
        return retrofit.create(SleepApi::class.java)
    }
}