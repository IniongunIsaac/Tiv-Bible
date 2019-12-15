package com.iniongun.tivbible.roomdb.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iniongun.tivbible.entities.HighlightColor
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
class HighlightColorDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mTivBibleDatabase: TivBibleDatabase

    private lateinit var highlightColorDao: HighlightColorDao

    private val testHighlightColor = HighlightColor("000000")

    @Before
    internal fun setUp() {

        mTivBibleDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(), TivBibleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        highlightColorDao = mTivBibleDatabase.highlightColorDao()

    }

    @After
    internal fun tearDown() {
        mTivBibleDatabase.close()
    }

    @Test
    internal fun getAllHighlightColors_shouldReturnNoValues_whenNoValuesInserted() {

        val highlightColors = highlightColorDao.getAllHighlightColors().blockingFirst()

        assertTrue(highlightColors.isNullOrEmpty())

    }

    @Test
    internal fun getAllHighlightColors_shouldReturnValues_whenValuesInserted() {

        highlightColorDao.insertHighlightColors(testHighlightColor).blockingAwait()

        val highlightColors = highlightColorDao.getAllHighlightColors().blockingFirst()

        assertTrue(highlightColors.isNotEmpty())
        assertEquals(highlightColors[0].id, testHighlightColor.id)
        assertEquals(highlightColors[0].hexCode, testHighlightColor.hexCode)

    }

    @Test
    internal fun getHighlightColorById_shouldReturnNoValue_whenNoValueInserted() {

        assertThrows<EmptyResultSetException> { highlightColorDao.getHighlightColorById(testHighlightColor.id).blockingGet() }

    }

    @Test
    internal fun getHighlightColorById_shouldReturnValue_whenValueInserted() {

        highlightColorDao.insertHighlightColors(testHighlightColor).blockingAwait()

        val highlightColor = highlightColorDao.getHighlightColorById(testHighlightColor.id).blockingGet()

        assertNotNull(highlightColor)
        assertEquals(highlightColor.id, testHighlightColor.id)
        assertEquals(highlightColor.hexCode, testHighlightColor.hexCode)

    }

    @Test
    internal fun deleteHighlightColors_shouldReturnNoValue_whenValuesInserted_andThenDeleted_andThenRetrieved() {

        highlightColorDao.insertHighlightColors(testHighlightColor).blockingAwait()

        highlightColorDao.deleteAllHighlightColors().blockingAwait()

        assertThrows<EmptyResultSetException> { highlightColorDao.getHighlightColorById(testHighlightColor.id).blockingGet() }

    }

}