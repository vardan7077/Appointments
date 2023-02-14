package com.example.appointments.ui.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointments.data.Appointment
import com.example.appointments.data.Doctor
import com.example.appointments.data.database.DatabaseDAO
import com.example.appointments.ui.search.doctorsRecyclerView.DoctorItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(application: Application, private val databaseDAO: DatabaseDAO) : ViewModel() {

    var listOfDoctors: LiveData<List<Doctor?>> = MutableLiveData()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            listOfDoctors = databaseDAO.getAllDoctorsLiveData()
        }
    }

    fun createAppointment(doctorItem: DoctorItem) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = databaseDAO.getUser()
            val doctor = doctorItem.doctor
            val location = doctorItem.selectedLocation
            val reason = doctorItem.reason
            if (user != null) {
                val appointment = Appointment(
                    userID = user.userID,
                    doctorID = doctor.doctorID,
                    location = location,
                    reason = reason
                )
                databaseDAO.insertAppointment(appointment)
            }
        }
    }


}