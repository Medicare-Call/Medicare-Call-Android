package com.konkuk.medicarecall.ui.homedetail.statemental.model

data class MentalUiState(
    val mentalSummary : List<String>, //심리 상태 요약 3줄
    val isRecorded : Boolean,   //기록 여부
) {
    companion object {

        val EMPTY = MentalUiState(
            mentalSummary = emptyList(),
            isRecorded = false
        )
    }
}