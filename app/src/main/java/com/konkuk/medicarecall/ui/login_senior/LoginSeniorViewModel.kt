package com.konkuk.medicarecall.ui.login_senior

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginSeniorViewModel : ViewModel() {


    var name by mutableStateOf("")
        private set
    var dateOfBirth by mutableStateOf("")
        private set

    var isMale by mutableStateOf<Boolean?>(null)
        private set
    var phoneNumber by mutableStateOf("")
        private set

    fun onPhoneNumberChanged(new: String) {
        phoneNumber = new
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

}