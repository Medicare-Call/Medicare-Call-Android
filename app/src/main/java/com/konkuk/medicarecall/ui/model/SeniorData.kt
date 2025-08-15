package com.konkuk.medicarecall.ui.model

data class SeniorData(
    val name: String = "",
    val birthDate: String = "",
    val gender: Boolean,
    val phoneNumber: String = "",
    val relationship: String = "",
    val livingType: String = "",
    val id: Int? = null,
)
