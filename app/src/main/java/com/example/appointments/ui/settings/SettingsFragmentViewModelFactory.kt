package com.example.appointments.ui.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appointments.data.database.DatabaseDAO
import java.util.*

class SettingsFragmentViewModelFactory(
    private val application: Application,
    private val databaseDAO: DatabaseDAO
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(application, databaseDAO) as T
        }
        throw IllformedLocaleException("Unknown ViewModel Class")
    }
}