package com.konkuk.medicarecall.ui.splash.viewmodel

import android.text.TextUtils.isEmpty
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.medicarecall.data.repository.ElderIdRepository
import com.konkuk.medicarecall.data.repository.EldersInfoRepository
import com.konkuk.medicarecall.ui.model.NavigationDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val eldersInfoRepository: EldersInfoRepository,
    private val elderIdRepository: ElderIdRepository
) : ViewModel() {
    private val _navigationDestination = MutableStateFlow<NavigationDestination?>(null)
    val navigationDestination = _navigationDestination.asStateFlow()

    init {
        checkElderStatus()
    }

    private fun checkElderStatus() {
        viewModelScope.launch {
            eldersInfoRepository.getElders()
                .onSuccess {
                    if (it.isEmpty()) {
                        _navigationDestination.value = NavigationDestination.GoToRegisterElder
                        Log.d("httplog", "어르신 없음, 어르신 등록 화면으로")
                    } else {
                        it.forEach { elderInfo ->
                            elderIdRepository.addElderId(elderInfo.name, elderInfo.elderId)
                        }
                        checkTimeStatus()

                    }
                }
                .onFailure { exception ->
                    Log.e("httplog", "최종 확인 실패, 로그인 화면으로 이동: $exception")
                    _navigationDestination.value = NavigationDestination.GoToLogin
                }

        }
    }

    private fun checkTimeStatus() {
        // TODO: 시간 설정 조회 API 연동
        checkPaymentStatus()
    }

    private fun checkPaymentStatus() {
        viewModelScope.launch {
            eldersInfoRepository.getSubscriptions()
                .onSuccess {
                    if (it.isEmpty()) {
                        _navigationDestination.value = NavigationDestination.GoToPayment
                        Log.d("httplog", "구독 정보 없음, 결제 화면으로")
                        
                    } else {
                        _navigationDestination.value = NavigationDestination.GoToHome
                        Log.d("httplog", "모든 정보 있음, 홈 화면으로")
                        
                    }
                }
                .onFailure { exception ->
                    Log.e("httplog", "최종 확인 실패, 로그인 화면으로 이동: $exception")
                    _navigationDestination.value = NavigationDestination.GoToLogin
                }
        }
    }
}