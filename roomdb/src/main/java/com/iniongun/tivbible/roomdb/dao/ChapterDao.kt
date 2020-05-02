package com.iniongun.tivbible.roomdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.Chapter
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

@Dao
interface ChapterDao {

    @Query("select * from Chapter")
    fun getAllChapters(): Observable<List<Chapter>>

    @Query("select * from Chapter where id = :chapterId ")
    fun getChapterById(chapterId: String): Maybe<Chapter>

    @Query("select * from Chapter where book_id = :bookId ")
    fun getChaptersByBook(bookId: String): Observable<List<Chapter>>

    @Query("select * from Chapter where book_id = :bookId and chapter_number = :chapterNumber")
    fun getChapterByBookAndChapterNumber(bookId: String, chapterNumber: Int): Single<Chapter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChapters(vararg chapters: Chapter): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChapters(chapters: List<Chapter>): Completable

    @Delete
    fun deleteChapters(chapters: List<Chapter>): Completable

    @Query("delete from Chapter")
    fun deleteAllChapters(): Completable

}