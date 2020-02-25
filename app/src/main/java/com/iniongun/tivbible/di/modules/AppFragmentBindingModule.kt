package com.iniongun.tivbible.di.modules

import com.iniongun.tivbible.di.scopes.PerFragment
import com.iniongun.tivbible.reader.more.MoreFragment
import com.iniongun.tivbible.reader.read.ReadFragment
import com.iniongun.tivbible.reader.search.SearchFragment
import com.iniongun.tivbible.reader.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Isaac Iniongun on 2019-12-31.
 * For Tiv Bible project.
 */

@Module
abstract class AppFragmentBindingModule {

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindMoreFragment(): MoreFragment

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindReadFragment(): ReadFragment

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindSearchFragment(): SearchFragment

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindSettingsFragment(): SettingsFragment

}