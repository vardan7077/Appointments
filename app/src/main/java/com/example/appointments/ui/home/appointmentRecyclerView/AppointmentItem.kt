package com.example.appointments.ui.home.appointmentRecyclerView

import com.example.appointments.data.Appointment
import com.example.appointments.data.Doctor
import com.example.appointments.data.User

data class AppointmentItem(
    var user: User,
    var doctor: Doctor,
    var editMode: Boolean = false,
    var appointment: Appointment
)