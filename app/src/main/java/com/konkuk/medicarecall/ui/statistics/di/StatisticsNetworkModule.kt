package com.konkuk.medicarecall.ui.statistics.di

import com.konkuk.medicarecall.ui.statistics.data.StatisticsApi
import com.konkuk.medicarecall.ui.statistics.data.StatisticsRepository
import com.konkuk.medicarecall.ui.statistics.data.StatisticsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StatisticsNetworkModule {


    @Provides
    @Singleton
    fun provideStatisticsApi(retrofit: Retrofit): StatisticsApi {
        return retrofit.create(StatisticsApi::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStatisticsRepository(
        statisticsRepositoryImpl: StatisticsRepositoryImpl
    ): StatisticsRepository
}

