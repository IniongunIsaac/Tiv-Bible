package com.iniongun.tivbible.roomdb.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iniongun.tivbible.entities.*
import com.iniongun.tivbible.roomdb.database.TivBibleDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith

/**
 * Created by Isaac Iniongun on 2019-12-12.
 * For Tiv Bible project.
 */

@RunWith(AndroidJUnit4::class)
class VerseDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mTivBibleDatabase: TivBibleDatabase

    //SUT
    private lateinit var verseDao: VerseDao

    private lateinit var testamentDao: TestamentDao
    private lateinit var versionDao: VersionDao
    private lateinit var bookDao: BookDao
    private lateinit var chapterDao: ChapterDao

    private val testTestament = Testament(name = "New")
    private val testVersion = Version(name = "Old")
    private val testBook = Book(testTestament.id, testVersion.id, 1, 10, 50, name =  "Genese")
    private val testChapter = Chapter(testBook.id, 1, 59)
    private val testVerse = Verse(testChapter.id, 1, "Sha hiihii la, Aondo gba sha man tar", true, "Kwagh u igbetar")

    @Before
    internal fun setUp() {

        mTivBibleDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(), TivBibleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        verseDao = mTivBibleDatabase.verseDao()
        testamentDao = mTivBibleDatabase.testamentDao()
        versionDao = mTivBibleDatabase.versionDao()
        chapterDao = mTivBibleDatabase.chapterDao()
        bookDao = mTivBibleDatabase.bookDao()

    }

    @After
    internal fun tearDown() {
        mTivBibleDatabase.close()
    }

    @Test
    internal fun getAllVerses_shouldReturnEmpty_whenNoVersesInserted() {

        val verses = verseDao.getAllVerses().blockingFirst()

        assertTrue(verses.isNullOrEmpty())

    }

    @Test
    internal fun getVerseById_shouldReturnNoVerse_whenNoVerseInserted() {
        assertThrows<EmptyResultSetException> { verseDao.getVerseById(testVerse.id).blockingGet() }
    }

    @Test
    internal fun getVerseById_shouldReturnInsertedVerse() {

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()
        bookDao.insertBooks(testBook).blockingAwait()
        chapterDao.insertChapters(testChapter).blockingAwait()

        verseDao.insertVerses(testVerse).blockingAwait()

        val verse = verseDao.getVerseById(testVerse.id).blockingGet()

        assertEquals(verse.id, testVerse.id)
        assertEquals(verse.chapterId, testVerse.chapterId)
        assertEquals(verse.hasTitle, testVerse.hasTitle)
        assertEquals(verse.number, testVerse.number)
        assertEquals(verse.text, testVerse.text)
        assertEquals(verse.title, testVerse.title)
    }

    @Test
    internal fun getVersesByText_shouldReturnNoVerse_whenNoVerseInserted() {

        val verses = verseDao.getVersesByText("sha hiihii la").blockingFirst()

        assertTrue(verses.isNullOrEmpty())

    }

    @Test
    internal fun getVersesByText_shouldReturnVerse_whenVerseInserted() {

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()
        bookDao.insertBooks(testBook).blockingAwait()
        chapterDao.insertChapters(testChapter).blockingAwait()

        verseDao.insertVerses(testVerse).blockingAwait()

        val verses = verseDao.getVersesByText("sha hiihii la").blockingFirst()

        assertTrue(verses.count() == 1)
        assertTrue(verses[0].text.contains("sha hiihii la", true))
        assertEquals(verses[0].id, testVerse.id)
        assertEquals(verses[0].chapterId, testVerse.chapterId)
        assertEquals(verses[0].text, testVerse.text)
        assertEquals(verses[0].hasTitle, testVerse.hasTitle)
        assertEquals(verses[0].title, testVerse.title)
        assertEquals(verses[0].number, testVerse.number)

    }

    @Test
    internal fun getVersesByChapter_shouldReturnNoVerses_whenNoVersesInserted() {

        val verses = verseDao.getVersesByChapter(testChapter.id).blockingFirst()

        assertTrue(verses.isNullOrEmpty())
    }

    @Test
    internal fun getVersesByChapter_shouldReturnVerse_whenVerseInserted() {

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()
        bookDao.insertBooks(testBook).blockingAwait()
        chapterDao.insertChapters(testChapter).blockingAwait()

        verseDao.insertVerses(testVerse).blockingAwait()

        val verses = verseDao.getVersesByChapter(testChapter.id).blockingFirst()

        assertTrue(verses.count() == 1)
        assertTrue(verses[0].text.contains("sha hiihii la", true))
        assertEquals(verses[0].id, testVerse.id)
        assertEquals(verses[0].chapterId, testVerse.chapterId)
        assertEquals(verses[0].text, testVerse.text)
        assertEquals(verses[0].hasTitle, testVerse.hasTitle)
        assertEquals(verses[0].title, testVerse.title)
        assertEquals(verses[0].number, testVerse.number)

    }

    @Test
    internal fun getVersesByBook_shouldReturnNoVerses_whenNoVersesInserted() {

        val verses = verseDao.getVersesByBook(testBook.id).blockingFirst()

        assertTrue(verses.isNullOrEmpty())
    }

    @Test
    internal fun getVersesByBook_shouldReturnCorrectVerses_whenVersesInserted() {

        val book = Book(testTestament.id, testVersion.id, 2, 15, 209, name = "Numeri")
        val chapter = Chapter(testBook.id, 1, 10)
        val chapter1 = Chapter(book.id, 1, 10)
        val verse = Verse(testChapter.id, 2, "Ter rumun a mo keke je", false, "")
        val verse1 = Verse(chapter.id, 2, "Ter rumun a mo keke je", false, "")
        val verse2 = Verse(chapter1.id, 2, "Ter rumun a mo keke je", false, "")

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()
        bookDao.insertBooks(testBook, book).blockingAwait()
        chapterDao.insertChapters(testChapter, chapter, chapter1).blockingAwait()

        verseDao.insertVerses(testVerse, verse, verse1, verse2).blockingAwait()

        val verses = verseDao.getVersesByBook(testBook.id).blockingFirst()
        val verses1 = verseDao.getVersesByBook(book.id).blockingFirst()

        assertTrue(verses.count() == 3)
        assertTrue(verses1.count() == 1)

    }

    @Test
    internal fun deleteAllVerses_shouldReturnNoVerses_whenVersesInserted_andThenDeletedVersesCalled_andThenGetAllVersesCalled() {

        val book = Book(testTestament.id, testVersion.id, 2, 15, 209, name = "Numeri")
        val chapter = Chapter(testBook.id, 1, 10)
        val chapter1 = Chapter(book.id, 1, 10)
        val verse = Verse(testChapter.id, 2, "Ter rumun a mo keke je", false, "")
        val verse1 = Verse(chapter.id, 2, "Ter rumun a mo keke je", false, "")
        val verse2 = Verse(chapter1.id, 2, "Ter rumun a mo keke je", false, "")

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()
        bookDao.insertBooks(testBook, book).blockingAwait()
        chapterDao.insertChapters(testChapter, chapter, chapter1).blockingAwait()

        verseDao.insertVerses(testVerse, verse, verse1, verse2).blockingAwait()

        verseDao.deleteAllVerses().blockingAwait()

        val verses = verseDao.getVersesByBook(testBook.id).blockingFirst()

        assertTrue(verses.isNullOrEmpty())

    }
}