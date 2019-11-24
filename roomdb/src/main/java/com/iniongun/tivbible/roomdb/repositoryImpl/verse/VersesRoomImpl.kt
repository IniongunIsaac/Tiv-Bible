package com.iniongun.tivbible.roomdb.repositoryImpl.verse

import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.repository.room.verse.IVersesRoom
import com.iniongun.tivbible.roomdb.dao.VerseDao
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-24
 * For Tiv Bible project
 */

class VersesRoomImpl @Inject constructor(
    private val verseDao: VerseDao
): IVersesRoom {

    override fun addVerses(verses: List<Verse>) = verseDao.insertVerses(verses)

    override fun getAllVerses() = verseDao.getAllVerses()

    override fun getVerseById(verseId: String) = verseDao.getVerseById(verseId)

    override fun getVersesByText(searchText: String) = verseDao.getVersesByText(searchText)

    override fun getVersesByChapter(chapterId: String) = verseDao.getVersesByChapter(chapterId)

    override fun deleteVerses(verses: List<Verse>) = verseDao.deleteVerses(verses)

}