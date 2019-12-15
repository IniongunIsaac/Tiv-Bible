package com.iniongun.tivbible.roomdb.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iniongun.tivbible.entities.Theme
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
class ThemeDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mTivBibleDatabase: TivBibleDatabase

    private lateinit var themeDao: ThemeDao

    private val testTheme = Theme(name = "Dark")

    @Before
    internal fun setUp() {

        mTivBibleDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(), TivBibleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        themeDao = mTivBibleDatabase.themeDao()

    }

    @After
    internal fun tearDown() {
        mTivBibleDatabase.close()
    }

    @Test
    internal fun getAllThemes_shouldReturnNoValues_whenNoValuesInserted() {

        val themes = themeDao.getAllThemes().blockingFirst()

        assertTrue(themes.isNullOrEmpty())

    }

    @Test
    internal fun getAllThemes_shouldReturnValues_whenValuesInserted() {

        themeDao.insertThemes(testTheme).blockingAwait()

        val themes = themeDao.getAllThemes().blockingFirst()

        assertTrue(themes.isNotEmpty())
        assertEquals(themes[0].id, testTheme.id)

    }

    @Test
    internal fun getThemesById_shouldReturnNoValue_whenNoValueInserted() {

        assertThrows<EmptyResultSetException> { themeDao.getThemeById(testTheme.id).blockingGet() }

    }

    @Test
    internal fun getThemesById_shouldReturnValue_whenValueInserted() {

        themeDao.insertThemes(testTheme).blockingAwait()

        val theme = themeDao.getThemeById(testTheme.id).blockingGet()

        assertNotNull(theme)
        assertEquals(theme.id, testTheme.id)

    }

    @Test
    internal fun deleteThemes_shouldReturnNoValue_whenValuesInserted_andThenDeleted_andThenRetrieved() {

        themeDao.insertThemes(testTheme).blockingAwait()

        themeDao.deleteThemes().blockingAwait()

        assertThrows<EmptyResultSetException> { themeDao.getThemeById(testTheme.id).blockingGet() }

    }

}