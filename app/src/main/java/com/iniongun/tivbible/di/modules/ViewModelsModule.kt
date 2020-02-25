package com.iniongun.tivbible.di.modules

import androidx.lifecycle.ViewModelProvider
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.di.keys.AppViewModelKey
import com.iniongun.tivbible.presentation.splash.SplashActivityViewModel
import com.iniongun.tivbible.reader.home.HomeActivityViewModel
import com.iniongun.tivbible.reader.more.MoreViewModel
import com.iniongun.tivbible.reader.read.ReadViewModel
import com.iniongun.tivbible.reader.search.SearchViewModel
import com.iniongun.tivbible.reader.settings.SettingsViewModel
import com.iniongun.tivbible.viewModelFactory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

@Module
abstract class ViewModelsModule {

    @Binds
    internal abstract fun bindGokadaViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @AppViewModelKey(SplashActivityViewModel::class)
    abstract fun bindSplashActivityViewModel(
        viewModel: SplashActivityViewModel
    ): BaseViewModel

    @Binds
    @IntoMap
    @AppViewModelKey(HomeActivityViewModel::class)
    abstract fun bindHomeActivityViewModel(
        viewModel: HomeActivityViewModel
    ): BaseViewModel

    @Binds
    @IntoMap
    @AppViewModelKey(MoreViewModel::class)
    abstract fun bindMoreViewModel(
        viewModel: MoreViewModel
    ): BaseViewModel

    @Binds
    @IntoMap
    @AppViewModelKey(ReadViewModel::class)
    abstract fun bindReadViewModel(
        viewModel: ReadViewModel
    ): BaseViewModel

    @Binds
    @IntoMap
    @AppViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(
        viewModel: SearchViewModel
    ): BaseViewModel

    @Binds
    @IntoMap
    @AppViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(
        viewModel: SettingsViewModel
    ): BaseViewModel

}