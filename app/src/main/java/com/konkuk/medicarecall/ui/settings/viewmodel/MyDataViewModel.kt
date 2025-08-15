package com.konkuk.medicarecall.ui.settings.viewmodel

import androidx.compose.runtime.clearCompositionErrors
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyDataViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun logout(onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            userRepository.logout()
                .onSuccess { onSuccess() }
                .onFailure { onError(it) }
        }
    }
}