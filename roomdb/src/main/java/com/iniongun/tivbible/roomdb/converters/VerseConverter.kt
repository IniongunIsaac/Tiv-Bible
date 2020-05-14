package com.iniongun.tivbible.roomdb.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iniongun.tivbible.entities.Verse
import java.lang.reflect.Type

/**
 * Created by Isaac Iniongun on 14/05/2020.
 * For Tiv Bible project.
 */

object VerseConverter {
    @TypeConverter
    @JvmStatic
    fun toVerseList(value: String?): List<Verse> {
        val listType: Type = object : TypeToken<List<Verse?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromVerseListString(list: List<Verse?>?): String {
        return Gson().toJson(list)
    }
}