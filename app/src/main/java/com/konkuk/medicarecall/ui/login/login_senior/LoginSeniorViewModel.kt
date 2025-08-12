package com.konkuk.medicarecall.ui.login.login_senior

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import com.konkuk.medicarecall.ui.model.MedicationTimeType
import com.konkuk.medicarecall.ui.model.SeniorData
import com.konkuk.medicarecall.ui.model.SeniorHealthData
import kotlin.collections.getValue
import kotlin.collections.mutableMapOf
import kotlin.collections.setValue

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

    var diseaseInputText = mutableStateListOf<MutableState<String>>()
    var diseaseList = mutableStateListOf(mutableStateListOf<String>())

    var medMap = mutableStateListOf<SnapshotStateMap<MedicationTimeType, MutableList<String>>>(

    )
    var medInputText = mutableStateListOf<MutableState<String>>()
    var healthIssueList = mutableStateListOf(mutableStateListOf<String>())


    // 기타 데이터

    val seniorDataList = mutableStateListOf<SeniorData>()
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
        initHealthData()
    }

    private fun initHealthData() {
        repeat(elders - diseaseInputText.size) {
            diseaseInputText.add(mutableStateOf(""))
            diseaseList.add(mutableStateListOf())
            medInputText.add(mutableStateOf(""))
            medMap.add(
                mutableStateMapOf(
                    MedicationTimeType.MORNING to mutableListOf(),
                    MedicationTimeType.LUNCH to mutableListOf(),
                    MedicationTimeType.DINNER to mutableListOf()
                )
            )
            healthIssueList.add(mutableStateListOf())
        }
    }

    val seniorHealthDataList = mutableStateListOf<SeniorHealthData>()

    fun createSeniorHealthDataList() {
        seniorHealthDataList.clear()
        seniorHealthDataList.apply {
            repeat(elders) { index ->
                add(
                    SeniorHealthData(
                        diseaseNames = diseaseList[index],
                        medicationMap = medMap[index],
                        notes = healthIssueList[index],
                    )
                )
            }
        }
    }


}