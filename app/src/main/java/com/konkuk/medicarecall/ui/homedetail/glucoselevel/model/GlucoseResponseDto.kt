package com.konkuk.medicarecall.ui.homedetail.glucoselevel.model

import com.google.gson.annotations.SerializedName

data class GlucoseResponseDto(
    val hasNext: Boolean,
    val data: List<GlucoseDayDto>
)

data class GlucoseDayDto(
    val date: String, // yyyy-MM-dd
    val records: List<GlucoseRecordDto>
)

data class GlucoseRecordDto(
    val time: String, // HH:mm
    val type: GlucoseType, // FASTING, AFTER_MEAL
    val value: Int,
    val status: GlucoseStatus // LOW, NORMAL, HIGH
)

enum class GlucoseType {
    @SerializedName("FASTING") FASTING,
    @SerializedName("AFTER_MEAL") AFTER_MEAL
}

