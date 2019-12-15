package com.iniongun.tivbible.roomdb.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iniongun.tivbible.entities.AudioSpeed
import com.iniongun.tivbible.entities.FontStyle
import com.iniongun.tivbible.entities.Setting
import com.iniongun.tivbible.entities.Theme
import com.iniongun.tivbible.roomdb.database.TivBibleDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith

/**
 * Created by Isaac Iniongun on 2019-12-13.
 * For Tiv Bible project.
 */

@RunWith(AndroidJUnit4::class)
class SettingDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mTivBibleDatabase: TivBibleDatabase

    private lateinit var settingDao: SettingDao

    private val testFontStyle = FontStyle(name = "Sans Serif")
    private val testTheme = Theme(name = "Dark")
    private val testAudioSpeed = AudioSpeed(name = "High")
    private val testSetting = Setting(12, 2, testFontStyle, testTheme, true, testAudioSpeed)

    @Before
    internal fun setUp() {

        mTivBibleDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(), TivBibleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        settingDao = mTivBibleDatabase.settingDao()

    }

    @After
    internal fun tearDown() {
        mTivBibleDatabase.close()
    }

    @Test
    internal fun getAllSettings_shouldReturnNoValues_whenNoValuesInserted() {

        val settings = settingDao.getAllSettings().blockingFirst()

        assertTrue(settings.isNullOrEmpty())

    }

    @Test
    internal fun getAllSettings_shouldReturnValues_whenValuesInserted() {

        settingDao.insertSettings(testSetting).blockingAwait()

        val settings = settingDao.getAllSettings().blockingFirst()

        assertTrue(settings.isNotEmpty())
        assertEquals(settings[0].id, testSetting.id)

    }

    @Test
    internal fun getSettingsById_shouldReturnNoValue_whenNoValueInserted() {

        assertThrows<EmptyResultSetException> { settingDao.getSettingById(testSetting.id).blockingGet() }

    }

    @Test
    internal fun getSettingsById_shouldReturnValue_whenValueInserted() {

        settingDao.insertSettings(testSetting).blockingAwait()

        val setting = settingDao.getSettingById(testSetting.id).blockingGet()

        assertNotNull(setting)
        assertEquals(setting.id, testSetting.id)

    }

    @Test
    internal fun deleteSettings_shouldReturnNoValue_whenValuesInserted_andThenDeleted_andThenRetrieved() {

        settingDao.insertSettings(testSetting).blockingAwait()

        settingDao.deleteSettings().blockingAwait()

        assertThrows<EmptyResultSetException> { settingDao.getSettingById(testSetting.id).blockingGet() }

    }

}