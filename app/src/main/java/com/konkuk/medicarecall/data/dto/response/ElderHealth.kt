package com.konkuk.medicarecall.data.dto.response


import com.konkuk.medicarecall.ui.model.MedicationTimeType
import com.konkuk.medicarecall.ui.model.SpecialNoteType
import kotlinx.serialization.Serializable

@Serializable
data class EldersHealthResponseDto(
    val elders: List<ElderHealth>
)

@Serializable
data class ElderHealth(
    val elderId : Int,
    val name : String,
    val diseases : List<String>,
    val medications : List<MedicationInfo>,
    val specialNotes : List<SpecialNoteType>
    )

@Serializable
data class MedicationInfo(
    val time : MedicationTimeType,
    val medList : List<String>
)