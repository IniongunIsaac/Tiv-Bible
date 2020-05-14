package com.iniongun.tivbible.di.modules

import com.iniongun.tivbible.di.scopes.PerFragment
import com.iniongun.tivbible.reader.more.MoreFragment
import com.iniongun.tivbible.reader.more.bookmarks.BookmarksFragment
import com.iniongun.tivbible.reader.more.help.HelpFragment
import com.iniongun.tivbible.reader.more.highlights.HighlightsFragment
import com.iniongun.tivbible.reader.more.miscContent.MiscContentFragment
import com.iniongun.tivbible.reader.more.notes.NoteDetailsFragment
import com.iniongun.tivbible.reader.more.notes.NotesFragment
import com.iniongun.tivbible.reader.more.settings.SettingsFragment
import com.iniongun.tivbible.reader.read.ReadFragment
import com.iniongun.tivbible.reader.read.ReadFragmentNew
import com.iniongun.tivbible.reader.search.SearchFragment
import com.iniongun.tivbible.reader.search.SearchResultsFragment
import com.iniongungroup.mobile.android.references.fragments.BooksFragment
import com.iniongungroup.mobile.android.references.fragments.ChaptersFragment
import com.iniongungroup.mobile.android.references.fragments.VersesFragment
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
    internal abstract fun bindReadFragmentNew(): ReadFragmentNew

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindSearchFragment(): SearchFragment

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindSettingsFragment(): SettingsFragment

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindBooksFragment(): BooksFragment

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindChaptersFragment(): ChaptersFragment

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindVersesFragment(): VersesFragment

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindSearchResultsFragment(): SearchResultsFragment

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindBookmarksFragment(): BookmarksFragment

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindHighlightsFragment(): HighlightsFragment

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindMiscContentFragment(): MiscContentFragment

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindNotesFragment(): NotesFragment

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindNoteDetailsFragment(): NoteDetailsFragment

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindHelpFragment(): HelpFragment

}