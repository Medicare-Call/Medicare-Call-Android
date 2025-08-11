package com.konkuk.medicarecall.ui.login.login_senior

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.konkuk.medicarecall.ui.model.SeniorData

class LoginSeniorViewModel : ViewModel() {


    // 어르신 정보 화면

    var expandedFormIndex by mutableIntStateOf(0)
    var elders by mutableIntStateOf(1) // 추가된 어르신 수


    var nameList = mutableStateListOf<String>().apply {
        repeat(5) { add("") }
    }
        private set
    var dateOfBirthList = mutableStateListOf<String>().apply {
        repeat(5) { add("") }
    }
        private set

    var isMaleBoolList = mutableStateListOf<Boolean?>().apply {
        repeat(5) { add(null) }
    }
        private set
    var phoneNumberList = mutableStateListOf<String>().apply {
        repeat(5) { add("") }
    }
        private set

    var relationshipList = mutableStateListOf<String>().apply {
        repeat(5) { add("") }
    }
        private set

    var livingTypeList = mutableStateListOf<String>().apply {
        repeat(5) { add("") }
    }
        private set

    fun isInputComplete(): Boolean {
        return (0 until elders).all { i ->

            nameList[i].isNotBlank() &&
            dateOfBirthList[i].length == 8 &&
            isMaleBoolList[i] != null &&
            phoneNumberList[i].length == 11 &&
            relationshipList[i].isNotBlank() &&
            livingTypeList[i].isNotBlank()
        }

    }

    fun onPhoneNumberChanged(index: Int, new: String) {
        phoneNumberList[index] = new
    }

    fun onNameChanged(index: Int, new: String) {
        nameList[index] = new
    }

    fun onDOBChanged(index: Int, new: String) {
        dateOfBirthList[index] = new
    }

    fun onGenderChanged(index: Int, new: Boolean?) {
        isMaleBoolList[index] = new
    }

    fun onRelationshipChanged(index: Int, new: String) {
        relationshipList[index] = new
    }

    fun onLivingTypeChanged(index: Int, new: String) {
        livingTypeList[index] = new
    }

    // 건강정보 화면
    var selectedSenior by mutableIntStateOf(0)
        private set

    fun onSelectedSeniorChanged(new: Int) {
        selectedSenior = new
    }

    var diseaseText = mutableStateOf("")
    var diseaseList = mutableStateListOf<String>()

    // 기타 데이터

    var seniorDataList = mutableStateListOf<SeniorData>()
        private set

    fun createSeniorDataList() {
        seniorDataList.clear()
        seniorDataList.apply {
            repeat(elders) { index ->
                add(
                    SeniorData(
                        nameList[index],
                        dateOfBirthList[index],
                        isMaleBoolList[index],
                        phoneNumberList[index],
                        relationshipList[index],
                        livingTypeList[index]
                    )
                )
            }
        }
    }


}