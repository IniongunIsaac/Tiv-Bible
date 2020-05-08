package com.iniongun.tivbible.roomdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.BookAndChapterAndVerse
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

    @Query("select * from Verse where text like '%' || :searchText || '%' and chapter_id = :chapterId order by number asc")
    fun getVersesByTextAndChapter(searchText: String, chapterId: String): Observable<List<Verse>>

    @Query("Select * from Verse inner join Chapter on Chapter.id = Verse.chapter_id inner join Book on Book.id = Chapter.book_id where Book.id = :bookId order by Verse.number")
    fun getVersesByBook(bookId: String): Observable<List<Verse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVerses(vararg verses: Verse): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVerses(verses: List<Verse>): Completable

    @Delete
    fun deleteVerses(verses: List<Verse>): Completable

    @Query("delete from Verse")
    fun deleteAllVerses(): Completable

    @Query("select book.testament_id as testamentId, book.version_id as versionId, book.order_no as orderNo, book.number_of_chapters as numberOfChapters, book.number_of_verses as bookNumberOfVerses, book.id as bookId, book.name as bookName, " +
            "chapter.chapter_number as chapterNumber, chapter.number_of_verses as chapterNumberOfVerses, chapter.id as chapterId, " +
            "verse.number as verseNumber, verse.text as verseText, verse.has_title as verseHasTitle, verse.title as verseTitle, verse.id as verseId " +
            "from book, chapter, verse where book.id = chapter.book_id and verse.chapter_id = chapter.id and text like '%' || :searchText || '%'")
    fun getBooksAndChaptersAndVersesByText(searchText: String): Observable<List<BookAndChapterAndVerse>>

    @Query("select book.testament_id as testamentId, book.version_id as versionId, book.order_no as orderNo, book.number_of_chapters as numberOfChapters, book.number_of_verses as bookNumberOfVerses, book.id as bookId, book.name as bookName, " +
            "chapter.chapter_number as chapterNumber, chapter.number_of_verses as chapterNumberOfVerses, chapter.id as chapterId, " +
            "verse.number as verseNumber, verse.text as verseText, verse.has_title as verseHasTitle, verse.title as verseTitle, verse.id as verseId " +
            "from book, chapter, verse where book.id = chapter.book_id and verse.chapter_id = chapter.id and text like '%' || :searchText || '%' and chapter_id = :chapterId order by number asc")
    fun getBooksAndChaptersAndVersesByTextAndChapter(searchText: String, chapterId: String): Observable<List<BookAndChapterAndVerse>>

}