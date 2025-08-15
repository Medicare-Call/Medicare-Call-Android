package com.konkuk.medicarecall.ui.settings.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.data.dto.response.EldersInfoResponseDto
import com.konkuk.medicarecall.data.repository.EldersInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EldersInfoViewModel @Inject constructor(
    private val eldersInfoRepository: EldersInfoRepository
) : ViewModel() {

    var eldersInfoList by mutableStateOf<List<EldersInfoResponseDto>>(emptyList())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadEldersInfo()
    }

    private fun loadEldersInfo() {
        Log.d("EldersInfoViewModel", "loadEldersInfo() 진입")
        viewModelScope.launch {
            eldersInfoRepository.getElders()
                .onSuccess {
                    Log.d("EldersInfoViewModel", "노인 개인 정보 불러오기 성공: ${it.size}개")
                    eldersInfoList = it
                    Log.d("EldersInfoViewModel", "노인 개인 정보: $eldersInfoList")
                }
                .onFailure {
                    errorMessage = "노인 개인 정보를 불러오지 못했습니다."
                    it.printStackTrace()
                    Log.e("EldersInfoViewModel", "노인 개인 정보 로딩 실패: ${it.message}", it)
                }
        }
    }
}