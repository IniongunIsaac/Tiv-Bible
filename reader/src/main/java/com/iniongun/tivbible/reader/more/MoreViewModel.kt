package com.iniongun.tivbible.reader.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.Constants.ABOUT_TITLE
import com.iniongun.tivbible.common.utils.Constants.COMMANDMENTS_TITLE
import com.iniongun.tivbible.common.utils.Constants.CREED_TITLE
import com.iniongun.tivbible.common.utils.Constants.LORDS_PRAYER_TITLE
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEvent
import com.iniongun.tivbible.common.utils.rxScheduler.SchedulerProvider
import com.iniongun.tivbible.common.utils.rxScheduler.subscribeOnIoObserveOnUi
import com.iniongun.tivbible.common.utils.state.AppResult
import com.iniongun.tivbible.entities.Other
import com.iniongun.tivbible.entities.Setting
import com.iniongun.tivbible.reader.utils.MoreItem
import com.iniongun.tivbible.reader.utils.MoreItemType.*
import com.iniongun.tivbible.repository.room.other.IOtherRepo
import com.iniongun.tivbible.repository.room.settings.ISettingsRepo
import javax.inject.Inject

class MoreViewModel @Inject constructor(
    private val settingsRepo: ISettingsRepo,
    private val otherRepo: IOtherRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _itemSelected = MutableLiveData<LiveDataEvent<MoreItem>>()
    val itemSelected: LiveData<LiveDataEvent<MoreItem>> = _itemSelected

    private var _settings = MutableLiveData<Setting>()
    val settings: LiveData<Setting> = _settings
    lateinit var currentSettings: Setting

    private val _other = MutableLiveData<Other>()
    val other: LiveData<Other> = _other

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

        when (moreItem.type) {
            CREED, COMMANDMENTS, LORDS_PRAYER, ABOUT -> { getOtherData(moreItem) }
            else -> {
                _itemSelected.value = LiveDataEvent(moreItem)
            }
        }
    }

    private fun getOtherData(moreItem: MoreItem) {
        when (moreItem.type) {
            CREED -> { getOtherDataFromStorage(CREED_TITLE, moreItem) }
            COMMANDMENTS -> { getOtherDataFromStorage(COMMANDMENTS_TITLE, moreItem) }
            LORDS_PRAYER -> { getOtherDataFromStorage(LORDS_PRAYER_TITLE, moreItem) }
            ABOUT -> { getOtherDataFromStorage(ABOUT_TITLE, moreItem) }
        }
    }

    private fun getOtherDataFromStorage(text: String, moreItem: MoreItem) {
        postLoadingState()
        compositeDisposable.add(
            otherRepo.getOtherByText(text)
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    _other.value = it
                    _itemSelected.value = LiveDataEvent(moreItem)
                }) {
                    removeLoadingState()
                }
        )
    }

    override fun handleCoroutineException(throwable: Throwable) {
        _notificationLiveData.postValue(LiveDataEvent(AppResult.failed(throwable.message)))
    }
}
