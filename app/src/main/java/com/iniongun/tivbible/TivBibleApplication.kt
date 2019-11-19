package com.iniongun.tivbible

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

class TivBibleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

}