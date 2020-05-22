package com.iniongun.tivbible.roomdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.iniongun.tivbible.entities.*
import com.iniongun.tivbible.roomdb.converters.BooleanConverter
import com.iniongun.tivbible.roomdb.converters.DateTimeTypeConverter
import com.iniongun.tivbible.roomdb.converters.VerseConverter
import com.iniongun.tivbible.roomdb.dao.*
import com.iniongun.tivbible.roomdb.utils.LocalDBConstants

/**
 * Created by Isaac Iniongun on 2019-11-24
 * For Tiv Bible project
 */

@Database(
    entities = [
        AudioSpeed::class,
        Book::class,
        Bookmark::class,
        Chapter::class,
        FontStyle::class,
        HighlightColor::class,
        Highlight::class,
        History::class,
        Other::class,
        OtherType::class,
        RecentSearch::class,
        Setting::class,
        Testament::class,
        Theme::class,
        Verse::class,
        Version::class,
        Note::class
    ],
    version = LocalDBConstants.DB_VERSION
)

@TypeConverters(BooleanConverter::class, DateTimeTypeConverter::class, VerseConverter::class)

abstract class TivBibleDatabase : RoomDatabase() {

    abstract fun audioSpeedDao(): AudioSpeedDao

    abstract fun bookDao(): BookDao

    abstract fun bookmarkDao(): BookmarkDao

    abstract fun chapterDao(): ChapterDao

    abstract fun fontStyleDao(): FontStyleDao

    abstract fun highlightColorDao(): HighlightColorDao

    abstract fun highlightDao(): HighlightDao

    abstract fun historyDao(): HistoryDao

    abstract fun otherDao(): OtherDao

    abstract fun otherTypeDao(): OtherTypeDao

    abstract fun recentSearchDao(): RecentSearchDao

    abstract fun settingDao(): SettingDao

    abstract fun testamentDao(): TestamentDao

    abstract fun themeDao(): ThemeDao

    abstract fun verseDao(): VerseDao

    abstract fun versionDao(): VersionDao

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: TivBibleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            TivBibleDatabase::class.java, LocalDBConstants.DB_NAME
        ).build()
    }

}