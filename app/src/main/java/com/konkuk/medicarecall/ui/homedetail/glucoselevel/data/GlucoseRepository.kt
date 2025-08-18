package com.konkuk.medicarecall.ui.homedetail.glucoselevel.data

import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseGraphResponseDto
import com.konkuk.medicarecall.ui.homedetail.glucoselevel.model.GlucoseType
import javax.inject.Inject

class GlucoseRepository @Inject constructor(
    private val glucoseApi: GlucoseApi
) {
    suspend fun getGlucoseGraph(
        elderId: Int,
        startDate: String,
        type: GlucoseType
    ): GlucoseGraphResponseDto {
        return glucoseApi.getGlucoseGraph(
            elderId = elderId,
            startDate = startDate,
            type = type
        )
    }

}