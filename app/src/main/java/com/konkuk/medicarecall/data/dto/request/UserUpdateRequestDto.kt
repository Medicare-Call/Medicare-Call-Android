package com.konkuk.medicarecall.data.dto.request

import com.konkuk.medicarecall.data.dto.response.PushNotificationDto
import com.konkuk.medicarecall.ui.model.GenderType
import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateRequestDto(
    val name: String? = null,
    val birthDate: String? = null,            // "YYYY-MM-DD"
    val gender: GenderType? = null,               // enum
    val phone: String? = null,
    val pushNotification: PushNotificationDto? = null
)
