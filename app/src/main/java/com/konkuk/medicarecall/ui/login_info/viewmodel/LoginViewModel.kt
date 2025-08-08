package com.konkuk.medicarecall.ui.login_info.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.data.dto.request.MemberRegisterRequestDto
import com.konkuk.medicarecall.data.repository.DataStoreRepository
import com.konkuk.medicarecall.data.repository.MemberRegisterRepository
import com.konkuk.medicarecall.data.repository.VerificationRepository
import com.konkuk.medicarecall.ui.model.GenderType
import com.konkuk.medicarecall.ui.util.formatAsDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val verificationRepository: VerificationRepository,
    private val memberRegisterRepository: MemberRegisterRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val isLoggedIn = false // TODO: 추후 서버나 로컬에서 정보 받아오기

    ///// 홈 작업을 위해 트루로 변경///////


    // 휴대폰 번호 입력 value
    var phoneNumber by mutableStateOf("")
        private set

    // 인증번호 입력 value
    var verificationCode by mutableStateOf("")
        private set

    var name by mutableStateOf("")
        private set
    var dateOfBirth by mutableStateOf("")
        private set
    var isMale by mutableStateOf<Boolean?>(null)
        private set

    // 상태 변경 함수
    fun onPhoneNumberChanged(new: String) {
        phoneNumber = new
    }

    fun onVerificationCodeChanged(new: String) {
        verificationCode = new
    }

    fun onNameChanged(new: String) {
        name = new
    }

    fun onDOBChanged(new: String) {
        dateOfBirth = new
    }

    fun onGenderChanged(new: Boolean?) {
        isMale = new
    }





    // 서버 통신 함수
    private val debug = false
    fun postPhoneNumber(phone: String) {
        if (!debug) {
            viewModelScope.launch {
                verificationRepository.requestCertificationCode(phone).fold(
                    onSuccess = {
                        Log.d("httplog", "성공, ${it.message()}")
                    },
                    onFailure = { error ->
                        Log.d("httplog", "실패, ${error.message.toString()}")
                    }
                )
            }
        }
    }

    var isVerified = false

    suspend fun confirmPhoneNumber(phone: String, code: String): Boolean {
        if (!debug) {
            return verificationRepository.confirmPhoneNumber(phone, code).fold(
                onSuccess = {
                    Log.d(
                        "httplog",
                        "${it.message} ${it.memberStatus} ${it.accessToken} ${it.refreshToken} ${it.verified} ${it.token} "
                    )
                    isVerified = it.verified
                    if (isVerified) {
                        dataStoreRepository.saveToken(it.token)
                        dataStoreRepository.saveAccessToken(it.accessToken ?: "")
                        dataStoreRepository.saveRefreshToken(it.refreshToken ?: "")
                    }
                    it.verified

                },
                onFailure = { error ->
                    Log.d("httplog", "실패, ${error.message.toString()}")
                    false
                }
            )

        } else return true
    }

    fun memberRegister(name: String, birthDate: String, gender: GenderType) {
        viewModelScope.launch {
            val result = memberRegisterRepository.registerMember(name, birthDate.formatAsDate(), gender).fold(
                onSuccess = {
                    // 성공 로직
                    // responseDto에는 MemberRegisterResponseDto 객체가 들어있음
                    // 예: 성공 메시지 표시, 다음 화면으로 이동
                    Log.d("httplog", "성공, ${it.accessToken} ${it.refreshToken}")
                    dataStoreRepository.saveAccessToken(it.accessToken)
                    dataStoreRepository.saveRefreshToken(it.refreshToken)
                    true
                },
                onFailure = { exception ->
                    // 실패 로직
                    // exception에는 API 호출 중 발생한 예외가 들어있음
                    // 예: 에러 메시지 표시
                    Log.e("httplog", "회원가입 실패: ${exception.message}")
                    false
                }
            )
        }


    }

}

