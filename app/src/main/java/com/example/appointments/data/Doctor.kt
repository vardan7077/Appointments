package com.example.appointments.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "Doctors")
data class Doctor(
    @PrimaryKey(autoGenerate = true)
    var doctorID: Long = 0,
    @ColumnInfo("firstName")
    var firstName: String,
    @ColumnInfo("lastName")
    var lastName: String,
    @ColumnInfo("specialisation")
    var specialisation: String,
    @ColumnInfo("locations")
    var locations : List<String> = listOf()

)
