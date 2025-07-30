package com.konkuk.medicarecall.ui.homedetail.glucoselevel.data


import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseGraphResponseDto
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseResponseDto
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseType
import retrofit2.http.GET
import retrofit2.http.Query

interface GlucoseApi {
    @GET("/api/view/blood-sugar/list")
    suspend fun getWeeklyGlucose(
        @Query("guardianId") guardianId: Int,
        @Query("cursorDate") cursorDate: String?,
        @Query("size") size: Int
    ): GlucoseResponseDto


    @GET("/api/view/blood-sugar/weekly")
    suspend fun getGlucoseGraph(
        @Query("guardianId") guardianId: Int,
        @Query("startDate") startDate: String,
        @Query("type") type: GlucoseType // FASTING or AFTER_MEAL
    ): GlucoseGraphResponseDto
}