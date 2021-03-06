package com.iniongun.tivbible

import com.iniongun.tivbible.di.component.AppComponent
import com.iniongun.tivbible.di.component.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

class TivBibleApplication : DaggerApplication() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)

        AndroidThreeTen.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        appComponent = DaggerAppComponent.builder().bindAppInstance(this).buildAppComponent()

        return appComponent

    }
}