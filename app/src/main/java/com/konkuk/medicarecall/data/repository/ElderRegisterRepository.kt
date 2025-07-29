package com.konkuk.medicarecall.data.repository

import android.R.attr.name
import com.konkuk.medicarecall.data.api.ElderRegisterService
import com.konkuk.medicarecall.data.dto.request.ElderHealthRegisterRequestDto
import com.konkuk.medicarecall.data.dto.request.ElderRegisterRequestDto
import com.konkuk.medicarecall.ui.model.RelationshipType
import com.konkuk.medicarecall.ui.model.SeniorLivingType

class ElderRegisterRepository(
    private val elderRegisterService: ElderRegisterService
) {
    suspend fun postElder(
        request: ElderRegisterRequestDto
    ) = runCatching {
        elderRegisterService.postElder(request)
    }

    suspend fun postElderHealthInfo(
        id: Int,
        request: ElderHealthRegisterRequestDto
    ) = runCatching {
        elderRegisterService.postElderHealthInfo(id, request)
    }
}