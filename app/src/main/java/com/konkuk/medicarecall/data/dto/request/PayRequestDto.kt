package com.konkuk.medicarecall.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class PayRequestDto(
    val merchantUserKey : String,
    val merchantPayKey : String,
    val productName : String,
    val productCount : Int,
    val totalPayAmount : Int,
    val taxScopeAmount : Int,
    val taxExScopeAmount : Int,
    val returnUrl : String,
    val purchaserName : String,
    val purchaserBirthday : String
)