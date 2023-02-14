package com.example.appointments.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    var appointmentID: Long = 0,
    @ColumnInfo("userID")
    var userID: Long,
    @ColumnInfo("doctorID")
    var doctorID: Long,
    @ColumnInfo("location")
    var location: String,
    @ColumnInfo("reason")
    var reason: String?
)