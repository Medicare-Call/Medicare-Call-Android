package com.konkuk.medicarecall.ui.homedetail.glucoselevel.data


import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseGraphResponseDto
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseType
import retrofit2.http.GET
import retrofit2.http.Query

interface GlucoseApi {


    @GET("/elders/{elderId}/blood-sugar/weekly?startDate=2025-07-09&type=BEFORE_MEAL")
    suspend fun getGlucoseGraph(
        @Query("guardianId") guardianId: Int,
        @Query("startDate") startDate: String,
        @Query("type") type: GlucoseType // FASTING or AFTER_MEAL
    ): GlucoseGraphResponseDto
}