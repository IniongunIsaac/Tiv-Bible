package com.iniongun.tivbible.roomdb.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iniongun.tivbible.entities.FontStyle
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
class FontStyleDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mTivBibleDatabase: TivBibleDatabase

    private lateinit var fontStyleDao: FontStyleDao

    private val testFontStyle = FontStyle(name = "Sans Serif")

    @Before
    internal fun setUp() {

        mTivBibleDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(), TivBibleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        fontStyleDao = mTivBibleDatabase.fontStyleDao()

    }

    @After
    internal fun tearDown() {
        mTivBibleDatabase.close()
    }

    @Test
    internal fun getAllFontStyles_shouldReturnNoValues_whenNoValuesInserted() {

        val speeds = fontStyleDao.getAllFontStyles().blockingFirst()

        assertTrue(speeds.isNullOrEmpty())

    }

    @Test
    internal fun getAllFontStyles_shouldReturnValues_whenValuesInserted() {

        fontStyleDao.insertFontStyles(testFontStyle).blockingAwait()

        val speeds = fontStyleDao.getAllFontStyles().blockingFirst()

        assertTrue(speeds.isNotEmpty())
        assertEquals(speeds[0].id, testFontStyle.id)
        assertEquals(speeds[0].name, testFontStyle.name)

    }

    @Test
    internal fun getFontStyleByName_shouldReturnNoValue_whenNoValueInserted() {

        assertThrows<EmptyResultSetException> { fontStyleDao.getFontStyleByName(testFontStyle.name).blockingGet() }

    }

    @Test
    internal fun getFontStyleByName_shouldReturnValue_whenValueInserted() {

        fontStyleDao.insertFontStyles(testFontStyle).blockingAwait()

        val fontStyle = fontStyleDao.getFontStyleByName(testFontStyle.name).blockingGet()

        assertNotNull(fontStyle)
        assertEquals(fontStyle.id, testFontStyle.id)
        assertEquals(fontStyle.name, testFontStyle.name)

    }

    @Test
    internal fun getFontStyleById_shouldReturnNoValue_whenNoValueInserted() {

        assertThrows<EmptyResultSetException> { fontStyleDao.getFontStyleById(testFontStyle.id).blockingGet() }

    }

    @Test
    internal fun getFontStyleById_shouldReturnValue_whenValueInserted() {

        fontStyleDao.insertFontStyles(testFontStyle).blockingAwait()

        val fontStyle = fontStyleDao.getFontStyleById(testFontStyle.id).blockingGet()

        assertNotNull(fontStyle)
        assertEquals(fontStyle.id, testFontStyle.id)
        assertEquals(fontStyle.name, testFontStyle.name)

    }

    @Test
    internal fun deleteFontStyles_shouldReturnNoValue_whenValuesInserted_andThenDeleted_andThenRetrieved() {

        fontStyleDao.insertFontStyles(testFontStyle).blockingAwait()

        fontStyleDao.deleteAllFontStyles().blockingAwait()

        assertThrows<EmptyResultSetException> { fontStyleDao.getFontStyleById(testFontStyle.id).blockingGet() }

    }

}