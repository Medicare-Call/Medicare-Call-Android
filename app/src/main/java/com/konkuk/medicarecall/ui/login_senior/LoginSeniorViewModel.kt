package com.konkuk.medicarecall.ui.login_senior

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.konkuk.medicarecall.ui.model.RelationshipType
import com.konkuk.medicarecall.ui.model.SeniorLivingType

class LoginSeniorViewModel : ViewModel() {


    // 어르신 정보 화면

    var expandedFormIndex by mutableIntStateOf(0)




    var name by mutableStateOf("")
        private set
    var dateOfBirth by mutableStateOf("")
        private set

    var isMale by mutableStateOf<Boolean?>(null)
        private set
    var phoneNumber by mutableStateOf("")
        private set

    var relationship by mutableStateOf("")
        private set

    var livingType by mutableStateOf("")
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

    fun onRelationshipChanged(new: String) {
        relationship = new
    }

    fun onLivingTypeChanged(new: String) {
        livingType = new
    }

    // 건강정보 화면
    var selectedSenior by mutableIntStateOf(0)
        private set

    fun onSeniorChanged(new: Int) {
        selectedSenior = new
    }


}