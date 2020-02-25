package com.iniongun.tivbible.common.utils.navigation

import android.content.Context
import android.content.Intent
import com.iniongun.tivbible.common.R

/**
 * Created by Isaac Iniongun on 2019-11-26
 * For Tiv Bible project
 */

object AppActivityNavCommands {

    fun getHomeActivityIntent(context: Context) = navigationIntent(context, context.getString(R.string.reader_home_activity_intent))

    private fun navigationIntent(context: Context, navAction: String) = Intent(navAction).setPackage(context.packageName)

}