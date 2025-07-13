package com.konkuk.medicarecall.ui.login_senior

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginSeniorViewModel : ViewModel() {

    // 어르신 정보 화면


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

    // 건강정보 화면
    var selectedSenior by mutableIntStateOf(0)
        private set

    fun onSeniorChanged(new: Int) {
        selectedSenior = new
    }


}