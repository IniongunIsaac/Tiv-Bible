package com.iniongun.tivbible.roomdb.repositoryImpl.chapter

import com.iniongun.tivbible.entities.Chapter
import com.iniongun.tivbible.repository.room.chapter.IChapterRepo
import com.iniongun.tivbible.roomdb.dao.ChapterDao
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class ChapterRepoImpl @Inject constructor(
    private val chapterDao: ChapterDao
): IChapterRepo {

    override fun getAllChapters() = chapterDao.getAllChapters()

    override fun getChaptersByBook(bookId: String) = chapterDao.getChaptersByBook(bookId)

    override fun getChapterByBookAndChapterNumber(
        bookId: String,
        chapterNumber: Int
    ) = chapterDao.getChapterByBookAndChapterNumber(bookId, chapterNumber)

    override fun insertChapters(chapters: List<Chapter>) = chapterDao.insertChapters(chapters)

    override fun deleteChapters(chapters: List<Chapter>) = chapterDao.deleteChapters(chapters)

}