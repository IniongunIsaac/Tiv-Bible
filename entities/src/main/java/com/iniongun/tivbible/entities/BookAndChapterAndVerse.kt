package com.iniongun.tivbible.entities

/**
 * Created by Isaac Iniongun on 08/05/2020.
 * For Tiv Bible project.
 */

data class BookAndChapterAndVerse(
    val testamentId: String,
    val versionId: String,
    val orderNo: Int,
    val numberOfChapters: Int,
    val bookNumberOfVerses: Int,
    val bookId: String,
    val bookName: String,

    val chapterNumber: Int,
    val chapterNumberOfVerses: Int,
    val chapterId: String,

    val verseNumber: Int,
    val verseText: String,
    val verseHasTitle: Boolean,
    val verseTitle: String,
    val verseId: String
)

val BookAndChapterAndVerse.book: Book
    get() = Book(testamentId, versionId, orderNo, numberOfChapters, bookNumberOfVerses, bookId, bookName)

val BookAndChapterAndVerse.chapter: Chapter
    get() = Chapter(bookId, chapterNumber, chapterNumberOfVerses, chapterId)

val BookAndChapterAndVerse.verse: Verse
    get() = Verse(chapterId, verseNumber, verseText, verseHasTitle, verseTitle, verseId)

val BookAndChapterAndVerse.bookAndChapterAndVerseString: String
    get() = "$bookName $chapterNumber:$verseNumber"