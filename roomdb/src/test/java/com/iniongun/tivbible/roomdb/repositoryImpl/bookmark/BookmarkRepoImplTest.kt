package com.iniongun.tivbible.roomdb.repositoryImpl.bookmark

import com.iniongun.tivbible.entities.Bookmark
import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.roomdb.dao.BookmarkDao
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.threeten.bp.OffsetDateTime

/**
 * Created by Isaac Iniongun on 2019-12-17.
 * For Tiv Bible project.
 */

@ExtendWith(MockKExtension::class)
internal class BookmarkRepoImplTest {

    //class under test
    private lateinit var bookmarkRepoImpl: BookmarkRepoImpl

    @MockK
    private lateinit var bookmarkDaoMock: BookmarkDao

    private val testVerse = Verse("123", 1, "Ter ngurumun mo", false, "")
    private val bookmarkedOn = OffsetDateTime.now().toString()
    private val testBookmarks = listOf(
        Bookmark(OffsetDateTime.parse(bookmarkedOn), testVerse),
        Bookmark(OffsetDateTime.now(), testVerse),
        Bookmark(OffsetDateTime.now(), testVerse)
    )

    @BeforeEach
    fun setUp() { bookmarkRepoImpl = BookmarkRepoImpl(bookmarkDaoMock) }

    @Test
    fun getBookmarks_shouldReturnCorrectValues_whenValuesAdded() {

        every { bookmarkDaoMock.getBookmarks() } returns Observable.just(testBookmarks)

        bookmarkRepoImpl.getBookmarks().test().assertValue { it.count() == testBookmarks.count() && it.containsAll(testBookmarks) }

    }

    @Test
    fun getBookmarkByDate_shouldReturnCorrectValues_whenValuesAdded() {

        every { bookmarkDaoMock.getBookmarkByDate(bookmarkedOn) } returns Observable.just(
            testBookmarks.filter { it.bookmarkedOn == OffsetDateTime.parse(bookmarkedOn) })

        bookmarkRepoImpl.getBookmarkByDate(bookmarkedOn).test().assertValue { it.isNotEmpty() }

    }

    @Test
    fun getBookmarkByVerse_shouldReturnCorrectValues_whenValuesAdded() {

        every { bookmarkDaoMock.getBookmarkByVerse(testVerse.id) } returns Single.just(testBookmarks.first { it.verse.id == testVerse.id })

        bookmarkRepoImpl.getBookmarkByVerse(testVerse.id).test().assertValue { it == testBookmarks[0] && it.verse == testVerse }

    }

    @Test
    fun insertBookmarks_shouldInsertBookmarksSuccessfully() {

        every { bookmarkDaoMock.insertBookmarks(testBookmarks) } returns Completable.complete()

        bookmarkRepoImpl.insertBookmarks(testBookmarks).test().assertComplete()

    }

    @Test
    fun deleteBookmarks_shouldDeleteSuccessfully() {

        every { bookmarkDaoMock.deleteBookmarks(testBookmarks) } returns Completable.complete()

        bookmarkRepoImpl.deleteBookmarks(testBookmarks).test().assertComplete()
    }
}