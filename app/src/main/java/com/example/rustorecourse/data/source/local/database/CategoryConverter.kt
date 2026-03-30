package com.example.rustorecourse.data.source.local.database

import androidx.room.TypeConverter
import com.example.rustorecourse.domain.model.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoryConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromCategory(category: Category?): String {
        return category?.name ?: ""
    }

    @TypeConverter
    fun toCategory(name: String): Category? {
        if (name.isEmpty()) return null
        return try {
            Category.valueOf(name)  
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}