package com.konkuk.medicarecall.ui.model

data class SeniorHealthData(
    val diseaseNames: List<String>,
    val medicationMap: Map<MedicationTimeType, List<String>>,
    val notes: List<String>
)
