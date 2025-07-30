package com.konkuk.medicarecall.ui.homedetail.medicine.data

import com.konkuk.medicarecall.ui.homedetail.medicine.model.MedicineResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MedicineApi {
    @GET("/api/view/dailyMedication")
    suspend fun getDailyMedication(
        @Query("guardianId") guardianId: Int,
        @Query("date") date: String
    ): MedicineResponseDto
}