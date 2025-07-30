package com.konkuk.medicarecall.data.dto.response

import com.konkuk.medicarecall.ui.model.GenderType
import com.konkuk.medicarecall.ui.model.NotificationStateType
import kotlinx.serialization.Serializable


@Serializable
data class MyInfoResponse(
    val name : String,
    val birthDate : String,
    val gender : GenderType,
    val phone : String,
    val pushNotification : PushNotificationDto
)

@Serializable
data class PushNotificationDto(
    val all: NotificationStateType? = null,
    val carecallCompleted: NotificationStateType? = null,
    val healthAlert: NotificationStateType? = null,
    val carecallMissed: NotificationStateType? = null
)

