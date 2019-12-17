package com.iniongun.tivbible.roomdb.repositoryImpl.settings

import com.iniongun.tivbible.entities.AudioSpeed
import com.iniongun.tivbible.entities.FontStyle
import com.iniongun.tivbible.entities.Setting
import com.iniongun.tivbible.entities.Theme
import com.iniongun.tivbible.roomdb.dao.SettingDao
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Created by Isaac Iniongun on 2019-12-17.
 * For Tiv Bible project.
 */

@ExtendWith(MockKExtension::class)
internal class SettingsRepoImplTest {

    //class under test
    private lateinit var settingsRepoImpl: SettingsRepoImpl

    @MockK
    private lateinit var settingDaoMock: SettingDao

    private val testFontStyle = FontStyle(name = "Sans Serif")
    private val testTheme = Theme(name = "Dark")
    private val testAudioSpeed = AudioSpeed(name = "High")
    private val testSettings = listOf(
        Setting(12, 2, testFontStyle, testTheme, true, testAudioSpeed),
        Setting(12, 2, testFontStyle, testTheme, true, testAudioSpeed),
        Setting(12, 2, testFontStyle, testTheme, true, testAudioSpeed)
    )

    @BeforeEach
    fun setUp() {
        settingsRepoImpl = SettingsRepoImpl(settingDaoMock)
    }

    @Test
    fun getAllSettings_shouldReturnCorrectValues_whenValuesInserted() {

        every { settingDaoMock.getAllSettings() } returns Observable.just(testSettings)

        settingsRepoImpl.getAllSettings().test().assertValue(testSettings)
    }

    @Test
    fun getSettingById_shouldReturnValue_whenValuesInserted() {

        val setting = testSettings[0]

        every { settingDaoMock.getSettingById(any()) } returns Single.just(testSettings.first { it.id == setting.id })

        settingsRepoImpl.getSettingById(setting.id).test().assertValue(setting)
    }

    @Test
    fun insertSettings_shouldCompleteSuccessfully() {

        every { settingDaoMock.insertSettings(testSettings) } returns Completable.complete()

        settingsRepoImpl.insertSettings(testSettings).test().assertComplete()
    }

    @Test
    fun deleteSettings_shouldCompleteSuccessfully() {

        every { settingDaoMock.deleteSettings(testSettings) } returns Completable.complete()

        settingsRepoImpl.deleteSettings(testSettings).test().assertComplete()
    }
}