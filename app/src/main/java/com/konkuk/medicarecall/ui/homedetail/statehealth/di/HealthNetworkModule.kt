package com.konkuk.medicarecall.ui.homedetail.statehealth.di

import com.konkuk.medicarecall.ui.homedetail.statehealth.data.HealthApi
import com.konkuk.medicarecall.ui.homedetail.statehealth.data.HealthRepository
import com.konkuk.medicarecall.ui.homedetail.statehealth.data.HealthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HealthNetworkModule {

    @Provides
    @Singleton
    fun provideHealthApi(retrofit: Retrofit): HealthApi {
        return retrofit.create(HealthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHealthRepository(healthApi: HealthApi): HealthRepository {
        return HealthRepositoryImpl(healthApi)
    }
}