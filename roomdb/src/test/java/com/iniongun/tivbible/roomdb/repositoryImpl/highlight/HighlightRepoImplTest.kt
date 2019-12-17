package com.iniongun.tivbible.roomdb.repositoryImpl.highlight

import com.iniongun.tivbible.entities.Highlight
import com.iniongun.tivbible.entities.HighlightColor
import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.roomdb.dao.HighlightDao
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
internal class HighlightRepoImplTest {

    //class under test
    private lateinit var highlightRepoImpl: HighlightRepoImpl

    @MockK
    private lateinit var highlightDaoMock: HighlightDao

    private val testHighlightColor = HighlightColor("000000")
    private val testVerse = Verse("123", 1, "Ter ngurumun mo", false, "")
    private val highlightedOn = OffsetDateTime.now()
    private val testHighlights = listOf(
        Highlight(highlightedOn, testHighlightColor, testVerse),
        Highlight(OffsetDateTime.now(), testHighlightColor, testVerse),
        Highlight(OffsetDateTime.now(), testHighlightColor, testVerse),
        Highlight(highlightedOn, testHighlightColor, testVerse)
    )

    @BeforeEach
    fun setUp() {
        highlightRepoImpl = HighlightRepoImpl(highlightDaoMock)
    }

    @Test
    fun getAllHighlights_shouldReturnCorrectValues_whenValuesInserted() {

        every { highlightDaoMock.getAllHighlights() } returns Observable.just(testHighlights)

        highlightRepoImpl.getAllHighlights().test().assertValue(testHighlights)

    }

    @Test
    fun getHighlightById_shouldReturnCorrectValue_whenValuesInserted() {

        val highlight = testHighlights[0]

        every { highlightDaoMock.getHighlightById(any()) } returns Single.just(testHighlights.first { it.id == highlight.id })

        highlightRepoImpl.getHighlightById(highlight.id).test().assertValue(highlight)

    }

    @Test
    fun getHighlightByDate_shouldReturnCorrectValue_whenValuesInserted() {

        every { highlightDaoMock.getHighlightByDate(any()) } returns Single.just(testHighlights.first {
            it.highlightedOn == OffsetDateTime.parse(
                highlightedOn.toString()
            )
        })

        highlightRepoImpl.getHighlightByDate(highlightedOn).test().assertValue(testHighlights[0])

    }

    @Test
    fun insertHighlights_shouldCompleteSuccessfully() {

        every { highlightDaoMock.insertHighlights(testHighlights) } returns Completable.complete()

        highlightRepoImpl.insertHighlights(testHighlights).test().assertComplete()

    }

    @Test
    fun deleteHighlights_shouldCompleteSuccessfully() {

        every { highlightDaoMock.deleteHighlights(testHighlights) } returns Completable.complete()

        highlightRepoImpl.deleteHighlights(testHighlights).test().assertComplete()

    }
}