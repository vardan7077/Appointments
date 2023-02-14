package com.example.appointments.data.database

import android.content.Context
import androidx.room.*
import androidx.room.Database
import com.example.appointments.data.Appointment
import com.example.appointments.data.Doctor
import com.example.appointments.data.User

import kotlinx.coroutines.InternalCoroutinesApi


@Database(
    entities = [
        User::class,
        Doctor::class,
        Appointment::class], version = 3, exportSchema = false
)
@TypeConverters(LocationsConverter::class)
abstract class Database : RoomDatabase() {
    abstract val databaseDAO: DatabaseDAO

    companion object {
        @Volatile
        private var INSTANCE: com.example.appointments.data.database.Database? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): com.example.appointments.data.database.Database {
            kotlinx.coroutines.internal.synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        com.example.appointments.data.database.Database::class.java,
                        "mainDatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}

