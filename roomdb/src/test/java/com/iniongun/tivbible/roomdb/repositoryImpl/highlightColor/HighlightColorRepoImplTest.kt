package com.iniongun.tivbible.roomdb.repositoryImpl.highlightColor

import com.iniongun.tivbible.entities.HighlightColor
import com.iniongun.tivbible.roomdb.dao.HighlightColorDao
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
internal class HighlightColorRepoImplTest {

    //class under test
    private lateinit var highlightColorRepoImpl: HighlightColorRepoImpl

    @MockK
    private lateinit var highlightColorDaoMock: HighlightColorDao

    private val testHighlightColors = listOf(
        HighlightColor("000000"),
        HighlightColor("000000"),
        HighlightColor("000000"),
        HighlightColor("000000"),
        HighlightColor("000000"),
        HighlightColor("000000"),
        HighlightColor("000000")
    )

    @BeforeEach
    fun setUp() {
        highlightColorRepoImpl = HighlightColorRepoImpl(highlightColorDaoMock)
    }

    @Test
    fun getAllHighlightColors_shouldReturnCorrectValues_whenValuesInserted() {

        every { highlightColorDaoMock.getAllHighlightColors() } returns Observable.just(
            testHighlightColors
        )

        highlightColorRepoImpl.getAllHighlightColors().test().assertValue(testHighlightColors)

    }

    @Test
    fun getHighlightColorById_shouldReturnCorrectValue_whenValuesInserted() {

        val highlightColor = testHighlightColors[0]

        every { highlightColorDaoMock.getHighlightColorById(highlightColor.id) } returns Single.just(
            testHighlightColors.first { it.id == highlightColor.id })

        highlightColorRepoImpl.getHighlightColorById(highlightColor.id).test()
            .assertValue(highlightColor)

    }

    @Test
    fun insertHighlightColors_shouldCompleteSuccessfully() {

        every { highlightColorDaoMock.insertHighlightColors(testHighlightColors) } returns Completable.complete()

        highlightColorRepoImpl.insertHighlightColors(testHighlightColors).test().assertComplete()

    }

    @Test
    fun deleteHighlightColors_shouldCompleteSuccessfully() {

        every { highlightColorDaoMock.deleteHighlightColors(testHighlightColors) } returns Completable.complete()

        highlightColorRepoImpl.deleteHighlightColors(testHighlightColors).test().assertComplete()
    }
}