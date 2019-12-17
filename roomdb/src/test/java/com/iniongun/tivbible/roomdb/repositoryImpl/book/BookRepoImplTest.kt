package com.iniongun.tivbible.roomdb.repositoryImpl.book

import com.iniongun.tivbible.entities.Book
import com.iniongun.tivbible.roomdb.dao.BookDao
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

/**
 * Created by Isaac Iniongun on 2019-12-16.
 * For Tiv Bible project.
 */

@ExtendWith(MockKExtension::class)
internal class BookRepoImplTest {

    //Class under test
    private lateinit var bookRepoImpl: BookRepoImpl

    @MockK
    private lateinit var bookDaoMock: BookDao

    private val testNewTestamentId =  UUID.randomUUID().toString()
    private val testOldTestamentId =  UUID.randomUUID().toString()
    private val testNewVersionId =  UUID.randomUUID().toString()
    private val testOldVersionId =  UUID.randomUUID().toString()
    private val testBooks = listOf(
        Book(testNewTestamentId, testNewVersionId, 1, 29, 105, name = "Mpase u Yohane"),
        Book(testNewTestamentId, testNewVersionId, 3, 50, 259, name = "1 Yohane"),
        Book(testOldTestamentId, testOldVersionId, 11, 11, 289, name = "2 Yohane"),
        Book(testOldTestamentId, testOldVersionId, 34, 34, 111, name = "Genese"),
        Book(testOldTestamentId, testNewVersionId, 50, 77, 534, name = "Kroniku")
    )

    private val testStringArgumentCaptor = slot<String>()
    private val testStringArgumentCaptor2 = slot<String>()

    @BeforeEach
    fun setUp() {
        bookRepoImpl = BookRepoImpl(bookDaoMock)
    }

    @Test
    fun getAllBooks_shouldReturnCorrectBooks_whenBooksAdded() {
        every { bookDaoMock.getAllBooks() } returns Observable.just(testBooks)

        bookRepoImpl.getAllBooks().test().assertValue { it.containsAll(testBooks) && it[0] == testBooks[0] && it.count() == testBooks.count() }

        verify { bookDaoMock.getAllBooks() }

        confirmVerified(bookDaoMock)
    }

    @Test
    fun getAllBooks_shouldReturnEmpty_whenNoBooksAdded() {
        every { bookDaoMock.getAllBooks() } returns Observable.just(listOf())

        bookRepoImpl.getAllBooks().test().assertValue { it.isNullOrEmpty() }

        verify {
            bookDaoMock.getAllBooks()
        }

        confirmVerified(bookDaoMock)
    }

    @Test
    fun getBooksByTestament_shouldReturnCorrectBooks_whenBooksAdded() {

        val book = testBooks[0]

        every { bookDaoMock.getBooksByTestament(any()) } returns Observable.just(testBooks.filter { it.testamentId == book.testamentId })

        bookRepoImpl.getBooksByTestament(book.testamentId).test().assertValue {
            it.count() == 2 &&
                    it.containsAll(listOf(book, testBooks[1])) &&
                    it[0] == testBooks[0] &&
                    it[0].testamentId == testBooks[0].testamentId
        }

        verify { bookDaoMock.getBooksByTestament(any()) }

        confirmVerified(bookDaoMock)

    }

    @Test
    fun getBooksByTestament_shouldReceiveCorrectTestamentIdArgument() {

        val book = testBooks[0]

        every { bookDaoMock.getBooksByTestament(any()) } returns Observable.just(listOf())
        every { bookRepoImpl.getBooksByTestament(capture(testStringArgumentCaptor)) } returns Observable.just(
            listOf()
        )

        bookRepoImpl.getBooksByTestament(book.testamentId)

        assertTrue(testStringArgumentCaptor.isCaptured)
        assertEquals(book.testamentId, testStringArgumentCaptor.captured)

        verify { bookDaoMock.getBooksByTestament(any()) }

        confirmVerified(bookDaoMock)
    }

    @Test
    fun getBooksByTestament_shouldReturnEmpty_whenNoBooksAdded() {
        val book = testBooks[0]

        every { bookDaoMock.getBooksByTestament(any()) } returns Observable.just(listOf())
        every { bookRepoImpl.getBooksByTestament(any()) } returns Observable.just(listOf())

        bookRepoImpl.getBooksByTestament(book.testamentId).test().assertValue { it.isNullOrEmpty() }

        verify { bookDaoMock.getBooksByTestament(any()) }

        confirmVerified(bookDaoMock)
    }

    @Test
    fun getBooksByVersion_shouldReturnCorrectBooks_whenBooksAdded() {

        val book = testBooks[1]

        every { bookDaoMock.getBooksByVersion(any()) } returns Observable.just(testBooks.filter { it.versionId == book.versionId })

        bookRepoImpl.getBooksByVersion(book.versionId).test().assertValue {
            it.count() == 3 &&
                    it.containsAll(listOf(book, testBooks[0])) &&
                    it[1] == testBooks[1] &&
                    it[0].versionId == testBooks[0].versionId
        }

        verify { bookDaoMock.getBooksByVersion(any()) }

        confirmVerified(bookDaoMock)

    }

