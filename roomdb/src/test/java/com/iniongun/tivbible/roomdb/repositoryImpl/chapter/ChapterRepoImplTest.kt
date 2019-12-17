package com.iniongun.tivbible.roomdb.repositoryImpl.chapter

import com.iniongun.tivbible.entities.Book
import com.iniongun.tivbible.entities.Chapter
import com.iniongun.tivbible.entities.Testament
import com.iniongun.tivbible.entities.Version
import com.iniongun.tivbible.roomdb.dao.ChapterDao
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Created by Isaac Iniongun on 2019-12-17.
 * For Tiv Bible project.
 */

@ExtendWith(MockKExtension::class)
internal class ChapterRepoImplTest {

    //class under test
    private lateinit var chapterRepoImpl: ChapterRepoImpl

    @MockK
    private lateinit var chapterDaoMock: ChapterDao

    private val testTestament = Testament(name = "New")
    private val testVersion = Version(name = "Old")
    private val testBook = Book(testTestament.id, testVersion.id, 1, 10, 50, name =  "Genese")
    private val testBook1 = Book(testTestament.id, testVersion.id, 2, 23, 455, name =  "Numeri")
    private val testChapters = listOf(
        Chapter(testBook.id, 1, 59),
        Chapter(testBook.id, 2, 432),
        Chapter(testBook1.id, 1, 204),
        Chapter(testBook1.id, 3, 556),
        Chapter(testBook1.id, 2, 650)
    )

    @BeforeEach
    fun setUp() { chapterRepoImpl = ChapterRepoImpl(chapterDaoMock) }

    @Test
    fun getAllChapters_shouldReturnCorrectValues_whenValuesInserted() {

        every { chapterDaoMock.getAllChapters() } returns Observable.just(testChapters)

        chapterRepoImpl.getAllChapters().test().assertValue(testChapters)
        chapterRepoImpl.getAllChapters().test().assertValue { it.count() == testChapters.count() && it[0].bookId == testBook.id }

    }

    @Test
    fun getChaptersByBook_shouldReturnCorrectValues_whenValuesAdded() {

        every { chapterDaoMock.getChaptersByBook(any()) } returns Observable.just(testChapters.filter { it.bookId == testBook.id })

        chapterRepoImpl.getChaptersByBook(testBook.id).test().assertValue(testChapters.take(2))
        chapterRepoImpl.getChaptersByBook(testBook.id).test().assertValue { it.count() == 2 && it[0] == testChapters[0] }

    }

    @Test
    fun getChaptersByBookAndChapterNumber_shouldReturnCorrectValues_whenValuesAdded() {

        every {
            chapterDaoMock.getChaptersByBookAndChapterNumber(
                any(),
                any()
            )
        } returns Single.just(testChapters.first { it.bookId == testBook1.id && it.chapterNumber == 2 })

        chapterRepoImpl.getChaptersByBookAndChapterNumber(testBook1.id, 2).test().assertValue(testChapters.last())
        chapterRepoImpl.getChaptersByBookAndChapterNumber(testBook1.id, 2).test().assertValue { it.numberOfVerses == testChapters.last().numberOfVerses }

    }

    @Test
    fun insertChapters_shouldInsertValuesSuccessfully() {

        every { chapterDaoMock.insertChapters(testChapters) } returns Completable.complete()

        chapterRepoImpl.insertChapters(testChapters).test().assertComplete()

    }

    @Test
    fun deleteChapters_shouldDeleteValuesSuccessfully() {

        every { chapterDaoMock.deleteChapters(testChapters) } returns Completable.complete()

        chapterRepoImpl.deleteChapters(testChapters).test().assertComplete()

    }
}