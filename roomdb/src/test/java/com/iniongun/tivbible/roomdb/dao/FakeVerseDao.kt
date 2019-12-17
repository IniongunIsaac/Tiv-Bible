package com.iniongun.tivbible.roomdb.dao

import com.iniongun.tivbible.entities.Verse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-12-15.
 * For Tiv Bible project.
 */

class FakeVerseDao(var verses: MutableList<Verse> = mutableListOf()): VerseDao {

    override fun getAllVerses() = Observable.just(verses.toList())

    override fun getVerseById(verseId: String) = Single.just(verses.first { it.id == verseId })

    override fun getVersesByText(searchText: String) = Observable.just(verses.filter { it.text.contains(searchText, true) })

    override fun getVersesByChapter(chapterId: String) = Observable.just(verses.filter { it.chapterId == chapterId })

    override fun getVersesByBook(bookId: String): Observable<List<Verse>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertVerses(vararg verses: Verse) = Completable.fromAction { this.verses.addAll(verses) }

    override fun insertVerses(verses: List<Verse>) = Completable.fromAction { this.verses.addAll(verses) }

    override fun deleteVerses(verses: List<Verse>) = Completable.fromAction{ this.verses.removeAll(verses) }

    override fun deleteAllVerses() = Completable.fromAction{ this.verses.clear() }
}