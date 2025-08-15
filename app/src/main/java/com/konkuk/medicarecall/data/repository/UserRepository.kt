package com.konkuk.medicarecall.data.repository

import com.konkuk.medicarecall.data.api.SettingService
import com.konkuk.medicarecall.data.dto.request.UserUpdateRequestDto
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val settingService: SettingService,
    private val tokenStore : DataStoreRepository
) {
    suspend fun getMyInfo() = runCatching {
        val response = settingService.getMyInfo()
        if (response.isSuccessful) {
            response.body() ?: throw IllegalStateException("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Request failed with code ${response.code()}: $errorBody")
        }
    }

    suspend fun updateMyInfo(userUpdateRequestDto: UserUpdateRequestDto) = runCatching {
        val response = settingService.updateMyInfo(userUpdateRequestDto)
        if (response.isSuccessful) {
            response.body() ?: throw IllegalStateException("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Request failed with code ${response.code()}: $errorBody")
        }
    }

    suspend fun logout(): Result<Unit> {
        val result = runCatching {
            val refresh = tokenStore.getRefreshToken() ?: error("Refresh token is null")
            val response = settingService.logout("Bearer $refresh")
            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                throw Exception("Request failed with code ${response.code()}: $errorBody")
            }
            Unit
        }
        // 성공/실패와 무관하게 로컬 토큰 제거(보안/UX 측면에서 권장)
        tokenStore.clearTokens()
        return result
    }
}