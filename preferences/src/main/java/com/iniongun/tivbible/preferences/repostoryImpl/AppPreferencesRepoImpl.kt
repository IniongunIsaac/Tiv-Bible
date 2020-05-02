package com.iniongun.tivbible.preferences.repostoryImpl

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iniongun.tivbible.common.utils.theme.ThemeConstants
import com.iniongun.tivbible.entities.Book
import com.iniongun.tivbible.entities.Chapter
import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.preferences.utils.PreferenceConstants
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

    override var currentTheme: String
        get() = getPreference(PreferenceConstants.CURRENT_THEME_KEY, ThemeConstants.SYSTEM_DEFAULT.name)
        set(value) = setPreference(PreferenceConstants.CURRENT_THEME_KEY, value)

    private val verseType = object : TypeToken<Verse>(){}.type

    override var currentVerse: Verse
        get() = gson.fromJson(getPreference(PreferenceConstants.CURRENT_VERSE_KEY, ""), verseType)
        set(value) = setPreference(PreferenceConstants.CURRENT_VERSE_KEY, gson.toJson(value, verseType))

    override var currentVerseString: String
        get() = getPreference(PreferenceConstants.CURRENT_VERSE_KEY, "")
        set(value) = setPreference(PreferenceConstants.CURRENT_VERSE_KEY, value)

    private val chapterType = object : TypeToken<Chapter>(){}.type

    override var currentChapter: Chapter
        get() = gson.fromJson(getPreference(PreferenceConstants.CURRENT_CHAPTER_KEY, ""), chapterType)
        set(value) = setPreference(PreferenceConstants.CURRENT_CHAPTER_KEY, gson.toJson(value, chapterType))

    private val bookType = object : TypeToken<Book>(){}.type

    override var currentBook: Book
        get() = gson.fromJson(getPreference(PreferenceConstants.CURRENT_BOOK_KEY, ""), bookType)
        set(value) = setPreference(PreferenceConstants.CURRENT_BOOK_KEY, gson.toJson(value, bookType))

    override var shouldReloadVerses: Boolean
        get() = getPreference(PreferenceConstants.SHOULD_RELOAD_VERSES_KEY, false)
        set(value) = setPreference(PreferenceConstants.SHOULD_RELOAD_VERSES_KEY, value)

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