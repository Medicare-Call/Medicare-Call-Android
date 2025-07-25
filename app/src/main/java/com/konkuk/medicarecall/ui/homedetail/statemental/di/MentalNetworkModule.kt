package com.konkuk.medicarecall.ui.homedetail.statemental.di

import com.konkuk.medicarecall.ui.homedetail.statemental.data.MentalApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MentalNetworkModule {

    @Provides
    @Singleton
    fun provideMentalApi(): MentalApi {
        return Retrofit.Builder()
            .baseUrl("https://medicare-call.shop/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MentalApi::class.java)
    }
}