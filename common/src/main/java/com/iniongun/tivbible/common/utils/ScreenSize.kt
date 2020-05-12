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

fun getMinimumFontSizeForDevice(deviceScreenSize: ScreenSize): Int {
    return when(deviceScreenSize) {
        SMALL -> 12
        NORMAL, UNDEFINED -> 14
        LARGE -> 16
        XLARGE -> 18
    }
}

fun getMaximumFontSizeForDevice(deviceScreenSize: ScreenSize): Int {
    return when(deviceScreenSize) {
        SMALL -> 15
        NORMAL, UNDEFINED -> 17
        LARGE -> 19
        XLARGE -> 21
    }
}

fun getDeviceLineSpacingTwo(deviceScreenSize: ScreenSize): Int {
    return when(deviceScreenSize) {
        SMALL -> 7
        NORMAL, UNDEFINED -> 8
        LARGE -> 9
        XLARGE -> 10
    }
}

fun getDeviceLineSpacingThree(deviceScreenSize: ScreenSize): Int {
    return when(deviceScreenSize) {
        SMALL -> 8
        NORMAL, UNDEFINED -> 9
        LARGE -> 10
        XLARGE -> 11
    }
}

fun getDeviceLineSpacingFour(deviceScreenSize: ScreenSize): Int {
    return when(deviceScreenSize) {
        SMALL -> 9
        NORMAL, UNDEFINED -> 10
        LARGE -> 11
        XLARGE -> 12
    }
}