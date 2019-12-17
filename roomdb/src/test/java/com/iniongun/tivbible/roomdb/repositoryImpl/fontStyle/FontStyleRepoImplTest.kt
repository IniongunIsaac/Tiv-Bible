package com.iniongun.tivbible.roomdb.repositoryImpl.fontStyle

import com.iniongun.tivbible.entities.FontStyle
import com.iniongun.tivbible.roomdb.dao.FontStyleDao
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
internal class FontStyleRepoImplTest {

    //class under test
    private lateinit var fontStyleRepoImpl: FontStyleRepoImpl

    @MockK
    private lateinit var fontStyleDaoMock: FontStyleDao

    private val testFontStyles = listOf(
        FontStyle(name = "Sans Serif"),
        FontStyle(name = "Lucida Console"),
        FontStyle(name = "Monospace"),
        FontStyle(name = "Georgia")
    )

    @BeforeEach
    fun setUp() { fontStyleRepoImpl = FontStyleRepoImpl(fontStyleDaoMock) }

    @Test
    fun getAllFontStyles_shouldReturnCorrectValues_whenValuesAdded() {

        every { fontStyleDaoMock.getAllFontStyles() } returns Observable.just(testFontStyles)

        fontStyleRepoImpl.getAllFontStyles().test().assertValue(testFontStyles)

    }

    @Test
    fun getFontStyleByName_shouldReturnCorrectValue_whenValuesAdded() {

        val fontStyle = testFontStyles[0]

        every { fontStyleDaoMock.getFontStyleByName(any()) } returns Single.just(testFontStyles.first { it.name.contains(fontStyle.name, true) })

        fontStyleRepoImpl.getFontStyleByName(fontStyle.name).test().assertValue(fontStyle)

    }

    @Test
    fun getFontStyleById_shouldReturnCorrectValue_whenValueAdded() {

        val fontStyle = testFontStyles[0]

        every { fontStyleDaoMock.getFontStyleById(any()) } returns Single.just(testFontStyles.first { it.id == fontStyle.id })

        fontStyleRepoImpl.getFontStyleById(fontStyle.id).test().assertValue(fontStyle)

    }

    @Test
    fun insertFontStyles_shouldInsertSuccessfully() {

        every { fontStyleDaoMock.insertFontStyles(testFontStyles) } returns Completable.complete()

        fontStyleRepoImpl.insertFontStyles(testFontStyles).test().assertComplete()

    }

    @Test
    fun deleteFontStyles_shouldDeleteSuccessfully() {

        every { fontStyleDaoMock.deleteFontStyles(testFontStyles) } returns Completable.complete()

        fontStyleRepoImpl.deleteFontStyles(testFontStyles).test().assertComplete()

    }
}