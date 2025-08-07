package com.konkuk.medicarecall.ui.homedetail.statemental.di

import com.konkuk.medicarecall.ui.homedetail.statemental.data.MentalApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MentalNetworkModule {

    @Provides
    @Singleton
    fun provideMentalApi(retrofit: Retrofit): MentalApi {
        return retrofit.create(MentalApi::class.java)
    }
}