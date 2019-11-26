package com.iniongun.tivbible.common.utils.navigation

import android.content.Context
import android.content.Intent

/**
 * Created by Isaac Iniongun on 2019-11-26
 * For Tiv Bible project
 */

object AppActivityNavCommands {



    private fun navigationIntent(context: Context, navAction: String) = Intent(navAction).setPackage(context.packageName)

}