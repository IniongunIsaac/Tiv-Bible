package com.iniongun.tivbible.reader.more

import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEvent
import com.iniongun.tivbible.common.utils.state.AppResult
import javax.inject.Inject

class MoreViewModel @Inject constructor() : BaseViewModel() {
    override fun handleCoroutineException(throwable: Throwable) {
        _notificationLiveData.postValue(LiveDataEvent(AppResult.failed(throwable.message)))
    }
}
