package com.konkuk.medicarecall.ui.login_info.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.data.repository.VerificationRepository
import com.konkuk.medicarecall.ui.login_info.uistate.LoginState
import com.konkuk.medicarecall.ui.login_info.uistate.LoginUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginViewModel(
    private val verificationRepository: VerificationRepository
) : ViewModel() {

    val isLoggedIn = false // TODO: 추후 서버나 로컬에서 정보 받아오기

    ///// 홈 작업을 위해 트루로 변경///////
    // 로그인 되어 있는지 아닌 지에 대한 정보
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Loading)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    // 현재 가입 단계에 대한 정보
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Start)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    fun updateLoginUiState(newState: LoginUiState) {
        _loginUiState.update { newState }
    }

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


    init {
        checkLoginStatus()
    }

    fun checkLoginStatus() {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
        }
        if (isLoggedIn) {
            _loginState.value = LoginState.LoggedIn
        } else {
            _loginState.value = LoginState.NotLoggedIn
        }

    }

    // 서버 통신 함수
    fun postPhoneNumber(phone: String) {
        viewModelScope.launch {
            verificationRepository.requestCertificationCode(phone).fold(
                onSuccess = {
                    Log.d("phoneveri", "성공, ${it.message()}")
                },
                onFailure = { error ->
                    Log.d("phoneveri", "실패, ${error.message.toString()}")
                }
            )
        }
    }

    var isVerified = false
    var token = ""
        private set

    suspend fun postCertificationCode(phone: String, code: String): Boolean {
        return withContext(Dispatchers.IO) {
            verificationRepository.confirmPhoneNumber(phone, code).fold(
                onSuccess = {
                    Log.d(
                        "phoneveri",
                        "${it.message} ${it.memberStatus} ${it.token} ${it.verified} ${it.nextAction}"
                    )
                    isVerified = it.verified
                    if (isVerified) {
                        token = it.token
                    }
                    it.verified

                },
                onFailure = { error ->
                    Log.d("phoneveri", "실패, ${error.message.toString()}")
                    false
                }
            )
        }
    }
}

