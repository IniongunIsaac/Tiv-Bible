package com.iniongun.tivbible.roomdb.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iniongun.tivbible.entities.Book
import com.iniongun.tivbible.entities.Testament
import com.iniongun.tivbible.entities.Version
import com.iniongun.tivbible.roomdb.database.TivBibleDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.runner.RunWith


/**
 * Created by Isaac Iniongun on 2019-12-09.
 * For Tiv Bible project.
 */

@RunWith(AndroidJUnit4::class)
class BookDaoTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mTivBibleDatabase: TivBibleDatabase

    //SUT
    private lateinit var bookDao: BookDao

    private lateinit var testamentDao: TestamentDao
    private lateinit var versionDao: VersionDao

    private val testTestament = Testament(name = "New")
    private val testVersion = Version(name = "Old")
    private val testBook = Book(testTestament.id, testVersion.id, 1, 10, 50, name =  "Genese")

    @Before
    internal fun setUp() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mTivBibleDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TivBibleDatabase::class.java
        )
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

        bookDao = mTivBibleDatabase.bookDao()
        testamentDao = mTivBibleDatabase.testamentDao()
        versionDao = mTivBibleDatabase.versionDao()
    }

    @After
    internal fun tearDown() {
        mTivBibleDatabase.close()
    }

    @Test
    internal fun getBooks_shouldReturnEmpty_whenNoBooksInserted() {

        val books = bookDao.getAllBooks().blockingFirst()

        assertTrue(books.isNullOrEmpty())
    }

    @Test
    internal fun getBooks_shouldReturnOneBook_whenOneBookInserted() {

        testamentDao.insertTestaments(testTestament).blockingAwait()

        versionDao.insertVersions(testVersion).blockingAwait()

        bookDao.insertBooks(testBook).blockingAwait()

        val books = bookDao.getAllBooks().blockingFirst()

        assertTrue(books.count() == 1)

    }

    @Test
    internal fun getBooksByTestament_shouldReturnCorrectBooks() {

        val oldTestament = Testament(name = "Old")
        val book = Book(oldTestament.id, testVersion.id, 2, 20, 30, name =  "Numeri")

        testamentDao.insertTestaments(testTestament, oldTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()

        bookDao.insertBooks(listOf(testBook, book)).blockingAwait()

        val books = bookDao.getBooksByTestament(testTestament.id).blockingFirst()

        assertTrue(books.count() == 1)
        assertEquals(books[0].id, testBook.id)
        assertEquals(books[0].testamentId, testBook.testamentId)
        assertEquals(books[0].orderNo, testBook.orderNo)
        assertEquals(books[0].numberOfChapters, testBook.numberOfChapters)
        assertEquals(books[0].numberOfVerses, testBook.numberOfVerses)
        assertEquals(books[0].name, testBook.name)

    }

    @Test
    internal fun getBooksByTestament_shouldReturnNoBooks_whenTestamentDoesNotExist() {
        val books = bookDao.getBooksByTestament("123456").blockingFirst()

        assertTrue(books.isNullOrEmpty())
    }

    @Test
    internal fun getBooksByVersion_shouldReturnCorrectBooks() {

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()
        bookDao.insertBooks(testBook).blockingAwait()

        val books = bookDao.getBooksByVersion(testVersion.id).blockingFirst()

        assertTrue(books.count() == 1)
        assertEquals(books[0].id, testBook.id)
        assertEquals(books[0].testamentId, testBook.testamentId)
        assertEquals(books[0].orderNo, testBook.orderNo)
        assertEquals(books[0].numberOfChapters, testBook.numberOfChapters)
        assertEquals(books[0].numberOfVerses, testBook.numberOfVerses)
        assertEquals(books[0].name, testBook.name)

    }

    @Test
    internal fun getBooksByVersion_shouldReturnNoBooks_whenVersionDoesNotExist() {
        val books = bookDao.getBooksByVersion("123456").blockingFirst()

        assertTrue(books.isNullOrEmpty())
    }

    @Test
    internal fun getBooksByTestamentAndVersion_shouldReturnCorrectBooks() {

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()
        bookDao.insertBooks(testBook).blockingAwait()

        val books = bookDao.getBooksByTestamentAndVersion(testTestament.id, testVersion.id).blockingFirst()

        assertTrue(books.count() == 1)
        assertEquals(books[0].id, testBook.id)
        assertEquals(books[0].testamentId, testBook.testamentId)
        assertEquals(books[0].orderNo, testBook.orderNo)
        assertEquals(books[0].numberOfChapters, testBook.numberOfChapters)
        assertEquals(books[0].numberOfVerses, testBook.numberOfVerses)
        assertEquals(books[0].name, testBook.name)

    }

    @Test
    internal fun getBooksByTestamentAndVersion_shouldReturnNoBooks_whenTestamentAndVersionDoesNotExist() {
        val books = bookDao.getBooksByTestamentAndVersion(testTestament.id, testVersion.id).blockingFirst()
        assertTrue(books.isNullOrEmpty())
    }

    @Test
    internal fun getBooksByTestamentAndVersion_shouldReturnNoBooks_whenTestamentDoesNotExist() {
        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()
        bookDao.insertBooks(testBook).blockingAwait()

        val books = bookDao.getBooksByTestamentAndVersion("123456", testVersion.id).blockingFirst()

        assertTrue(books.isNullOrEmpty())
    }

    @Test
    internal fun getBooksByTestamentAndVersion_shouldReturnNoBooks_whenVersionDoesNotExist() {

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()
        bookDao.insertBooks(testBook).blockingAwait()

        val books = bookDao.getBooksByTestamentAndVersion(testTestament.id, "123456").blockingFirst()

        assertTrue(books.isNullOrEmpty())
    }

    @Test
    internal fun insertBooks_shouldReturnCorrectNumberOfBooksInserted() {

        val book = Book(testTestament.id, testVersion.id, 2, 20, 30, name =  "Numeri")

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()

        bookDao.insertBooks(listOf(testBook, book)).blockingAwait()

        val books = bookDao.getAllBooks().blockingFirst()

        assertTrue(books.count() == 2)
        assertEquals(books[0].id, testBook.id)
        assertEquals(books[0].testamentId, testBook.testamentId)
        assertEquals(books[0].orderNo, testBook.orderNo)
        assertEquals(books[0].numberOfChapters, testBook.numberOfChapters)
        assertEquals(books[0].numberOfVerses, testBook.numberOfVerses)
        assertEquals(books[0].name, testBook.name)
        assertEquals(books[1].id, book.id)
        assertEquals(books[1].testamentId, book.testamentId)
        assertEquals(books[1].orderNo, book.orderNo)
        assertEquals(books[1].numberOfChapters, book.numberOfChapters)
        assertEquals(books[1].numberOfVerses, book.numberOfVerses)
        assertEquals(books[1].name, book.name)

    }

    @Test
    internal fun insertBooks_shouldReplacePreviouslyInsertedBookOnConflict() {

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()
        bookDao.insertBooks(testBook).blockingAwait()

        val book = Book(testTestament.id, testVersion.id, 3, 35, 590, testBook.id, "Mpase")

        bookDao.insertBooks(book).blockingAwait()

        val books = bookDao.getAllBooks().blockingFirst()

        assertTrue(books.count() == 1)
        assertEquals(books[0].id, book.id)
        assertEquals(books[0].testamentId, book.testamentId)
        assertEquals(books[0].orderNo, book.orderNo)
        assertEquals(books[0].numberOfChapters, book.numberOfChapters)
        assertEquals(books[0].numberOfVerses, book.numberOfVerses)
        assertEquals(books[0].name, book.name)
    }

    @Test
    internal fun deleteAllBooks_shouldReturnNoBooks_whenGetAllBooksCalledAfterDeletion() {

        val book = Book(testTestament.id, testVersion.id, 2, 20, 30, name =  "Numeri")

        testamentDao.insertTestaments(testTestament).blockingAwait()
        versionDao.insertVersions(testVersion).blockingAwait()

        bookDao.insertBooks(listOf(testBook, book)).blockingAwait()

        bookDao.deleteAllBooks().blockingAwait()

        val books = bookDao.getAllBooks().blockingFirst()

        assertTrue(books.isNullOrEmpty())

    }

}