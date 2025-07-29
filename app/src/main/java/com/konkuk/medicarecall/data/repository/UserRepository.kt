package com.konkuk.medicarecall.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.data.api.SettingService
import com.konkuk.medicarecall.data.dto.request.UserUpdateRequestDto
import com.konkuk.medicarecall.data.dto.response.MyInfoResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.onSuccess

class UserRepository(private val api: SettingService) {
    suspend fun updateMe(dto: UserUpdateRequestDto): Result<MyInfoResponse> =
        runCatching {
            val resp = api.updateMyInfo(dto)
            if (resp.isSuccessful) resp.body()!!
            else throw HttpException(resp)
        }
}

class UserViewModel(private val repo: UserRepository): ViewModel() {
    fun updateMyInfo(dto: UserUpdateRequestDto) = viewModelScope.launch {
        repo.updateMe(dto)
            .onSuccess { updatedUser ->
                // 화면용 상태 업데이트
            }
            .onFailure { e ->
                // 에러 처리
            }
    }
}