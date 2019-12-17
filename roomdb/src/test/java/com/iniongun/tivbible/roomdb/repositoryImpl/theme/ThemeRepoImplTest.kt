package com.iniongun.tivbible.roomdb.repositoryImpl.theme

import com.iniongun.tivbible.entities.Theme
import com.iniongun.tivbible.roomdb.dao.ThemeDao
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
internal class ThemeRepoImplTest {

    //class under test
    private lateinit var themeRepoImpl: ThemeRepoImpl

    @MockK
    private lateinit var themeDaoMock: ThemeDao

    private val testThemes = listOf(
        Theme(name = "Dark"),
        Theme(name = "Light")
    )

    @BeforeEach
    fun setUp() {
        themeRepoImpl = ThemeRepoImpl(themeDaoMock)
    }

    @Test
    fun getAllThemes_shouldReturnCorrectValues_whenValuesInserted() {

        every { themeDaoMock.getAllThemes() } returns Observable.just(testThemes)

        themeRepoImpl.getAllThemes().test().assertValue(testThemes)
    }

    @Test
    fun getThemeById_shouldReturnValue_whenValuesInserted() {

        val theme = testThemes[0]

        every { themeDaoMock.getThemeById(any()) } returns Single.just(testThemes.first { it.id == theme.id })

        themeRepoImpl.getThemeById(theme.id).test().assertValue(theme)
    }

    @Test
    fun insertThemes_shouldCompleteSuccessfully() {

        every { themeDaoMock.insertThemes(testThemes) } returns Completable.complete()

        themeRepoImpl.insertThemes(testThemes).test().assertComplete()
    }

    @Test
    fun deleteThemes_shouldCompleteSuccessfully() {

        every { themeDaoMock.deleteThemes(testThemes) } returns Completable.complete()

        themeRepoImpl.deleteThemes(testThemes).test().assertComplete()
    }
}