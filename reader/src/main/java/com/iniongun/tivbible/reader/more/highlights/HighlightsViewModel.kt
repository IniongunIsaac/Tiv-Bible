package com.iniongun.tivbible.reader.more.highlights

import com.iniongun.tivbible.common.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 09/05/2020.
 * For Tiv Bible project.
 */

class HighlightsViewModel @Inject constructor() : BaseViewModel() {

    override fun handleCoroutineException(throwable: Throwable) {
        postFailureNotification(throwable.message)
    }

}