package com.konkuk.medicarecall.ui.homedetail.glucoselevel.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GlucoseResponseDto(
    val hasNext: Boolean,
    val data: List<GlucoseDayDto>
)

@Serializable
data class GlucoseDayDto(
    val date: String, // yyyy-MM-dd
    val records: List<GlucoseRecordDto>
)

@Serializable
data class GlucoseRecordDto(
    val time: String,         // HH:mm
    val type: GlucoseType,    // BEFORE_MEAL, AFTER_MEAL
    val value: Int,
    val status: GlucoseStatus // LOW, NORMAL, HIGH
)

@Serializable
enum class GlucoseType {
    @SerialName("BEFORE_MEAL,") BEFORE_MEAL,
    @SerialName("AFTER_MEAL") AFTER_MEAL
}