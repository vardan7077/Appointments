package com.example.appointments.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appointments.data.Appointment
import com.example.appointments.data.Doctor
import com.example.appointments.data.User
import com.example.appointments.data.database.DatabaseDAO
import com.example.appointments.ui.home.appointmentRecyclerView.AppointmentItem
import com.example.appointments.ui.search.doctorsRecyclerView.DoctorItem
import kotlinx.coroutines.*

class HomeViewModel(application: Application, val database: DatabaseDAO) :
    AndroidViewModel(application) {

    var appointments: LiveData<List<Appointment>> = MutableLiveData()

    init {

        CoroutineScope(Dispatchers.IO).launch {
            appointments = database.getAppointmentsByUserIDLiveData(1)
        }
        addFakeDoctors()
    }

    private fun addFakeDoctors() {
        val listOfDoctors = mutableListOf(
            DoctorItem(
                doctor = Doctor(
                    firstName = "DocName",
                    lastName = "DocLastName",
                    specialisation = "Dentist",
                    locations = listOf("Glendale", "Burbank", "Santa Monica")
                )
            ),
            DoctorItem(
                doctor = Doctor(
                    firstName = "DocName2",
                    lastName = "DocLastName2",
                    specialisation = "Dentist2",
                    locations = listOf("Burbank")
                )
            ),
            DoctorItem(
                doctor =
                Doctor(
                    firstName = "DocName3",
                    lastName = "DocLastName3",
                    specialisation = "Dentist3",
                    locations = listOf("Pasadena")
                )
            ),
            DoctorItem(
                doctor =
                Doctor(
                    firstName = "DocName4",
                    lastName = "DocLastName4",
                    specialisation = "Dentist4",
                    locations = listOf("BeverlyHills")
                )
            ),
            DoctorItem(
                doctor =
                Doctor(
                    firstName = "DocName5",
                    lastName = "DocLastName5",
                    specialisation = "Dentist5",
                    locations = listOf("New york")
                )
            ),
            DoctorItem(
                doctor =
                Doctor(
                    firstName = "DocName6",
                    lastName = "DocLastName6",
                    specialisation = "Dentist6",
                    locations = listOf("Las Vegas")
                )
            )

        )
        CoroutineScope(Dispatchers.IO).launch {
            if (database.getAllDoctors().isEmpty())
                for (doctorItem in listOfDoctors) {
                    database.insertDoctor(doctorItem.doctor)
                }

        }
    }

    fun addUser() {
        CoroutineScope(Dispatchers.IO).launch {
            if (database.getUser() == null)
                database.insertUser(
                    User(
                        firstName = "Vardan",
                        lastName = "Vardanian",
                        age = 25,
                        sex = "male"
                    )
                )
        }
    }

    fun getAppointmentData(appointments: List<Appointment>): List<AppointmentItem> {
        val appointmentItems: MutableList<AppointmentItem> = mutableListOf()
        runBlocking(Dispatchers.IO) {
            for (appointment in appointments) {
                val user: User? = database.getUser()
                val doctor: Doctor? = database.getDoctorByID(appointment.doctorID)
                if (user != null && doctor != null) {
                    val appointmentItem = AppointmentItem(user, doctor, false, appointment)
                    appointmentItems.add(appointmentItem)
                }
            }

        }
        return appointmentItems.toList()
    }


    fun cancelAppointment(appointment: Appointment) {
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteAppointment(appointment)
        }
    }

    fun updateAppointment(appointment: Appointment) {
        CoroutineScope(Dispatchers.IO).launch {
            database.updateAppointment(appointment)
        }
    }

}