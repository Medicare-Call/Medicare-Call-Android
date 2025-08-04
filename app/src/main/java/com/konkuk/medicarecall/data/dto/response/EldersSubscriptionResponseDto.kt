package com.konkuk.medicarecall.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class EldersSubscriptionResponseDto(
    val subscriptions: List<EldersSubscriptionBody>
)

@Serializable
data class EldersSubscriptionBody(
    val elderId : Int,
    val name : String,
    val plan : String,
    val price : Int,
    val nextBillingDate : String,
    val startDate : String
)