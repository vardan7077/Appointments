package com.example.appointments.ui.search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appointments.data.database.DatabaseDAO
import java.util.*

class SearchFragmentViewModelFactory(
    private val application: Application,
    private val databaseDAO: DatabaseDAO
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(application, databaseDAO) as T
        }
        throw IllformedLocaleException("Unknown ViewModel Class")
    }
}