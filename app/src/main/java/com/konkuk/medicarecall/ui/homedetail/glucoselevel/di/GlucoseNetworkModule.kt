package com.konkuk.medicarecall.ui.homedetail.glucoselevel.di

import com.konkuk.medicarecall.ui.homedetail.glucoselevel.data.GlucoseApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlucoseNetworkModule {

    @Provides
    @Singleton
    fun provideGlucoseApi(retrofit: Retrofit): GlucoseApi {
        return retrofit.create(GlucoseApi::class.java)
    }

}