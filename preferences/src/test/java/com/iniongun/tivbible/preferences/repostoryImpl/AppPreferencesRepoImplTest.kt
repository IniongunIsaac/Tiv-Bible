package com.iniongun.tivbible.preferences.repostoryImpl

import android.content.SharedPreferences
import com.google.gson.Gson
import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.preferences.utils.PreferenceConstants
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Created by Isaac Iniongun on 2019-12-08.
 * For Tiv Bible project.
 */

@ExtendWith(MockKExtension::class)
internal class AppPreferencesRepoImplTest {

    @MockK
    private lateinit var sharedPreference: SharedPreferences

    @MockK
    private lateinit var editor: SharedPreferences.Editor

    @MockK
    private lateinit var gson: Gson

    //SUT
    private lateinit var appPreferencesRepoImpl: AppPreferencesRepoImpl

    private val testKey = "TestKey"

    private val testKeyCaptor = slot<String>()

    @MockK
    private lateinit var testVerse: Verse

    @BeforeEach
    internal fun setUp() {

        appPreferencesRepoImpl = AppPreferencesRepoImpl(sharedPreference, gson)

        every { sharedPreference.edit() } returns editor

    }

    @Test
    internal fun getPreference_shouldReceiveCorrectKey() {
        every { appPreferencesRepoImpl.getPreference(capture(testKeyCaptor), "") } returns ""

        appPreferencesRepoImpl.getPreference(testKey, "")

        assertEquals(testKey, testKeyCaptor.captured)
    }

    @Test
    internal fun putPreference_shouldReceiveCorrectKey() {

        every { appPreferencesRepoImpl.setPreference(capture(testKeyCaptor), "") } answers {}

        appPreferencesRepoImpl.setPreference(testKey, "")

        assertEquals(testKey, testKeyCaptor.captured)
    }

    @Nested
    @DisplayName("Get Values from Shared Preferences")
    inner class GetPreference {

        @Test
        internal fun shouldReturnInt() {
            every { appPreferencesRepoImpl.getPreference(testKey, 0) } returns 0
            val intResult = appPreferencesRepoImpl.getPreference(testKey, 0)
            assertEquals(0, intResult)
        }

        @Test
        internal fun shouldReturnLong() {
            every { appPreferencesRepoImpl.getPreference(testKey, 0L) } returns 0L
            val longResult = appPreferencesRepoImpl.getPreference(testKey, 0L)
            assertEquals(0L, longResult)
        }

        @Test
        internal fun shouldReturnBooleanTrue() {
            every { appPreferencesRepoImpl.getPreference(testKey, true) } returns true
            val booleanResult = appPreferencesRepoImpl.getPreference(testKey, true)
            assertEquals(true, booleanResult)
        }

        @Test
        internal fun shouldReturnBooleanFalse() {
            every { appPreferencesRepoImpl.getPreference(testKey, false) } returns false
            val booleanResult = appPreferencesRepoImpl.getPreference(testKey, false)
            assertEquals(false, booleanResult)
        }

        @Test
        internal fun shouldReturnFloat() {
            every { appPreferencesRepoImpl.getPreference(testKey, 0.0F) } returns 0.0F
            val booleanResult = appPreferencesRepoImpl.getPreference(testKey, 0.0F)
            assertEquals(0.0F, booleanResult)
        }

        @Test
        internal fun shouldReturnEmptyString() {
            every { appPreferencesRepoImpl.getPreference(testKey, "") } returns ""
            val stringResult = appPreferencesRepoImpl.getPreference(testKey, "")
            assertEquals("", stringResult)
        }

        @Test
        internal fun shouldThrowExceptionIfInvalidDataType() {
            val exception = assertThrows(IllegalArgumentException::class.java) { appPreferencesRepoImpl.getPreference(testKey, null) }
            assertEquals(PreferenceConstants.CANT_BE_RETRIEVED_FROM_PREFERENCES_MESSAGE, exception.message)
        }

//        @Test
//        internal fun shouldReturnTrueForIsDbInitialized() {
//
//            every { appPreferencesRepoImpl.getPreference(PreferenceConstants.DB_INITIALIZED_KEY, true) } returns true
//            every { sharedPreference.getBoolean(PreferenceConstants.DB_INITIALIZED_KEY, true) } answers { true }
//
//            val booleanResult = appPreferencesRepoImpl.isDBInitialized
//
//            assertTrue(booleanResult)
//        }

        @Test
        internal fun shouldReturnFalseForIsDbInitialized() {

            every { appPreferencesRepoImpl.getPreference(PreferenceConstants.DB_INITIALIZED_KEY, false) } returns false
            every { sharedPreference.getBoolean(PreferenceConstants.DB_INITIALIZED_KEY, false) } answers { false }

            val booleanResult = appPreferencesRepoImpl.isDBInitialized

            assertFalse(booleanResult)
        }

        @Test
        internal fun shouldReturnVerse() {

        }
    }

