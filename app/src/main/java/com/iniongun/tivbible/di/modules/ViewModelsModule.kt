package com.iniongun.tivbible.di.modules

import androidx.lifecycle.ViewModelProvider
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.di.keys.AppViewModelKey
import com.iniongun.tivbible.presentation.splash.SplashActivityViewModel
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

}