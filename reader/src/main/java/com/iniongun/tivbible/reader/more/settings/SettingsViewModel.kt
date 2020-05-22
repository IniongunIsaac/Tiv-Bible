package com.iniongun.tivbible.reader.more.settings

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.*
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEvent
import com.iniongun.tivbible.common.utils.rxScheduler.SchedulerProvider
import com.iniongun.tivbible.common.utils.rxScheduler.subscribeOnIoObserveOnUi
import com.iniongun.tivbible.common.utils.state.AppResult
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongun.tivbible.common.utils.theme.ThemeConstants.*
import com.iniongun.tivbible.common.utils.theme.ThemeHelper
import com.iniongun.tivbible.entities.AudioSpeed
import com.iniongun.tivbible.entities.FontStyle
import com.iniongun.tivbible.entities.Setting
import com.iniongun.tivbible.entities.Theme
import com.iniongun.tivbible.reader.utils.LineSpacingType
import com.iniongun.tivbible.reader.utils.LineSpacingType.*
import com.iniongun.tivbible.repository.room.audioSpeed.IAudioSpeedRepo
import com.iniongun.tivbible.repository.room.fontStyle.IFontStyleRepo
import com.iniongun.tivbible.repository.room.settings.ISettingsRepo
import com.iniongun.tivbible.repository.room.theme.IThemeRepo
import io.reactivex.Observable
import io.reactivex.functions.Function4
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val context: Context,
    private val schedulerProvider: SchedulerProvider,
    private val settingsRepo: ISettingsRepo,
    private val fontStyleRepo: IFontStyleRepo,
    private val themeRepo: IThemeRepo,
    private val audioSpeedRepo: IAudioSpeedRepo
) : BaseViewModel() {

    private val _settings = MutableLiveData<Setting>()
    val settings: LiveData<Setting> = _settings

    private val _fontStyles = MutableLiveData<List<FontStyle>>()
    val fontStyles: LiveData<List<FontStyle>> = _fontStyles

    private val _themes = MutableLiveData<List<Theme>>()
    val themes: LiveData<List<Theme>> = _themes

    private val _audioSpeeds = MutableLiveData<List<AudioSpeed>>()
    val audioSpeeds: LiveData<List<AudioSpeed>> = _audioSpeeds

    lateinit var currentSettings: Setting

    private val deviceScreenSize by lazy { getDeviceScreenSize(context.resources) }

    private val _shouldEnableFontSettingsUIControls = MutableLiveData<Boolean>()
    val shouldEnableFontSettingsUIControls: LiveData<Boolean> = _shouldEnableFontSettingsUIControls

    init {
        getSettingsAndFontStylesAndThemes()
    }

    private fun getSettingsAndFontStylesAndThemes() {
        postLoadingState()
        compositeDisposable.add(
            Observable.zip(
                settingsRepo.getAllSettings(),
                fontStyleRepo.getAllFontStyles(),
                themeRepo.getAllThemes(),
                audioSpeedRepo.getAllAudioSpeeds(),
                Function4<List<Setting>, List<FontStyle>, List<Theme>, List<AudioSpeed>, SettingsData> { sttngs, fnts, thms, audSpds -> SettingsData(sttngs, fnts, thms, audSpds) }
            ).subscribeOnIoObserveOnUi(schedulerProvider, {
                removeLoadingState()
                val settings = it.settings.first()
                currentSettings = settings
                _settings.value = settings
                _fontStyles.value = it.fontStyles
                _themes.value = it.themes
                _audioSpeeds.value = it.audioSpeeds
            }) {
                removeLoadingState()
            }
        )
    }

    private fun getUserSettings() {
        postLoadingState()
        compositeDisposable.add(
            settingsRepo.getAllSettings().subscribeOnIoObserveOnUi(schedulerProvider, {
                removeLoadingState()
                with(it.first()) {
                    currentSettings = this
                    _settings.value = this
                    val currentTheme = when(theme.name) {
                        LIGHT.name -> LIGHT
                        DARK.name -> DARK
                        BATTERY_SAVER.name -> BATTERY_SAVER
                        else -> SYSTEM_DEFAULT
                    }
                    ThemeHelper.changeTheme(currentTheme)
                }
            })
        )
    }

    private fun updateUserSettings() {
        postLoadingState()
        _shouldEnableFontSettingsUIControls.value = false
        compositeDisposable.add(
            settingsRepo.updateSetting(currentSettings)
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    _shouldEnableFontSettingsUIControls.value = true
                    getUserSettings()
                })
        )
    }

    fun changeFontStyle(fontStyle: FontStyle) {
        currentSettings.fontStyle = fontStyle
        updateUserSettings()
    }

    fun changeTheme(theme: Theme) {
        currentSettings.theme = theme
        updateUserSettings()
    }

    fun changeAudioSpeed(audioSpeed: AudioSpeed) {
        currentSettings.audioSpeed = audioSpeed
        updateUserSettings()
    }

    fun changeStayAwakeSetting(shouldStayAwake: Boolean) {
        currentSettings.stayAwake = shouldStayAwake
        updateUserSettings()
    }

    fun increaseFontSize() {
        val maximumFontSizeForDevice = getMaximumFontSizeForDevice(deviceScreenSize)
        if (currentSettings.fontSize == maximumFontSizeForDevice)
            setMessage("Maximum font size for your device is ${maximumFontSizeForDevice}px",
                AppState.FAILED
            )
        else {
            ++currentSettings.fontSize
            updateUserSettings()
        }
    }

    fun decreaseFontSize() {
        val minimumFontSizeForDevice = getMinimumFontSizeForDevice(deviceScreenSize)
        if (currentSettings.fontSize == minimumFontSizeForDevice)
            setMessage("Minimum font size for your device is ${minimumFontSizeForDevice}px",
                AppState.FAILED
            )
        else {
            --currentSettings.fontSize
            updateUserSettings()
        }
    }

    fun updateLineSpacing(lineSpacingType: LineSpacingType) {
        val lineSpacing = when(lineSpacingType) {
            TWO -> getDeviceLineSpacingTwo(deviceScreenSize)
            THREE -> getDeviceLineSpacingThree(deviceScreenSize)
            FOUR -> getDeviceLineSpacingFour(deviceScreenSize)
        }

        currentSettings.lineSpacing = lineSpacing
        updateUserSettings()
    }

    override fun handleCoroutineException(throwable: Throwable) {
        _notificationLiveData.postValue(LiveDataEvent(AppResult.failed(throwable.message)))
    }
}

data class SettingsData(val settings: List<Setting>, val fontStyles: List<FontStyle>, val themes: List<Theme>, val audioSpeeds: List<AudioSpeed>)