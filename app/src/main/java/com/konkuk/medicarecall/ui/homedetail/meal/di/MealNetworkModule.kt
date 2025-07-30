package com.konkuk.medicarecall.ui.homedetail.meal.di

import com.konkuk.medicarecall.ui.homedetail.meal.data.MealApi
import com.konkuk.medicarecall.ui.homedetail.medicine.data.MedicineApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MealNetworkModule {

    @Provides
    @Singleton
    fun provideMealApi(retrofit: Retrofit): MealApi {
        return retrofit.create(MealApi::class.java)
    }

}
