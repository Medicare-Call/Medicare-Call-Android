package com.konkuk.medicarecall.data.dto.request

import com.konkuk.medicarecall.ui.model.HealthIssueType
import com.konkuk.medicarecall.ui.model.MedicationTime
import com.konkuk.medicarecall.ui.model.RelationshipType
import com.konkuk.medicarecall.ui.model.SeniorLivingType
import kotlinx.serialization.Serializable

@Serializable
data class ElderHealthRegisterRequestDto(
    val diseaseNames: List<String>? = null,
    val medicationSchedules: List<MedicationSchedule>? = null,
    val notes: List<HealthIssueType>? = null,
)

@Serializable
data class MedicationSchedule(
    val medicationName: String,
    val scheduleTimes: List<MedicationTime>
)

