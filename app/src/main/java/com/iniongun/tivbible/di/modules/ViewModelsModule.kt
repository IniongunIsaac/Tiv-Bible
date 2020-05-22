package com.iniongun.tivbible.di.modules

import androidx.lifecycle.ViewModelProvider
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.di.keys.AppViewModelKey
import com.iniongun.tivbible.presentation.splash.SplashActivityViewModel
import com.iniongun.tivbible.reader.home.HomeActivityViewModel
import com.iniongun.tivbible.reader.more.MoreViewModel
import com.iniongun.tivbible.reader.more.bookmarks.BookmarksViewModel
import com.iniongun.tivbible.reader.more.help.HelpViewModel
import com.iniongun.tivbible.reader.more.highlights.HighlightsViewModel
import com.iniongun.tivbible.reader.more.notes.NotesViewModel
import com.iniongun.tivbible.reader.more.settings.SettingsViewModel
import com.iniongun.tivbible.reader.read.ReadViewModel
import com.iniongun.tivbible.reader.read.ReadViewModelNew
import com.iniongun.tivbible.reader.search.SearchViewModel
import com.iniongun.tivbible.viewModelFactory.ViewModelFactory
import com.iniongungroup.mobile.android.references.ReferencesViewModel
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
    internal abstract fun bindViewModelFactory(
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
    @AppViewModelKey(ReadViewModelNew::class)
    abstract fun bindReadViewModelNew(
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

    @Binds
    @IntoMap
    @AppViewModelKey(ReferencesViewModel::class)
    abstract fun bindReferencesViewModel(
        viewModel: ReferencesViewModel
    ): BaseViewModel

    @Binds
    @IntoMap
    @AppViewModelKey(BookmarksViewModel::class)
    abstract fun bindBookmarksViewModel(
        viewModel: BookmarksViewModel
    ): BaseViewModel

    @Binds
    @IntoMap
    @AppViewModelKey(HighlightsViewModel::class)
    abstract fun bindHighlightsViewModel(
        viewModel: HighlightsViewModel
    ): BaseViewModel

    @Binds
    @IntoMap
    @AppViewModelKey(NotesViewModel::class)
    abstract fun bindNotesViewModel(
        viewModel: NotesViewModel
    ): BaseViewModel

    @Binds
    @IntoMap
    @AppViewModelKey(HelpViewModel::class)
    abstract fun bindHelpViewModel(
        viewModel: HelpViewModel
    ): BaseViewModel

}