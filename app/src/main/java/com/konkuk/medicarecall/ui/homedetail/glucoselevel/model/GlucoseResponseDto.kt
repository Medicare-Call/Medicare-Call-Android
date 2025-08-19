package com.konkuk.medicarecall.ui.homedetail.glucoselevel.model

import kotlinx.serialization.Serializable

@Serializable
data class GlucoseResponseDto(
    val hasNextPage: Boolean,
    val data: List<GlucoseRecordDto>
)

@Serializable
data class GlucoseRecordDto(
    val date: String, // yyyy-MM-dd
    val value: Int,
    val status: GlucoseStatus
)
