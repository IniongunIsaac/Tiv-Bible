package com.iniongun.tivbible.reader.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEvent
import com.iniongun.tivbible.common.utils.rxScheduler.SchedulerProvider
import com.iniongun.tivbible.common.utils.rxScheduler.subscribeOnIoObserveOnUi
import com.iniongun.tivbible.common.utils.state.AppResult
import com.iniongun.tivbible.entities.Setting
import com.iniongun.tivbible.reader.utils.MoreItem
import com.iniongun.tivbible.repository.room.settings.ISettingsRepo
import javax.inject.Inject

class MoreViewModel @Inject constructor(
    private val settingsRepo: ISettingsRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _itemSelected = MutableLiveData<LiveDataEvent<MoreItem>>()
    val itemSelected: LiveData<LiveDataEvent<MoreItem>> = _itemSelected

    private var _settings = MutableLiveData<Setting>()
    val settings: LiveData<Setting> = _settings
    lateinit var currentSettings: Setting

    init {
        getUserSettings()
    }

    private fun getUserSettings() {
        postLoadingState()
        compositeDisposable.add(
            settingsRepo.getAllSettings().subscribeOnIoObserveOnUi(schedulerProvider, {
                removeLoadingState()
                val settings = it.first()
                currentSettings = settings
                _settings.value = settings
            })
        )
    }



    fun handleMoreItemClicked(moreItem: MoreItem) {
        _itemSelected.value = LiveDataEvent(moreItem)
    }

    override fun handleCoroutineException(throwable: Throwable) {
        _notificationLiveData.postValue(LiveDataEvent(AppResult.failed(throwable.message)))
    }
}
