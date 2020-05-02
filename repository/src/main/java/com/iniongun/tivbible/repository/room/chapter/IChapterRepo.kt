package com.iniongun.tivbible.repository.room.chapter

import com.iniongun.tivbible.entities.Chapter
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface IChapterRepo {

    fun getAllChapters(): Observable<List<Chapter>>

    fun getChaptersByBook(bookId: String): Observable<List<Chapter>>

    fun getChapterByBookAndChapterNumber(bookId: String, chapterNumber: Int): Single<Chapter>

    fun insertChapters(chapters: List<Chapter>): Completable

    fun deleteChapters(chapters: List<Chapter>): Completable

}