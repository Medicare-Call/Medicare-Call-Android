package com.konkuk.medicarecall.ui.login.login_elder

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.data.repository.ElderIdRepository
import com.konkuk.medicarecall.data.repository.ElderRegisterRepository
import com.konkuk.medicarecall.data.repository.EldersInfoRepository
import com.konkuk.medicarecall.ui.model.MedicationTimeType
import com.konkuk.medicarecall.ui.model.ElderData
import com.konkuk.medicarecall.ui.model.ElderHealthData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginElderViewModel @Inject constructor(
    private val elderRegisterRepository: ElderRegisterRepository,
    private val elderIdRepository: ElderIdRepository,
    private val eldersInfoRepository: EldersInfoRepository
) : ViewModel() {
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
    var selectedElder by mutableIntStateOf(0)
        private set

    fun onSelectedElderChanged(new: Int) {
        selectedElder = new
    }

    var diseaseInputText = mutableStateListOf<MutableState<String>>()
    var diseaseList = mutableStateListOf(mutableStateListOf<String>())

    var medMap = mutableStateListOf<SnapshotStateMap<MedicationTimeType, MutableList<String>>>(

    )
    var medInputText = mutableStateListOf<MutableState<String>>()
    var healthIssueList = mutableStateListOf(mutableStateListOf<String>())


    // 기타 데이터

    val elderDataList = mutableStateListOf<ElderData>()
    fun createElderDataList() {
        elderDataList.clear()
        elderDataList.apply {
            repeat(elders) { index ->
                add(
                    ElderData(
                        nameList[index],
                        dateOfBirthList[index],
                        isMaleBoolList[index] ?: true,
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

    val elderHealthDataList = mutableStateListOf<ElderHealthData>()

    fun createElderHealthDataList() {
        elderHealthDataList.clear()
        elderHealthDataList.apply {
            repeat(elders) { index ->
                add(
                    ElderHealthData(
                        diseaseNames = diseaseList[index],
                        medicationMap = medMap[index],
                        notes = healthIssueList[index],
                    )
                )
            }
        }
    }

    // ------------------repo에서 elderId 가져오기------------------
    fun getElderIds(): List<Map<String, Int>> {
        return elderIdRepository.getElderIds()
    }

    // ------------------API 요청------------------
    fun postElderAndHealth() {
        viewModelScope.launch {
            elderRegisterRepository.registerElderAndHealth(
                elders = elders,
                elderInfoList = elderDataList,
                elderHealthInfo = elderHealthDataList
            )
                .onSuccess {
                    Log.d("httplog", "어르신 및 건강정보 전부 등록 성공!")
                }
                .onFailure { exception ->
                    Log.e("httplog", "어르신 정보 or 건강정보 등록 실패: ${exception.message}")
                }
        }
    }

    fun updateAllElders() { // getElderIds.isNotEmpty == true
        viewModelScope.launch {
            val elderIds = elderIdRepository.getElderIds()
            elderIds.forEachIndexed { index, it ->
                eldersInfoRepository.updateElder(
                    it.values.first(), elderDataList[index]
                ).onSuccess {
                    Log.d("httplog", "어르신 재등록(수정) 성공")
                }.onFailure { exception ->
                    Log.e("httplog", "어르신 정보 등록 실패: ${exception.message}")
                }
            }

        }

    }

    fun updateAllEldersHealthInfo() {
        viewModelScope.launch {
            val elderIds = elderIdRepository.getElderIds()
                elderIds.forEachIndexed { index, it ->
                    runCatching {
                        elderRegisterRepository.postElderHealthInfo(
                            it.values.first(),
                            elderHealthDataList[index]
                        )
                    }.onSuccess {
                        Log.d("httplog", "어르신 건강정보 재등록(수정) 성공")
                    }
                }
        }
    }
}


