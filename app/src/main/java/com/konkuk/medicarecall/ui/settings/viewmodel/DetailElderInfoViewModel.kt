package com.konkuk.medicarecall.ui.settings.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.data.dto.request.ElderRegisterRequestDto
import com.konkuk.medicarecall.data.dto.response.EldersInfoResponseDto
import com.konkuk.medicarecall.data.repository.UpdateElderInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailElderInfoViewModel @Inject constructor(
    private val eldersInfoRepository: UpdateElderInfoRepository,
) : ViewModel() {

    fun updateElderInfo(elderInfo : EldersInfoResponseDto) {
        val updateInfo = ElderRegisterRequestDto(
            name = elderInfo.name,
            birthDate = elderInfo.birthDate,
            gender = elderInfo.gender,
            phone = elderInfo.phone,
            relationship = elderInfo.relationship,
            residenceType = elderInfo.residenceType
        )
        Log.d("UpdateElderInfoViewModel", "어르신 개인 정보 수정 요청: $updateInfo")
        viewModelScope.launch {
            eldersInfoRepository.updateElderInfo(
                id = elderInfo.elderId,
                request = updateInfo
            )
                .onSuccess {
                    Log.d("UpdateElderInfoViewModel", "어르신 개인 정보 수정 완료: $it")
                }
                .onFailure { exception ->
                    Log.e("UpdateElderInfoViewModel", "어르신 개인 정보 수정 실패: $exception")
                }
        }
    }

    fun deleteElderInfo(elderId: Int) {
        Log.d("DeleteElderInfoViewModel", "어르신 정보 삭제 요청: ID = $elderId")
        viewModelScope.launch {
            eldersInfoRepository.deleteElder(elderId)
                .onSuccess {
                    Log.d("DeleteElderInfoViewModel", "어르신 정보 삭제 완료: ID = $elderId")
                }
                .onFailure { exception ->
                    Log.e("DeleteElderInfoViewModel", "어르신 정보 삭제 실패: $exception")
                }
        }
    }


}