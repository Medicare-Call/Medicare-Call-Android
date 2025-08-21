package com.konkuk.medicarecall.ui.homedetail.sleep.di

import com.konkuk.medicarecall.ui.homedetail.sleep.data.SleepApi
import com.konkuk.medicarecall.ui.homedetail.sleep.data.SleepRepository
import com.konkuk.medicarecall.ui.homedetail.sleep.data.SleepRepositoryImpl
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

    @Provides
    @Singleton
    fun provideSleepRepository(sleepApi: SleepApi): SleepRepository {
        return SleepRepositoryImpl(sleepApi)
    }
}