package com.iniongun.tivbible.roomdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.Bookmark
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

@Dao
interface BookmarkDao {

    @Query("select * from Bookmark order by datetime(bookmarked_on) asc")
    fun getBookmarks(): Observable<List<Bookmark>>

    @Query("select * from Bookmark where bookmarked_on = :bookmarkedOn")
    fun getBookmarkByDate(bookmarkedOn: String): Observable<List<Bookmark>>

    @Query("select * from Bookmark where verse_id = :verseId limit 1")
    fun getBookmarkByVerse(verseId: String): Single<Bookmark>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmarks(bookmarks: List<Bookmark>): Maybe<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmarks(vararg bookmarks: Bookmark): Completable

    @Delete
    fun deleteBookmarks(bookmarks: List<Bookmark>): Single<Int>

}