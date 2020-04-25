package com.iniongun.tivbible.repository.room.verse

import com.iniongun.tivbible.entities.Verse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-24
 * For Tiv Bible project
 */

interface IVersesRepo {

    fun addVerses(verses: List<Verse>): Completable

    fun getAllVerses(): Observable<List<Verse>>

    fun getVerseById(verseId: String): Single<Verse>

    fun getVersesByBook(bookId: String): Observable<List<Verse>>

    fun getVersesByText(searchText: String): Observable<List<Verse>>

    fun getVersesByChapter(chapterId: String): Observable<List<Verse>>

    fun deleteVerses(verses: List<Verse>): Completable

}