package com.iniongun.tivbible.roomdb.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iniongun.tivbible.entities.Book
import com.iniongun.tivbible.entities.Chapter
import com.iniongun.tivbible.entities.Testament
import com.iniongun.tivbible.entities.Version
import com.iniongun.tivbible.roomdb.database.TivBibleDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith

/**
 * Created by Isaac Iniongun on 2019-12-13.
 * For Tiv Bible project.
 */

@RunWith(AndroidJUnit4::class)
class ChapterDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mTivBibleDatabase: TivBibleDatabase

    //SUT
    private lateinit var chapterDao: ChapterDao

    private lateinit var testamentDao: TestamentDao
    private lateinit var versionDao: VersionDao
    private lateinit var bookDao: BookDao

    private val testTestament = Testament(name = "New")
    private val testVersion = Version(name = "Old")
    private val testBook = Book(testTestament.id, testVersion.id, 1, 10, 50, name =  "Genese")
    private val testChapter = Chapter(testBook.id, 1, 59)

    @Before
    internal fun setUp() {

        mTivBibleDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(), TivBibleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

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
    internal fun getAllChapters_shouldReturnNoChapters_whenNoChaptersInserted() {

        val chapters = chapterDao.getAllChapters().blockingFirst()

        assertTrue(chapters.isNullOrEmpty())

    }

    @Test
    internal fun getAllChapters_shouldReturnCorrectChapters_whenChaptersInserted() {

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()
        bookDao.insertBooks(testBook).blockingAwait()
        chapterDao.insertChapters(testChapter).blockingAwait()

        val chapters = chapterDao.getAllChapters().blockingFirst()

        assertTrue(chapters.count() == 1)
        assertEquals(chapters[0].bookId, testChapter.bookId)
        assertEquals(chapters[0].id, testChapter.id)
        assertEquals(chapters[0].chapterNumber, testChapter.chapterNumber)
        assertEquals(chapters[0].numberOfVerses, testChapter.numberOfVerses)

    }

    @Test
    internal fun getChaptersByBook_shouldReturnNoChapters_whenNoChaptersInserted() {

        val chapters = chapterDao.getChaptersByBook(testBook.id).blockingFirst()

        assertTrue(chapters.isNullOrEmpty())
    }

    @Test
    internal fun getChaptersByBook_shouldReturnCorrectChapters_whenChaptersInserted() {

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()
        bookDao.insertBooks(testBook).blockingAwait()
        chapterDao.insertChapters(testChapter).blockingAwait()

        val chapters = chapterDao.getChaptersByBook(testBook.id).blockingFirst()

        assertTrue(chapters.count() == 1)
        assertEquals(chapters[0].bookId, testChapter.bookId)
        assertEquals(chapters[0].id, testChapter.id)
        assertEquals(chapters[0].chapterNumber, testChapter.chapterNumber)
        assertEquals(chapters[0].numberOfVerses, testChapter.numberOfVerses)

    }

    @Test
    internal fun getChapterById_shouldReturnNoChapter_whenNoChapterInserted() {

        val chapter = chapterDao.getChapterById(testChapter.id).blockingGet()

        assertNull(chapter)

    }

    @Test
    internal fun getChapterById_shouldReturnCorrectChapter_whenChapterInserted() {

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()
        bookDao.insertBooks(testBook).blockingAwait()
        chapterDao.insertChapters(testChapter).blockingAwait()

        val chapter = chapterDao.getChapterById(testChapter.id).blockingGet()

        assertNotNull(chapter)
        assertEquals(chapter.bookId, testChapter.bookId)
        assertEquals(chapter.id, testChapter.id)
        assertEquals(chapter.chapterNumber, testChapter.chapterNumber)
        assertEquals(chapter.numberOfVerses, testChapter.numberOfVerses)

    }

    @Test
    internal fun getChaptersByBookAndChapterNumber_shouldReturnNull_whenNoChapterInserted() {

        assertThrows<EmptyResultSetException> { chapterDao.getChaptersByBookAndChapterNumber(testBook.id, testChapter.chapterNumber).blockingGet() }

    }

    @Test
    internal fun getChapterByBookAndChapterNumber_shouldReturnCorrectChapter_whenChapterInserted() {

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()
        bookDao.insertBooks(testBook).blockingAwait()
        chapterDao.insertChapters(testChapter).blockingAwait()

        val chapter = chapterDao.getChaptersByBookAndChapterNumber(testBook.id, testChapter.chapterNumber).blockingGet()

        assertNotNull(chapter)
        assertEquals(chapter.bookId, testChapter.bookId)
        assertEquals(chapter.id, testChapter.id)
        assertEquals(chapter.chapterNumber, testChapter.chapterNumber)
        assertEquals(chapter.numberOfVerses, testChapter.numberOfVerses)

    }

    @Test
    internal fun deleteAllChapters_shouldReturnNoChapters_whenChaptersInserted_andThenDeletedChaptersCalled_andThenGetAllChaptersCalled() {

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()
        bookDao.insertBooks(testBook).blockingAwait()
        chapterDao.insertChapters(testChapter).blockingAwait()

        chapterDao.deleteAllChapters().blockingAwait()

        val chapters = chapterDao.getAllChapters().blockingFirst()
        val chapter = chapterDao.getChapterById(testChapter.id).blockingGet()

        assertTrue(chapters.isNullOrEmpty())
        assertNull(chapter)

    }

}