package com.example.appointments.ui.settings

import android.app.Application
import android.content.res.Resources
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointments.R
import com.example.appointments.data.User
import com.example.appointments.data.database.DatabaseDAO
import kotlinx.coroutines.*
import java.lang.Error
import java.lang.Exception

class SettingsViewModel(val application: Application, val databaseDAO: DatabaseDAO) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    lateinit var user: LiveData<User?>

    init {
        CoroutineScope(Dispatchers.IO).launch {
            user = databaseDAO.getUserLiveData()
        }
    }


    fun updateUserInformation(
        firstNameEdit: EditText,
        lastNameEdit: EditText,
        ageStringEdit: EditText,
        sexEdit: EditText
    ) {
        try {
            val firstName = firstNameEdit.text.toString()
            val lastName = lastNameEdit.text.toString()
            val age = ageStringEdit.text.toString().toInt()
            val sex = sexEdit.text.toString()

            uiScope.launch {
                withContext(Dispatchers.IO) {
                    if (databaseDAO.getUser() != null)
                        databaseDAO.updateUser(
                            User(
                                firstName = firstName,
                                lastName = lastName,
                                age = age,
                                sex = sex
                            )
                        )
                    else
                        databaseDAO.insertUser(
                            User(
                                firstName = firstName,
                                lastName = lastName,
                                age = age,
                                sex = sex
                            )
                        )
                }
                Toast.makeText(
                    application.applicationContext,
                    application.getString(R.string.user_info_updated), Toast.LENGTH_SHORT
                ).show()
            }
        } catch (error: Exception) {
            Toast.makeText(
                application.applicationContext,
                application.getString(R.string.user_info_update_failed),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }

}