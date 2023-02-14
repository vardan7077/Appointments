package com.example.appointments.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.appointments.data.Appointment
import com.example.appointments.data.Doctor
import com.example.appointments.data.User


@Dao
interface DatabaseDAO {
    @Insert
    fun insertUser(user: User)

    @Insert
    fun insertDoctor(doctor: Doctor):Long

    @Insert
    fun insertAppointment(appointment: Appointment):Long

    @Update
    fun updateUser(user: User)

    @Update
    fun updateDoctor(doctor: Doctor)

    @Update
    fun updateAppointment(appointment: Appointment)

    @Delete
    fun deleteUser(user: User)

    @Delete
    fun deleteDoctor(doctor: Doctor)

    @Query("DELETE FROM doctors")
    fun deleteAllDoctors()

    @Query("DELETE FROM appointments")
    fun deleteAllAppointments()

    @Delete
    fun deleteAppointment(appointment: Appointment)

    @Query("SELECT * FROM users WHERE userID = 1")
    fun getUserLiveData():LiveData<User?>

    @Query("SELECT * FROM users WHERE userID = 1")
    fun getUser():User?

    @Query("SELECT * FROM doctors WHERE doctorID = :doctorID")
    fun getDoctorByID(doctorID:Long) : Doctor?

    @Query("SELECT * FROM appointments WHERE appointmentID = :appointmentID")
    fun getAppointmentByID(appointmentID:Long) : Appointment?

    @Query("SELECT * FROM doctors")
    fun getAllDoctors() : List<Doctor?>

    @Query("SELECT * FROM doctors")
    fun getAllDoctorsLiveData() : LiveData<List<Doctor?>>

    @Query("SELECT * FROM appointments WHERE userID = :userID")
    fun getAppointmentsByUserIDLiveData(userID:Long) : LiveData<List<Appointment>>


//    @Query("SELECT * FROM charge_data_table ORDER BY chargingId DESC")
//    fun getAllChargingSessionsLive(): LiveData<List<ChargeData>>
//
//    @Query("SELECT * FROM charge_data_table ORDER BY chargingId DESC")
//    fun getAllChargingSessions(): List<ChargeData>?
//
//    @Query("SELECT * FROM charge_data_table WHERE chargingId = :itemID")
//    fun getChargingSessionByID(itemID: Long): ChargeData?
//
//    @Query("SELECT * FROM charge_data_table ORDER BY chargingId DESC LIMIT 2")
//    fun getRecentChargingSession(): List<ChargeData?>?
//
//    @Query("SELECT * FROM charge_data_table WHERE date = :date")
//    fun getIsChargingSessionExistByDate(date: String): ChargeData?
//
//    @Query("SELECT * FROM charge_data_table WHERE date = :date ORDER BY chargingId DESC")
//    fun getChargingSessionsByDate(date: String): List<ChargeData>?
//
//    @Query("SELECT * FROM current_charging_data")
//    fun getCurrentChargingSession(): CurrentChargingData?
//
//    @Query("SELECT * FROM current_charging_data")
//    fun getCurrentChargingSessionLive(): LiveData<CurrentChargingData>?
//
//    @Query("SELECT * FROM selected_locale")
//    fun getCurrentLocale(): Localization?
//
//    @Query("SELECT * FROM charging_status")
//    fun getChargingStatusInfoLive(): LiveData<ChargingStatusInfo>?
//
//    @Query("SELECT * FROM charging_status")
//    fun getChargingStatusInfo(): ChargingStatusInfo?
//
//    @Query("SELECT * FROM charging_status")
//    fun getAllChargingStatusInfo(): List<ChargingStatusInfo>?
//
//    @Query("DELETE FROM charge_data_table")
//    fun deleteChargingData()
//
//    @Query("DELETE FROM selected_locale")
//    fun deleteLocalizationData()
//
//    @Query("DELETE FROM current_charging_data")
//    fun deleteCurrentSession()
//
//    @Query("DELETE FROM charge_data_table WHERE chargingId = :chargingId")
//    fun deleteChargingSessionByID(chargingId:Long)
//
//    @Query("DELETE FROM charging_status")
//    fun deleteChargingStatus()


}