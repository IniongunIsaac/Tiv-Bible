package com.iniongun.tivbible.roomdb.repositoryImpl.chapter

import com.iniongun.tivbible.entities.Chapter
import com.iniongun.tivbible.repository.room.chapter.IChaptersRoom
import com.iniongun.tivbible.roomdb.dao.ChapterDao
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class ChaptersRoomImpl @Inject constructor(
    private val chapterDao: ChapterDao
): IChaptersRoom {

    override fun getAllChapters() = chapterDao.getAllChapters()

    override fun getChaptersByBook(bookId: String) = chapterDao.getChaptersByBook(bookId)

    override fun getChaptersByBookAndChapterNumber(
        bookId: String,
        chapterNumber: String
    ) = chapterDao.getChaptersByBookAndChapterNumber(bookId, chapterNumber)

    override fun insertChapters(chapters: List<Chapter>) = chapterDao.insertChapters(chapters)

    override fun deleteChapters(chapters: List<Chapter>) = chapterDao.deleteChapters(chapters)

}