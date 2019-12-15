package com.iniongun.tivbible.roomdb.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iniongun.tivbible.entities.Testament
import com.iniongun.tivbible.roomdb.database.TivBibleDatabase
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith

/**
 * Created by Isaac Iniongun on 2019-12-12.
 * For Tiv Bible project.
 */

@RunWith(AndroidJUnit4::class)
class TestamentDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mTivBibleDatabase: TivBibleDatabase

    //SUT
    private lateinit var testamentDao: TestamentDao

    private val newTestament = Testament(name = "New")
    private val oldTestament = Testament(name = "Old")

    @Before
    internal fun setUp() {
        mTivBibleDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(), TivBibleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        testamentDao = mTivBibleDatabase.testamentDao()
    }

    @After
    internal fun tearDown() {
        mTivBibleDatabase.close()
    }

    @Test
    internal fun insertTestamentAndGetTestamentById_returnsInsertedTestament() {

        testamentDao.insertTestaments(newTestament).blockingAwait()

        val retrievedTestament = testamentDao.getTestamentById(newTestament.id).blockingGet()

        assertThat(newTestament.id, `is`(retrievedTestament.id))
        assertTrue(newTestament.name == retrievedTestament.name)

    }

    @Test
    internal fun insertTestament_shouldReplaceExistingTestamentOnConflict() {

        testamentDao.insertTestaments(newTestament).blockingAwait()

        val testament1 = Testament(newTestament.id, "New Testament")
        testamentDao.insertTestaments(testament1).blockingAwait()

        val retrievedTestament = testamentDao.getTestamentById(newTestament.id).blockingGet()

        assertThat(retrievedTestament.name, `is`(testament1.name))
        assertThat(retrievedTestament.id, `is`(testament1.id))
        assertThat(retrievedTestament.id, `is`(newTestament.id))

    }

    @Test
    internal fun getTestaments_shouldReturnEmpty_whenNoTestamentsInserted() {

        val testaments = testamentDao.getAllTestaments().blockingFirst()

        assertThat(testaments.isNullOrEmpty(), `is`(true))

    }

    @Test
    internal fun getAllTestaments_returnsCorrectNumberOfTestaments() {

        testamentDao.insertTestaments(newTestament, oldTestament).blockingAwait()

        val retrievedTestaments = testamentDao.getAllTestaments().blockingFirst()

        assertThat(2, `is`(retrievedTestaments.count()))

    }

    @Test
    internal fun getTestamentIdByName_shouldReturnTestament() {

        testamentDao.insertTestaments(newTestament).blockingAwait()

        val retrievedTestamentId = testamentDao.getTestamentIdByName("new").blockingGet()

        assertThat(retrievedTestamentId, `is`(newTestament.id))
    }

    @Test
    internal fun deleteAllTestamentsAndGetAllTestaments_shouldReturnEmpty() {

        testamentDao.insertTestaments(newTestament, oldTestament).blockingAwait()

        testamentDao.deleteTestaments(listOf(newTestament, oldTestament)).blockingAwait()

        val retrievedTestaments = testamentDao.getAllTestaments().blockingFirst()

        assertThat(retrievedTestaments.isNullOrEmpty(), `is`(true))

    }

    @Test
    internal fun deleteTestamentById_shouldReturnNoTestament_whenRetrievedWithSameId() {

        testamentDao.insertTestaments(newTestament).blockingAwait()

        testamentDao.deleteTestamentById(newTestament.id).blockingAwait()

        assertThrows<EmptyResultSetException> { testamentDao.getTestamentById(newTestament.id).blockingGet() }

    }

}