    @Test
    fun getBooksByVersion_shouldReturnEmpty_whenNoBooksAdded() {
        val book = testBooks[0]

        every { bookDaoMock.getBooksByVersion(any()) } returns Observable.just(listOf())
        every { bookRepoImpl.getBooksByVersion(any()) } returns Observable.just(listOf())

        bookRepoImpl.getBooksByVersion(book.versionId).test().assertValue { it.isNullOrEmpty() }

        verify { bookDaoMock.getBooksByVersion(any()) }

        confirmVerified(bookDaoMock)
    }

    @Test
    fun getBooksByVersion_shouldReceiveCorrectVersionIdArgument() {

        val book = testBooks[0]

        every { bookDaoMock.getBooksByVersion(any()) } returns Observable.just(listOf())
        every { bookRepoImpl.getBooksByVersion(capture(testStringArgumentCaptor)) } returns Observable.just(
            listOf()
        )

        bookRepoImpl.getBooksByVersion(book.versionId)

        assertTrue(testStringArgumentCaptor.isCaptured)
        assertEquals(book.versionId, testStringArgumentCaptor.captured)

        verify { bookDaoMock.getBooksByVersion(any()) }

        confirmVerified(bookDaoMock)
    }

    @Test
    fun getBooksByTestamentAndVersion_shouldReturnCorrectBooks_whenBooksAdded() {

        val book = testBooks[0]

        every { bookDaoMock.getBooksByTestamentAndVersion(any(), any()) } returns Observable.just(testBooks.filter { it.testamentId == book.testamentId && it.versionId == book.versionId })

        bookRepoImpl.getBooksByTestamentAndVersion(book.testamentId, book.versionId).test().assertValue {
            it.count() == 2 &&
                    it.containsAll(listOf(book, testBooks[1])) &&
                    it[0] == book &&
                    it[1] == testBooks[1] &&
                    it[0].versionId == testBooks[0].versionId &&
                    it[0].testamentId == testBooks[0].testamentId
        }

        verify { bookDaoMock.getBooksByTestamentAndVersion(any(), any()) }

        confirmVerified(bookDaoMock)

    }

    @Test
    fun getBooksByTestamentAndVersion_shouldReturnEmpty_whenNoBooksAdded() {

        val book = testBooks[0]

        every { bookDaoMock.getBooksByTestamentAndVersion(any(), any()) } returns Observable.just(listOf())
        every { bookRepoImpl.getBooksByTestamentAndVersion(any(), any()) } returns Observable.just(listOf())

        bookRepoImpl.getBooksByTestamentAndVersion(book.testamentId, book.versionId).test().assertValue { it.isNullOrEmpty() }

        verify { bookDaoMock.getBooksByTestamentAndVersion(any(), any()) }

        confirmVerified(bookDaoMock)

    }

    @Test
    fun getBooksByTestamentAndVersion_shouldReceiveCorrectTestamentIdAndVersionIdArguments() {

        val book = testBooks[0]

        every { bookDaoMock.getBooksByTestamentAndVersion(any(), any()) } returns Observable.just(listOf())
        every { bookRepoImpl.getBooksByTestamentAndVersion(capture(testStringArgumentCaptor), capture(testStringArgumentCaptor2)) } returns Observable.just(
            listOf()
        )

        bookRepoImpl.getBooksByTestamentAndVersion(book.testamentId, book.versionId)

        assertTrue(testStringArgumentCaptor.isCaptured)
        assertTrue(testStringArgumentCaptor2.isCaptured)
        assertEquals(book.testamentId, testStringArgumentCaptor.captured)
        assertEquals(book.versionId, testStringArgumentCaptor2.captured)

        verify { bookDaoMock.getBooksByTestamentAndVersion(any(), any()) }

        confirmVerified(bookDaoMock)
    }

    @Test
    fun insertBooks_shouldInsertBooksSuccessfully() {

        every { bookDaoMock.insertBooks(testBooks) } returns Completable.complete()

        bookRepoImpl.insertBooks(testBooks).test().assertComplete()

        verify { bookDaoMock.insertBooks(testBooks) }

        confirmVerified(bookDaoMock)
    }

    @Test
    fun deleteBooks_shouldReturnNoValuesAfterDeleteOperation() {

        every { bookDaoMock.deleteBooks(testBooks) } returns Completable.complete()

        bookRepoImpl.deleteBooks(testBooks).test().assertComplete()

        verify { bookDaoMock.deleteBooks(testBooks) }

        confirmVerified(bookDaoMock)

    }
}