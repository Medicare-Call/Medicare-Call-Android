package com.konkuk.medicarecall.ui.homedetail.medicine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.ui.homedetail.medicine.data.MedicineRepository
import com.konkuk.medicarecall.ui.homedetail.medicine.model.MedicineUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val medicineRepository: MedicineRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _medicines = MutableStateFlow<List<MedicineUiState>>(emptyList())
    val medicines: StateFlow<List<MedicineUiState>> = _medicines

    private val guardianId = 1 // TODO: 로그인 연동 시 대체

    init {
        fetchMedicines(_selectedDate.value)
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        fetchMedicines(date)
    }

    private fun fetchMedicines(date: LocalDate) {
        viewModelScope.launch {
            try {
                val result = medicineRepository.getMedicineUiStateList(guardianId, date)
                _medicines.value = result
            } catch (e: Exception) {
                // TODO: 네트워크 에러 처리 (미기록 상태 등)
                _medicines.value = emptyList()
            }
        }
    }

    // 주간 달력 표시용: 선택한 날짜 기준으로 주간 날짜 계산
    fun getCurrentWeekDates(): List<LocalDate> {
        val selected = _selectedDate.value
        val dayOfWeek = selected.dayOfWeek.value % 7 // 일요일 = 0
        val sunday = selected.minusDays(dayOfWeek.toLong())
        return (0..6).map { sunday.plusDays(it.toLong()) }
    }
}
