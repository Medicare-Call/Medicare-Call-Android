package com.konkuk.medicarecall.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.konkuk.medicarecall.data.ApiClient
import com.konkuk.medicarecall.data.api.VerificationService
import com.konkuk.medicarecall.data.repository.VerificationRepository
import com.konkuk.medicarecall.ui.login_info.viewmodel.LoginViewModel

class LoginViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val verificationService  = ApiClient.verificationService
            val repository = VerificationRepository(verificationService ) // Repository 생성 및 ApiService 주입
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T // ViewModel에 Repository 주입
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }
}