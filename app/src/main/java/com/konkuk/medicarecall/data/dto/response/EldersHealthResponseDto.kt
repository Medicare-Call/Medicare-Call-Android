package com.konkuk.medicarecall.data.dto.response


import com.konkuk.medicarecall.data.dto.request.MedicationSchedule
import com.konkuk.medicarecall.ui.model.HealthIssueType
import kotlinx.serialization.Serializable

@Serializable
data class EldersHealthResponseDto(
    val elderId : Int,
    val name : String,
    val diseases : List<String>,
    val medications : List<MedicationSchedule>,
    val specialNotes : List<HealthIssueType>
    )
