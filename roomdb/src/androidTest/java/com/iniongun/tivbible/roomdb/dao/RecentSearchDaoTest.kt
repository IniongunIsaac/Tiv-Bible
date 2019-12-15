package com.iniongun.tivbible.roomdb.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iniongun.tivbible.entities.RecentSearch
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
class RecentSearchDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mTivBibleDatabase: TivBibleDatabase

    private lateinit var recentSearchDao: RecentSearchDao

    private val testRecentSearch = RecentSearch("Ter ngu kaan nahan ye, umbur nen iyange i memen")

    @Before
    internal fun setUp() {

        mTivBibleDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(), TivBibleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        recentSearchDao = mTivBibleDatabase.recentSearchDao()

    }

    @After
    internal fun tearDown() {
        mTivBibleDatabase.close()
    }

    @Test
    internal fun getAllRecentSearches_shouldReturnNoValues_whenNoValuesInserted() {

        val recentSearches = recentSearchDao.getAllRecentSearches().blockingFirst()

        assertTrue(recentSearches.isNullOrEmpty())

    }

    @Test
    internal fun getAllRecentSearches_shouldReturnValues_whenValuesInserted() {

        recentSearchDao.insertRecentSearches(testRecentSearch).blockingAwait()

        val recentSearches = recentSearchDao.getAllRecentSearches().blockingFirst()

        assertTrue(recentSearches.isNotEmpty())
        assertEquals(recentSearches[0].id, testRecentSearch.id)

    }

    @Test
    internal fun getRecentSearchesById_shouldReturnNoValue_whenNoValueInserted() {

        assertThrows<EmptyResultSetException> { recentSearchDao.getRecentSearchById(testRecentSearch.id).blockingGet() }

    }

    @Test
    internal fun getRecentSearchesById_shouldReturnValue_whenValueInserted() {

        recentSearchDao.insertRecentSearches(testRecentSearch).blockingAwait()

        val recentSearch = recentSearchDao.getRecentSearchById(testRecentSearch.id).blockingGet()

        assertNotNull(recentSearch)
        assertEquals(recentSearch.id, testRecentSearch.id)

    }

    @Test
    internal fun deleteRecentSearches_shouldReturnNoValue_whenValuesInserted_andThenDeleted_andThenRetrieved() {

        recentSearchDao.insertRecentSearches(testRecentSearch).blockingAwait()

        recentSearchDao.deleteRecentSearches().blockingAwait()

        assertThrows<EmptyResultSetException> { recentSearchDao.getRecentSearchById(testRecentSearch.id).blockingGet() }

    }

}