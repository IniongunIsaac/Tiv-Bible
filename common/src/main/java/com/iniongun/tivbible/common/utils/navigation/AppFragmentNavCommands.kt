package com.iniongun.tivbible.common.utils.navigation

import androidx.annotation.IdRes
import androidx.navigation.NavDirections

/**
 * Created by Isaac Iniongun on 2019-11-26
 * For Tiv Bible project
 */

sealed class AppFragmentNavCommands {
    data class To(val direction: NavDirections): AppFragmentNavCommands()
    object Back: AppFragmentNavCommands()
    data class BackTo(@IdRes val destinationId: Int): AppFragmentNavCommands()
    object BackToRoot: AppFragmentNavCommands()
}