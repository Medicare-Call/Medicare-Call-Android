package com.konkuk.medicarecall.data.dto.request

data class SetCallTimeRequestDto(
    val firstCallTime : String,
    val secondCallTime : String,
    val thirdCallTime : String
)