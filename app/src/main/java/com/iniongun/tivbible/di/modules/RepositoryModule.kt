package com.iniongun.tivbible.di.modules

import com.iniongun.tivbible.di.scopes.AppScope
import com.iniongun.tivbible.preferences.repostoryImpl.AppPreferencesRepoImpl
import com.iniongun.tivbible.repository.preference.IAppPreferencesRepo
import com.iniongun.tivbible.repository.room.audioSpeed.IAudioSpeedRepo
import com.iniongun.tivbible.repository.room.book.IBookRepo
import com.iniongun.tivbible.repository.room.bookmark.IBookmarkRepo
import com.iniongun.tivbible.repository.room.chapter.IChapterRepo
import com.iniongun.tivbible.repository.room.fontStyle.IFontStyleRepo
import com.iniongun.tivbible.repository.room.highlight.IHighlightRepo
import com.iniongun.tivbible.repository.room.highlightColor.IHighlightColorRepo
import com.iniongun.tivbible.repository.room.history.IHistoryRepo
import com.iniongun.tivbible.repository.room.other.IOtherRepo
import com.iniongun.tivbible.repository.room.otherType.IOtherTypeRepo
import com.iniongun.tivbible.repository.room.recentSearch.IRecentSearchRepo
import com.iniongun.tivbible.repository.room.settings.ISettingsRepo
import com.iniongun.tivbible.repository.room.testament.ITestamentRepo
import com.iniongun.tivbible.repository.room.theme.IThemeRepo
import com.iniongun.tivbible.repository.room.verse.IVersesRepo
import com.iniongun.tivbible.repository.room.version.IVersionRepo
import com.iniongun.tivbible.roomdb.repositoryImpl.audioSpeed.AudioSpeedRepoImpl
import com.iniongun.tivbible.roomdb.repositoryImpl.book.BookRepoImpl
import com.iniongun.tivbible.roomdb.repositoryImpl.bookmark.BookmarkRepoImpl
import com.iniongun.tivbible.roomdb.repositoryImpl.chapter.ChapterRepoImpl
import com.iniongun.tivbible.roomdb.repositoryImpl.fontStyle.FontStyleRepoImpl
import com.iniongun.tivbible.roomdb.repositoryImpl.highlight.HighlightRepoImpl
import com.iniongun.tivbible.roomdb.repositoryImpl.highlightColor.HighlightColorRepoImpl
import com.iniongun.tivbible.roomdb.repositoryImpl.history.HistoryRepoImpl
import com.iniongun.tivbible.roomdb.repositoryImpl.other.OtherRepoImpl
import com.iniongun.tivbible.roomdb.repositoryImpl.otherType.OtherTypeRepoImpl
import com.iniongun.tivbible.roomdb.repositoryImpl.recentSearch.RecentSearchRepoImpl
import com.iniongun.tivbible.roomdb.repositoryImpl.settings.SettingsRepoImpl
import com.iniongun.tivbible.roomdb.repositoryImpl.testament.TestamentRepoImpl
import com.iniongun.tivbible.roomdb.repositoryImpl.theme.ThemeRepoImpl
import com.iniongun.tivbible.roomdb.repositoryImpl.verse.VersesRepoImpl
import com.iniongun.tivbible.roomdb.repositoryImpl.version.VersionRepoImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

@Module
abstract class RepositoryModule {

    @Binds
    @AppScope
    internal abstract fun bindAppPreference(
        preference: AppPreferencesRepoImpl
    ): IAppPreferencesRepo

    @Binds
    @AppScope
    internal abstract fun bindAudioSpeedRepo(
        repo: AudioSpeedRepoImpl
    ): IAudioSpeedRepo

    @Binds
    @AppScope
    internal abstract fun bindBookRepo(
        repo: BookRepoImpl
    ): IBookRepo

    @Binds
    @AppScope
    internal abstract fun bindBookmarkRepo(
        repo: BookmarkRepoImpl
    ): IBookmarkRepo

    @Binds
    @AppScope
    internal abstract fun bindChapterRepo(
        repo: ChapterRepoImpl
    ): IChapterRepo

    @Binds
    @AppScope
    internal abstract fun bindFontStyleRepo(
        repo: FontStyleRepoImpl
    ): IFontStyleRepo

    @Binds
    @AppScope
    internal abstract fun bindHighlightRepo(
        repo: HighlightRepoImpl
    ): IHighlightRepo

    @Binds
    @AppScope
    internal abstract fun bindHighlightColorRepo(
        repo: HighlightColorRepoImpl
    ): IHighlightColorRepo

    @Binds
    @AppScope
    internal abstract fun bindHistoryRepo(
        repo: HistoryRepoImpl
    ): IHistoryRepo

    @Binds
    @AppScope
    internal abstract fun bindOtherRepo(
        repo: OtherRepoImpl
    ): IOtherRepo

    @Binds
    @AppScope
    internal abstract fun bindOtherTypeRepo(
        repo: OtherTypeRepoImpl
    ): IOtherTypeRepo

    @Binds
    @AppScope
    internal abstract fun bindRecentSearchRepo(
        repo: RecentSearchRepoImpl
    ): IRecentSearchRepo

    @Binds
    @AppScope
    internal abstract fun bindSettingsRepo(
        repo: SettingsRepoImpl
    ): ISettingsRepo

    @Binds
    @AppScope
    internal abstract fun bindTestamentRepo(
        repo: TestamentRepoImpl
    ): ITestamentRepo

    @Binds
    @AppScope
    internal abstract fun bindThemeRepo(
        repo: ThemeRepoImpl
    ): IThemeRepo

    @Binds
    @AppScope
    internal abstract fun bindVerseRepo(
        repo: VersesRepoImpl
    ): IVersesRepo

    @Binds
    @AppScope
    internal abstract fun bindVersionRepo(
        repo: VersionRepoImpl
    ): IVersionRepo
}