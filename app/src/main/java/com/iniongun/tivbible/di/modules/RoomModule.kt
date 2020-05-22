package com.iniongun.tivbible.di.modules

import android.content.Context
import com.iniongun.tivbible.di.scopes.AppScope
import com.iniongun.tivbible.roomdb.database.TivBibleDatabase
import dagger.Module
import dagger.Provides

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

@Module
class RoomModule {

    @AppScope
    @Provides
    fun provideTivBibleRoomDatabase(context: Context) = TivBibleDatabase(context)

//    @AppScope
//    @Provides
//    fun provideTivBibleRoomDatabase(context: Context) = Room.databaseBuilder(
//        context,
//        TivBibleDatabase::class.java, DB_NAME
//    ).build()

    @AppScope
    @Provides
    fun provideAudioSpeedDao(db: TivBibleDatabase) = db.audioSpeedDao()

    @AppScope
    @Provides
    fun provideBookDao(db: TivBibleDatabase) = db.bookDao()

    @AppScope
    @Provides
    fun provideBookmarkDao(db: TivBibleDatabase) = db.bookmarkDao()

    @AppScope
    @Provides
    fun provideChapterDao(db: TivBibleDatabase) = db.chapterDao()

    @AppScope
    @Provides
    fun provideFontStyleDao(db: TivBibleDatabase) = db.fontStyleDao()

    @AppScope
    @Provides
    fun provideHighlightColorDao(db: TivBibleDatabase) = db.highlightColorDao()

    @AppScope
    @Provides
    fun provideHighlightDao(db: TivBibleDatabase) = db.highlightDao()

    @AppScope
    @Provides
    fun provideHistoryDao(db: TivBibleDatabase) = db.historyDao()

    @AppScope
    @Provides
    fun provideOtherDao(db: TivBibleDatabase) = db.otherDao()

    @AppScope
    @Provides
    fun provideOtherTypeDao(db: TivBibleDatabase) = db.otherTypeDao()

    @AppScope
    @Provides
    fun provideRecentSearchDao(db: TivBibleDatabase) = db.recentSearchDao()

    @AppScope
    @Provides
    fun provideSettingsDao(db: TivBibleDatabase) = db.settingDao()

    @AppScope
    @Provides
    fun provideTestamentDao(db: TivBibleDatabase) = db.testamentDao()

    @AppScope
    @Provides
    fun provideThemeDao(db: TivBibleDatabase) = db.themeDao()

    @AppScope
    @Provides
    fun provideVerseDao(db: TivBibleDatabase) = db.verseDao()

    @AppScope
    @Provides
    fun provideVersionDao(db: TivBibleDatabase) = db.versionDao()

    @AppScope
    @Provides
    fun provideNoteDao(db: TivBibleDatabase) = db.noteDao()

}