    @Nested
    @DisplayName("Set Preferences Values")
    inner class SetPreference {

        @Test
        internal fun shouldSetInt() {
            every { editor.putInt(testKey, 0) } answers { editor }
            every { editor.apply() } answers {}

            appPreferencesRepoImpl.setPreference(testKey, 0)

            verify { editor.putInt(testKey, 0) }
            verify { editor.apply() }

            confirmVerified(editor)
        }

        @Test
        internal fun shouldSetLong() {
            every { editor.putLong(testKey, 0L) } answers { editor }
            every { editor.apply() } answers {}

            appPreferencesRepoImpl.setPreference(testKey, 0L)

            verify { editor.putLong(testKey, 0L) }
            verify { editor.apply() }

            confirmVerified(editor)
        }

        @Test
        internal fun shouldSetBooleanTrue() {
            every { editor.putBoolean(testKey, true) } answers { editor }
            every { editor.apply() } answers {}

            appPreferencesRepoImpl.setPreference(testKey, true)

            verify { editor.putBoolean(testKey, true) }
            verify { editor.apply() }

            confirmVerified(editor)
        }

        @Test
        internal fun shouldSetBooleanFalse() {
            every { editor.putBoolean(testKey, false) } answers { editor }
            every { editor.apply() } answers {}

            appPreferencesRepoImpl.setPreference(testKey, false)

            verify { editor.putBoolean(testKey, false) }
            verify { editor.apply() }

            confirmVerified(editor)
        }

        @Test
        internal fun shouldSetEmptyString() {
            every { editor.putString(testKey, "") } answers { editor }
            every { editor.apply() } answers {}

            appPreferencesRepoImpl.setPreference(testKey, "")

            verify { editor.putString(testKey, "") }
            verify { editor.apply() }

            confirmVerified(editor)
        }

        @Test
        internal fun shouldThrowExceptionIfInvalidValue() {
            val exception = assertThrows(IllegalArgumentException::class.java) { appPreferencesRepoImpl.setPreference(testKey, null) }
            assertEquals(PreferenceConstants.CANT_BE_SAVED_TO_PREFERENCES_MESSAGE.replace("value", "null", true), exception.message)
        }

        @Test
        internal fun shouldSetIsDbInitializedToTrue() {
            every { editor.putBoolean(PreferenceConstants.DB_INITIALIZED_KEY, true) } answers { editor }
            every { editor.apply() } answers {}

            appPreferencesRepoImpl.isDBInitialized = true

            verify { editor.putBoolean(PreferenceConstants.DB_INITIALIZED_KEY, true) }
            verify { editor.apply() }

            confirmVerified(editor)
        }

        @Test
        internal fun shouldSetIsDbInitializedToFalse() {
            every { editor.putBoolean(PreferenceConstants.DB_INITIALIZED_KEY, false) } answers { editor }
            every { editor.apply() } answers {}

            appPreferencesRepoImpl.isDBInitialized = false

            verify { editor.putBoolean(PreferenceConstants.DB_INITIALIZED_KEY, false) }
            verify { editor.apply() }

            confirmVerified(editor)
        }

        @Test
        internal fun shouldSetVerse() {

            val testVerseString = gson.toJson(testVerse)

            every { editor.putString(PreferenceConstants.TEST_PREFERENCE_VERSE_KEY, testVerseString) } answers { editor }
            every { editor.apply() } answers {}
            every { gson.toJson(any()) } returns testVerseString

            appPreferencesRepoImpl.testPreferenceVerse = testVerse

            verify { editor.putString(PreferenceConstants.TEST_PREFERENCE_VERSE_KEY, testVerseString) }
            verify { editor.apply() }

            confirmVerified(editor)

        }
    }

}