package com.konkuk.medicarecall.data.dto.request

import com.konkuk.medicarecall.ui.model.ElderResidenceType
import com.konkuk.medicarecall.ui.model.GenderType
import com.konkuk.medicarecall.ui.model.RelationshipType
import kotlinx.serialization.Serializable

@Serializable
data class ElderRegisterRequestDto(
    val name: String,
    val birthDate: String,
    val gender: GenderType,
    val phone: String,
    val relationship: RelationshipType,
    val residenceType: ElderResidenceType,
)
