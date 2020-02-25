package com.iniongun.tivbible.reader.home

import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEvent
import com.iniongun.tivbible.common.utils.state.AppResult
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-12-31.
 * For Tiv Bible project.
 */

class HomeActivityViewModel @Inject constructor(): BaseViewModel() {

    override fun handleCoroutineException(throwable: Throwable) {
        _notificationLiveData.postValue(LiveDataEvent(AppResult.failed(throwable.message)))
    }
}