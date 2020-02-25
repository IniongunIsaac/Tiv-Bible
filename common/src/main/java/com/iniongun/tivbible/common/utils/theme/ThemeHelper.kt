package com.iniongun.tivbible.common.utils.theme

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.iniongun.tivbible.common.utils.theme.ThemeConstants.*

/**
 * Created by Isaac Iniongun on 2020-01-02.
 * For Tiv Bible project.
 */

object ThemeHelper {

    fun changeTheme(themeConstant: ThemeConstants) {
        when (themeConstant) {
            LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            BATTERY_SAVER -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            SYSTEM_DEFAULT -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }


        }
    }

}