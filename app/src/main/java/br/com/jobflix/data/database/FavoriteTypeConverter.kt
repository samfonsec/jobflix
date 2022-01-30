package br.com.jobflix.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoriteTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun listToString(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToList(jsonText: String): List<String> {
        val objectType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(jsonText, objectType)
    }
}