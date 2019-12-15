package com.iniongun.tivbible.roomdb.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iniongun.tivbible.entities.Other
import com.iniongun.tivbible.entities.OtherType
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
class OtherDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mTivBibleDatabase: TivBibleDatabase

    private lateinit var otherDao: OtherDao

    private val testOtherType = OtherType(name = "Atindi a pue")
    private val testOther = Other(testOtherType, "Sha hiihii la, Aondo gba sha man tar")

    @Before
    internal fun setUp() {

        mTivBibleDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(), TivBibleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        otherDao = mTivBibleDatabase.otherDao()

    }

    @After
    internal fun tearDown() {
        mTivBibleDatabase.close()
    }

    @Test
    internal fun getAllOthers_shouldReturnNoValues_whenNoValuesInserted() {

        val others = otherDao.getAllOthers().blockingFirst()

        assertTrue(others.isNullOrEmpty())

    }

    @Test
    internal fun getAllOthers_shouldReturnValues_whenValuesInserted() {

        otherDao.insertOthers(testOther).blockingAwait()

        val others = otherDao.getAllOthers().blockingFirst()

        assertTrue(others.isNotEmpty())
        assertEquals(others[0].id, testOther.id)

    }

    @Test
    internal fun getOthersById_shouldReturnNoValue_whenNoValueInserted() {

        assertThrows<EmptyResultSetException> { otherDao.getOtherById(testOther.id).blockingGet() }

    }

    @Test
    internal fun getOthersById_shouldReturnValue_whenValueInserted() {

        otherDao.insertOthers(testOther).blockingAwait()

        val other = otherDao.getOtherById(testOther.id).blockingGet()

        assertNotNull(other)
        assertEquals(other.id, testOther.id)

    }

    @Test
    internal fun deleteOthers_shouldReturnNoValue_whenValuesInserted_andThenDeleted_andThenRetrieved() {

        otherDao.insertOthers(testOther).blockingAwait()

        otherDao.deleteOthers().blockingAwait()

        assertThrows<EmptyResultSetException> { otherDao.getOtherById(testOther.id).blockingGet() }

    }

}