package com.iniongun.tivbible.common.utils.liveDataEvent

import androidx.lifecycle.Observer

/**
 * Created by Isaac Iniongun on 2019-12-01.
 * For Tiv Bible project.
 */

/**
 * An [Observer] for [LiveDataEvent]s, simplifying the pattern of checking if the [LiveDataEvent]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [LiveDataEvent]'s contents has not been handled.
 */
class LiveDataEventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<LiveDataEvent<T>> {
    override fun onChanged(event: LiveDataEvent<T>?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}