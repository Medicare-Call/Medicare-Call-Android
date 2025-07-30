package com.konkuk.medicarecall.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class PayResponse (
    val code : String,
    val message : String,
    val body : PayBody
)

@Serializable
data class PayBody(
    val reservedId : String
)