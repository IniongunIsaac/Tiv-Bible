package com.iniongun.tivbible.preferences.repostoryImpl

import android.content.SharedPreferences
import com.google.gson.Gson
import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.preferences.utils.PreferenceConstants
import com.iniongun.tivbible.preferences.utils.getTypeToken
import com.iniongun.tivbible.repository.preference.IAppPreferencesRepo
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class AppPreferencesRepoImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
): IAppPreferencesRepo {

    override var isDBInitialized
        get() = getPreference(PreferenceConstants.DB_INITIALIZED_KEY, false)
        set(value) = setPreference(PreferenceConstants.DB_INITIALIZED_KEY, value)

    //private val verseType = object : TypeToken<Verse>() {}.type

    override var testPreferenceVerse: Verse
        get() = gson.fromJson<Verse>(getPreference(PreferenceConstants.TEST_PREFERENCE_VERSE_KEY, ""), getTypeToken<Verse>())
        set(value) = setPreference(PreferenceConstants.TEST_PREFERENCE_VERSE_KEY, gson.toJson(value))

    @Suppress("UNCHECKED_CAST")
    @Synchronized
    fun <T> getPreference(key: String, default: T): T = with(sharedPreferences) {
        val res: Any = when (default) {
            is Long -> getLong(key, default)
            is String -> getString(key, default)
            is Int -> getInt(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            else -> throw IllegalArgumentException(PreferenceConstants.CANT_BE_RETRIEVED_FROM_PREFERENCES_MESSAGE)
        }!!

        res as T
    }

    @Synchronized
    fun <T> setPreference(key: String, value: T) = with(sharedPreferences.edit()) {
        when (value) {
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            else -> throw IllegalArgumentException(PreferenceConstants.CANT_BE_SAVED_TO_PREFERENCES_MESSAGE.replace("value", value.toString(), true))
        }.apply()
    }

}