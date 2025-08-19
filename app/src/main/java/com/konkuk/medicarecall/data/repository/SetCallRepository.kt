package com.konkuk.medicarecall.data.repository

import android.util.Log
import com.konkuk.medicarecall.data.api.SetCallService
import com.konkuk.medicarecall.data.dto.request.SetCallTimeRequestDto
import com.konkuk.medicarecall.ui.model.CallTimes
import javax.inject.Inject

class SetCallRepository @Inject constructor(
    private val service : SetCallService
){
    suspend fun saveForElder(
        elderId : Int,
        body : SetCallTimeRequestDto
    ) : Result <Unit> =
        runCatching {
            val response = service.saveCareCallTimes(elderId, body)
            if (!response.isSuccessful) {
                Log.e("SetCallRepository", "HTTP ${response.code()} ${response.message()}")
                Log.e("SetCallRepository", "ErrorBody=${response.errorBody()?.string()}")
                throw Exception("Error saving care call times: ${response.errorBody()?.string()} / SetCallRepository.kt")
            }
        }

    // 오버로드: UI에서 CallTimes만 넘기면 레포가 변환까지 처리
    suspend fun saveForElder(
        elderId: Int,
        times: CallTimes
    ): Result<Unit> = saveForElder(elderId, times.toRequestDto())

    // --- 내부 변환 유틸 ---
    private fun Triple<Int, Int, Int>.toHHmm(): String {
        val (amPm, h12, m) = this
        val h24 = when {
            amPm == 0 && h12 == 12 -> 0
            amPm == 1 && h12 < 12  -> h12 + 12
            else -> h12 % 24
        }
        Log.d("SetCallRepository", "Converting time: $this to 24-hour format: $h24:$m")
        return "%02d:%02d".format(h24, m)
    }

    private fun CallTimes.toRequestDto(): SetCallTimeRequestDto =
        SetCallTimeRequestDto(
            firstCallTime  = requireNotNull(first) .toHHmm(),
            secondCallTime = requireNotNull(second).toHHmm(),
            thirdCallTime  = requireNotNull(third) .toHHmm()
        )
}