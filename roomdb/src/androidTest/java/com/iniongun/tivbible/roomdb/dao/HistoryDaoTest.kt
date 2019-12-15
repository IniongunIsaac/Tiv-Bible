package com.iniongun.tivbible.roomdb.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iniongun.tivbible.entities.History
import com.iniongun.tivbible.entities.Verse
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
class HistoryDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mTivBibleDatabase: TivBibleDatabase

    private lateinit var historyDao: HistoryDao

    private val testVerse = Verse("123", 1, "Ter ngurumun mo", false, "")
    private val testHistory = History(testVerse)

    @Before
    internal fun setUp() {

        mTivBibleDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(), TivBibleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        historyDao = mTivBibleDatabase.historyDao()

    }

    @After
    internal fun tearDown() {
        mTivBibleDatabase.close()
    }

    @Test
    internal fun getAllHistory_shouldReturnNoValues_whenNoValuesInserted() {

        val histories = historyDao.getAllHistory().blockingFirst()

        assertTrue(histories.isNullOrEmpty())

    }

    @Test
    internal fun getAllHistory_shouldReturnValues_whenValuesInserted() {

        historyDao.insertHistory(testHistory).blockingAwait()

        val histories = historyDao.getAllHistory().blockingFirst()

        assertTrue(histories.isNotEmpty())
        assertEquals(histories[0].id, testHistory.id)

    }

    @Test
    internal fun getHistoryById_shouldReturnNoValue_whenNoValueInserted() {

        assertThrows<EmptyResultSetException> { historyDao.getHistoryById(testHistory.id).blockingGet() }

    }

    @Test
    internal fun getHistoryById_shouldReturnValue_whenValueInserted() {

        historyDao.insertHistory(testHistory).blockingAwait()

        val history = historyDao.getHistoryById(testHistory.id).blockingGet()

        assertNotNull(history)
        assertEquals(history.id, testHistory.id)

    }

    @Test
    internal fun deleteHistory_shouldReturnNoValue_whenValuesInserted_andThenDeleted_andThenRetrieved() {

        historyDao.insertHistory(testHistory).blockingAwait()

        historyDao.deleteHistories().blockingAwait()

        assertThrows<EmptyResultSetException> { historyDao.getHistoryById(testHistory.id).blockingGet() }

    }

}