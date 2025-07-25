package com.konkuk.medicarecall.ui.homedetail.statehealth.di

import com.konkuk.medicarecall.ui.homedetail.statehealth.data.HealthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HealthNetworkModule {

    @Provides
    @Singleton
    fun provideHealthApi(): HealthApi {
        return Retrofit.Builder()
            .baseUrl("https://medicare-call.shop/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HealthApi::class.java)
    }
}