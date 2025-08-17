package com.konkuk.medicarecall.ui.statistics.data

import com.konkuk.medicarecall.ui.statistics.model.StatisticsResponseDto
import javax.inject.Inject



interface StatisticsRepository {
    suspend fun getStatistics(elderId: Int, startDate: String): StatisticsResponseDto
}


class StatisticsRepositoryImpl @Inject constructor(
    private val statisticsApi: StatisticsApi
) : StatisticsRepository {

    override suspend fun getStatistics(elderId: Int, startDate: String): StatisticsResponseDto {
        return statisticsApi.getStatistics(elderId = elderId, startDate = startDate)
    }
}
