package com.iniongun.tivbible.repository.preference

import com.iniongun.tivbible.entities.Book
import com.iniongun.tivbible.entities.Chapter
import com.iniongun.tivbible.entities.Verse

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface IAppPreferencesRepo {

    var isDBInitialized: Boolean

    var currentTheme: String

    var currentVerse: Verse

    var currentChapter: Chapter

    var currentBook: Book

    var currentVerseString: String

    var shouldReloadVerses: Boolean

}