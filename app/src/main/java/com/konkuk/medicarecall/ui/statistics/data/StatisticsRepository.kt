package com.konkuk.medicarecall.ui.statistics.data

import com.konkuk.medicarecall.data.repository.EldersHealthInfoRepository
import com.konkuk.medicarecall.ui.statistics.model.AverageSleepDto
import com.konkuk.medicarecall.ui.statistics.model.BloodSugarDetailDto
import com.konkuk.medicarecall.ui.statistics.model.BloodSugarDto
import com.konkuk.medicarecall.ui.statistics.model.MealStatsDto
import com.konkuk.medicarecall.ui.statistics.model.MedicationStatDto
import com.konkuk.medicarecall.ui.statistics.model.PsychSummaryDto
import com.konkuk.medicarecall.ui.statistics.model.StatisticsResponseDto
import com.konkuk.medicarecall.ui.statistics.model.SummaryStatsDto
import retrofit2.HttpException
import javax.inject.Inject

interface StatisticsRepository {
    suspend fun getStatistics(elderId: Int, startDate: String): StatisticsResponseDto
}


class StatisticsRepositoryImpl @Inject constructor(
    private val statisticsApi: StatisticsApi,
    private val eldersHealthInfoRepository: EldersHealthInfoRepository
) : StatisticsRepository {

    override suspend fun getStatistics(elderId: Int, startDate: String): StatisticsResponseDto {
        return try {
            val response = statisticsApi.getStatistics(elderId = elderId, startDate = startDate)


            // 서버가 200 OK로 응답했지만, 내용이 '미기록' 상태일 때만 보내주는 특정 메시지인지 확인
            // "실제 0% 기록"과 "미기록"을 구분
            val isUnrecordedSummary = response.healthSummary.contains("전혀 이루어지지 않아")

            if (isUnrecordedSummary) {
                // 요약 메시지가 '미기록'을 의미하면, 직접 미기록 DTO를 생성하여 반환
                createUnrecordedStatisticsDto(elderId)
            } else {

                response
            }
            // --- 수정된 부분 끝 ---

        } catch (e: Exception) {
            // 404 에러가 발생하는 경우에도 미기록 DTO를 생성합니다.
            if (e is HttpException && e.code() == 404) {
                createUnrecordedStatisticsDto(elderId)
            } else {
                throw e
            }
        }
    }

    private suspend fun createUnrecordedStatisticsDto(elderId: Int): StatisticsResponseDto {
        val healthInfo = eldersHealthInfoRepository.getEldersHealthInfo()
            .getOrNull()
            ?.firstOrNull { it.elderId == elderId }

        val elderName = healthInfo?.name ?: ""

        val medicationStats = healthInfo?.medications?.values
            ?.flatten()
            ?.distinct()
            ?.associateWith { MedicationStatDto(takenCount = -1, totalCount = 0) }
            ?: emptyMap()

        return StatisticsResponseDto(
            elderName = elderName,
            summaryStats = SummaryStatsDto(
                mealRate = -1,
                medicationRate = -1,
                healthSignals = -1,
                missedCalls = -1
            ),
            mealStats = MealStatsDto(breakfast = -1, lunch = -1, dinner = -1),
            medicationStats = medicationStats,
            healthSummary = "아직 충분한 기록이 쌓이지 않았어요.",
            averageSleep = AverageSleepDto(hours = -1, minutes = -1),
            psychSummary = PsychSummaryDto(good = -1, normal = -1, bad = -1),
            bloodSugar = BloodSugarDto(
                beforeMeal = BloodSugarDetailDto(normal = 0, high = 0, low = 0),
                afterMeal = BloodSugarDetailDto(normal = 0, high = 0, low = 0)
            )
        )
    }
}