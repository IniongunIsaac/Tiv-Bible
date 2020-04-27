package com.iniongun.tivbible.common.utils

import android.content.res.Configuration
import android.content.res.Resources
import com.iniongun.tivbible.common.utils.ScreenSize.*

/**
 * Created by Isaac Iniongun on 27/04/2020.
 * For Tiv Bible project.
 */

enum class ScreenSize {
    SMALL, NORMAL, LARGE, XLARGE, UNDEFINED
}

fun getDeviceScreenSize(resources: Resources): ScreenSize {
    // Thanks to https://stackoverflow.com/a/5016350/2563009.
    val screenLayout = resources.configuration.screenLayout
    return when {
        screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_SMALL -> SMALL
        screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_NORMAL -> NORMAL
        screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE -> LARGE
        screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_XLARGE -> XLARGE
        screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_UNDEFINED -> UNDEFINED
        else -> error("Unknown screenLayout: $screenLayout")
    }
}