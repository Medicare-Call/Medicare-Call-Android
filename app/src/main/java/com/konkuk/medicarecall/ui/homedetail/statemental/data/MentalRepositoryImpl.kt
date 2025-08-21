package com.konkuk.medicarecall.ui.homedetail.statemental.data

import android.util.Log
import com.konkuk.medicarecall.ui.homedetail.statemental.model.MentalUiState
import java.time.LocalDate
import javax.inject.Inject

class MentalRepositoryImpl @Inject constructor(
    private val mentalApi: MentalApi
) : MentalRepository {

    override suspend fun getMentalUiState(
        elderId: Int,
        date: LocalDate
    ): MentalUiState = try {
        val dto = mentalApi.getDailyMental(elderId, date.toString())


        val comments = dto.commentList.orEmpty()
        Log.d("MENTAL", "comments=$comments")
        MentalUiState(
            mentalSummary = comments,
            isRecorded = comments.isNotEmpty()
        )
    } catch (e: Exception) {
        MentalUiState.EMPTY
    }
}
