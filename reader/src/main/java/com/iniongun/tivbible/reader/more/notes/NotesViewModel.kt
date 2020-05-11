package com.iniongun.tivbible.reader.more.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.rxScheduler.SchedulerProvider
import com.iniongun.tivbible.common.utils.rxScheduler.subscribeOnIoObserveOnUi
import com.iniongun.tivbible.entities.Setting
import com.iniongun.tivbible.repository.preference.IAppPreferencesRepo
import com.iniongun.tivbible.repository.room.settings.ISettingsRepo
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 10/05/2020.
 * For Tiv Bible project.
 */

class NotesViewModel @Inject constructor(
    private val settingsRepo: ISettingsRepo,
    private val appPreferencesRepo: IAppPreferencesRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _settings = MutableLiveData<Setting>()
    val settings: LiveData<Setting> = _settings

    init {
        getSettings()
    }

    fun getSettings() {
        postLoadingState()
        compositeDisposable.add(
            settingsRepo.getAllSettings()
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    _settings.value = it.first()
                    removeLoadingState()
                }) { removeLoadingState() }
        )
    }

    override fun handleCoroutineException(throwable: Throwable) {
        postFailureNotification(throwable.message)
    }

}