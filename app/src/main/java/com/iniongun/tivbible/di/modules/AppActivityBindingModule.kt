package com.iniongun.tivbible.di.modules

import com.iniongun.tivbible.di.scopes.PerActivity
import com.iniongun.tivbible.presentation.splash.SplashActivity
import com.iniongun.tivbible.reader.home.HomeActivity
import com.iniongungroup.mobile.android.references.ReferencesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Isaac Iniongun on 2019-11-26
 * For Tiv Bible project
 */

@Module
abstract class AppActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity

    @PerActivity
    @ContributesAndroidInjector
    internal abstract fun bindHomeActivity(): HomeActivity

    @PerActivity
    @ContributesAndroidInjector
    internal abstract fun bindReferencesActivity(): ReferencesActivity

}