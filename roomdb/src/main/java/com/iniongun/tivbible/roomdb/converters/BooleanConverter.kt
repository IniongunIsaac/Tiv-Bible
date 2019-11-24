package com.iniongun.tivbible.roomdb.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Isaac Iniongun on 2019-11-24
 * For Tiv Bible project
 */

class BooleanConverter{
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromBoolean(value: Boolean?): String? {
            if (value == null) {
                return null
            }
            return Gson().toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toBoolean(value: String?): Boolean? {
            if (value == null) {
                return null
            }
            val type = object : TypeToken<Boolean>() {}.type
            return Gson().fromJson(value, type)
        }
    }
}