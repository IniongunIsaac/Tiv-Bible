package com.iniongun.tivbible.reader.more.notes

import com.iniongun.tivbible.common.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 10/05/2020.
 * For Tiv Bible project.
 */

class NotesViewModel @Inject constructor() : BaseViewModel() {

    override fun handleCoroutineException(throwable: Throwable) {
        postFailureNotification(throwable.message)
    }

}