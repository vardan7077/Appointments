package com.example.appointments.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
    @PrimaryKey
    var userID: Long = 1,
    @ColumnInfo("firstName")
    var firstName: String,
    @ColumnInfo("lastName")
    var lastName: String,
    @ColumnInfo("age")
    var age: Int,
    @ColumnInfo("sex")
    var sex: String
)
