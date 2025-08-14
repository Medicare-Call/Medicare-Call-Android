package com.konkuk.medicarecall.ui.homedetail.statemental.model
@kotlinx.serialization.Serializable
data class MentalResponseDto(
    val date: String,
    val commentList: List<String> = emptyList()
)