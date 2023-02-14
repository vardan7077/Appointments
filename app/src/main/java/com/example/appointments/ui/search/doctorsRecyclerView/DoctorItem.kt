package com.example.appointments.ui.search.doctorsRecyclerView

import com.example.appointments.data.Doctor

data class DoctorItem(
    var selectedLocation: String = "",
    var reason: String = "",
    var doctor: Doctor
)