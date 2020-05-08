package com.iniongun.tivbible.roomdb.repositoryImpl.verse

import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.repository.room.verse.IVersesRepo
import com.iniongun.tivbible.roomdb.dao.VerseDao
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-24
 * For Tiv Bible project
 */

class VersesRepoImpl @Inject constructor(
    private val verseDao: VerseDao
): IVersesRepo {

    override fun addVerses(verses: List<Verse>) = verseDao.insertVerses(verses)

    override fun getAllVerses() = verseDao.getAllVerses()

    override fun getVerseById(verseId: String) = verseDao.getVerseById(verseId)

    override fun getVersesByBook(bookId: String) = verseDao.getVersesByBook(bookId)

    override fun getVersesByText(searchText: String) = verseDao.getVersesByText(searchText)

    override fun getVersesByChapter(chapterId: String) = verseDao.getVersesByChapter(chapterId)

    override fun getVersesByTextAndChapter(searchText: String, chapterId: String) = verseDao.getVersesByTextAndChapter(searchText, chapterId)

    override fun deleteVerses(verses: List<Verse>) = verseDao.deleteVerses(verses)

    override fun getBooksAndChaptersAndVersesByText(searchText: String) = verseDao.getBooksAndChaptersAndVersesByText(searchText)

    override fun getBooksAndChaptersAndVersesByTextAndChapter(
        searchText: String,
        chapterId: String
    ) = verseDao.getBooksAndChaptersAndVersesByTextAndChapter(searchText, chapterId)

}