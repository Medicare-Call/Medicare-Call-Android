package com.konkuk.medicarecall.ui.homedetail.glucoselevel.data


import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseGraphResponseDto
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseType
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GlucoseApi {


    @GET("elders/{elderId}/blood-sugar/weekly")
    suspend fun getGlucoseGraph(
        @Path("elderId") elderId: Int,
        @Query("startDate") startDate: String,
        @Query("type") type: GlucoseType // FASTING or AFTER_MEAL
    ): GlucoseGraphResponseDto
}