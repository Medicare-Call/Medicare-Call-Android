package com.konkuk.medicarecall.ui.homedetail.sleep.data


import com.konkuk.medicarecall.ui.homedetail.sleep.model.SleepUiState
import java.time.LocalDate
import javax.inject.Inject

class SleepRepository @Inject constructor(
    private val sleepApi: SleepApi
) {
    suspend fun getSleepUiState(
        guardianId: Int,
        date: LocalDate
    ): SleepUiState {

        // 테스트: 2025-07-23
        if (date.toString() == "2025-07-23") {
            return SleepUiState(
                date = "2025-07-23",
                totalSleepHours = 8,
                totalSleepMinutes = 12,
                bedTime = "오후 10:12",
                wakeUpTime = "오전 6:00",
                isRecorded = true
            )
        }

        return try {
            val response = sleepApi.getDailySleep(guardianId, date.toString())
            if (response.totalSleep != null && response.sleepTime != null && response.wakeTime != null) {
                SleepUiState(
                    date = response.date,
                    totalSleepHours = response.totalSleep.hours ?: 0,
                    totalSleepMinutes = response.totalSleep.minutes ?: 0,
                    bedTime = response.sleepTime,
                    wakeUpTime = response.wakeTime,
                    isRecorded = true
                )
            } else {
                SleepUiState(
                    date = response.date,
                    totalSleepHours = 0,
                    totalSleepMinutes = 0,
                    bedTime = "오후 --:--",
                    wakeUpTime = "오전 --:--",
                    isRecorded = false
                )
            }
        } catch (e: Exception) {
            SleepUiState(
                date = date.toString(),
                totalSleepHours = 0,
                totalSleepMinutes = 0,
                bedTime = "오후 --:--",
                wakeUpTime = "오전 --:--",
                isRecorded = false
            )
        }
    }
}