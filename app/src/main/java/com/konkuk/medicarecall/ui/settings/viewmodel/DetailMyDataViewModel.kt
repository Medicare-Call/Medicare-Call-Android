package com.konkuk.medicarecall.ui.settings.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.data.dto.response.MyInfoResponseDto
import com.konkuk.medicarecall.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMyDataViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(){
    fun updateUserData(
        userInfo : MyInfoResponseDto
    ) {
        viewModelScope.launch {
            userRepository.updateMyInfo(userInfo)
                .onSuccess {
                    Log.d("DetailMyDataViewModel", "사용자 정보 업데이트 성공: $it")
                }
                .onFailure { exception ->
                    Log.e("DetailMyDataViewModel", "사용자 정보 업데이트 실패: ${exception.message}", exception)
                    exception.printStackTrace()
                }
        }
    }
}