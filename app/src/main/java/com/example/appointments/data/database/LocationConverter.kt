package com.example.appointments.data.database
import androidx.room.TypeConverter

class LocationsConverter{
    @TypeConverter
    fun stringToList(data: String?): List<String?> {
        val listOfLocations = ArrayList<String>()
        data?.split(";")?.map {
            if(it.length > 1)
                listOfLocations.add(it)
        }
        return listOfLocations
    }

    @TypeConverter
    fun listToString(list:List<String>): String {
       val stringBuilder = StringBuilder()
        for (item in list){
            stringBuilder.append(item)
            stringBuilder.append(";")
        }
        return stringBuilder.toString()
    }

}