package com.iniongun.tivbible.roomdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.Verse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

@Dao
interface VerseDao {

    @Query("select * from Verse")
    fun getAllVerses(): Observable<List<Verse>>

    @Query("select * from Verse where id = :verseId limit 1")
    fun getVerseById(verseId: String): Single<Verse>

    @Query("select * from Verse where text like '%' || :searchText || '%'")
    fun getVersesByText(searchText: String): Observable<List<Verse>>

    @Query("select * from Verse where chapter_id = :chapterId order by number asc")
    fun getVersesByChapter(chapterId: String): Observable<List<Verse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVerses(vararg verses: Verse): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVerses(verses: List<Verse>): Completable

    @Delete
    fun deleteVerses(verses: List<Verse>): Single<Int>

}