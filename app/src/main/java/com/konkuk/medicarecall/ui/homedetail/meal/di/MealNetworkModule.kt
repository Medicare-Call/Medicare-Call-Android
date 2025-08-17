package com.konkuk.medicarecall.ui.homedetail.meal.di

import com.konkuk.medicarecall.ui.homedetail.meal.data.MealApi
import com.konkuk.medicarecall.ui.homedetail.meal.data.MealRepository
import com.konkuk.medicarecall.ui.homedetail.meal.data.MealRepositoryImpl
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

    @Provides
    @Singleton
    fun provideMealRepository(mealApi: MealApi): MealRepository {
        return MealRepositoryImpl(mealApi)
    }
}
