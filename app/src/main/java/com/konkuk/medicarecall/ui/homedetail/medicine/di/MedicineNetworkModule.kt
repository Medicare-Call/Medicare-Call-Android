package com.konkuk.medicarecall.ui.homedetail.medicine.di

import com.konkuk.medicarecall.ui.homedetail.medicine.data.MedicineApi
import com.konkuk.medicarecall.ui.homedetail.medicine.data.MedicineRepository
import com.konkuk.medicarecall.ui.homedetail.medicine.data.MedicineRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MedicineNetworkModule {

    @Provides
    @Singleton
    fun provideMedicineApi(retrofit: Retrofit): MedicineApi {
        return retrofit.create(MedicineApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMedicineRepository(medicineApi: MedicineApi): MedicineRepository {
        return MedicineRepositoryImpl(medicineApi)
    }
}