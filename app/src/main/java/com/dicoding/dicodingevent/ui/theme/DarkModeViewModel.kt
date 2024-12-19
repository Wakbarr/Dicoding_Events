package com.dicoding.dicodingevent.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DarkModeViewModel(private val preferences: DarkModePreferences) : ViewModel() {
    fun getDarkModeSetting(): LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }

    //launch
    fun saveDarkModeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkModeActive)
        }
    }
}