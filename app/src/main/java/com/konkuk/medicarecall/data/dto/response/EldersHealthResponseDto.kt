package com.konkuk.medicarecall.data.dto.response


import com.konkuk.medicarecall.ui.model.HealthIssueType
import com.konkuk.medicarecall.ui.model.MedicationTimeType
import kotlinx.serialization.Serializable

@Serializable
data class EldersHealthResponseDto(
    val elderId : Int,
    val name : String,
    val diseases : List<String> = emptyList(),
    val medications : Map<MedicationTimeType, List<String>> = emptyMap(),
    val notes : List<HealthIssueType>
    )
