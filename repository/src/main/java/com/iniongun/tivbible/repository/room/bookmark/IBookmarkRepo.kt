package com.iniongun.tivbible.repository.room.bookmark

import com.iniongun.tivbible.entities.Bookmark
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface IBookmarkRepo {

    fun getBookmarks(): Observable<List<Bookmark>>

    fun getBookmarkByDate(bookmarkedOn: String): Observable<List<Bookmark>>

    fun getBookmarkByVerse(verseId: String): Single<Bookmark>

    fun insertBookmarks(bookmarks: List<Bookmark>): Completable

    fun deleteBookmarks(bookmarks: List<Bookmark>): Completable

}