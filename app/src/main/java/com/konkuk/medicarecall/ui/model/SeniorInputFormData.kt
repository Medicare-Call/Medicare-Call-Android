package com.konkuk.medicarecall.ui.model

data class SeniorInputFormData(
    val nameList: List<String>,
    val birthDateList: List<String>,
    val isMaleList: List<Boolean?>,
    val phoneNumberList: List<String>,
    val relationshipList: List<String>,
    val livingTypeList: List<String>

)
