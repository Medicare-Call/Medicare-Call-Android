package com.konkuk.medicarecall.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import com.konkuk.medicarecall.data.api.SettingService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingService : SettingService
) : ViewModel() {

}