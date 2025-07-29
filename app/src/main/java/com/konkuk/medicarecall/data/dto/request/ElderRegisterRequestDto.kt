package com.konkuk.medicarecall.data.dto.request

import com.konkuk.medicarecall.ui.model.RelationshipType
import com.konkuk.medicarecall.ui.model.SeniorLivingType
import kotlinx.serialization.Serializable

@Serializable
data class ElderRegisterRequestDto(
    val name: String,
    val birthDate: String,
    val gender: String,
    val phone: String,
    val relationship: RelationshipType,
    val residenceType: SeniorLivingType,
)
