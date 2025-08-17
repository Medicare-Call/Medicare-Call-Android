package com.konkuk.medicarecall.data.dto.response

import com.konkuk.medicarecall.ui.model.GenderType
import com.konkuk.medicarecall.ui.model.RelationshipType
import com.konkuk.medicarecall.ui.model.ElderResidenceType
import kotlinx.serialization.Serializable

@Serializable
data class EldersInfoResponseDto(
    val elderId : Int,
    val name : String,
    val birthDate : String,
    val gender : GenderType,
    val phone : String,
    val relationship : RelationshipType,
    val residenceType : ElderResidenceType
)
