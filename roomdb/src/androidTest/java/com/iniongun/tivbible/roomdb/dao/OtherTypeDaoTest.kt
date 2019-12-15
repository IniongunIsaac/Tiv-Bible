package com.iniongun.tivbible.roomdb.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
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
class OtherTypeDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mTivBibleDatabase: TivBibleDatabase

    private lateinit var otherTypeDao: OtherTypeDao

    private val testOtherType = OtherType(name = "Atindi a pue")

    @Before
    internal fun setUp() {

        mTivBibleDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(), TivBibleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        otherTypeDao = mTivBibleDatabase.otherTypeDao()

    }

    @After
    internal fun tearDown() {
        mTivBibleDatabase.close()
    }

    @Test
    internal fun getAllOtherTypes_shouldReturnNoValues_whenNoValuesInserted() {

        val otherTypes = otherTypeDao.getAllOtherTypes().blockingFirst()

        assertTrue(otherTypes.isNullOrEmpty())

    }

    @Test
    internal fun getAllOtherTypes_shouldReturnValues_whenValuesInserted() {

        otherTypeDao.insertOtherTypes(testOtherType).blockingAwait()

        val otherTypes = otherTypeDao.getAllOtherTypes().blockingFirst()

        assertTrue(otherTypes.isNotEmpty())
        assertEquals(otherTypes[0].id, testOtherType.id)

    }

    @Test
    internal fun getOtherTypesById_shouldReturnNoValue_whenNoValueInserted() {

        assertThrows<EmptyResultSetException> { otherTypeDao.getOtherTypeById(testOtherType.id).blockingGet() }

    }

    @Test
    internal fun getOtherTypesById_shouldReturnValue_whenValueInserted() {

        otherTypeDao.insertOtherTypes(testOtherType).blockingAwait()

        val otherType = otherTypeDao.getOtherTypeById(testOtherType.id).blockingGet()

        assertNotNull(otherType)
        assertEquals(otherType.id, testOtherType.id)

    }

    @Test
    internal fun deleteOtherTypes_shouldReturnNoValue_whenValuesInserted_andThenDeleted_andThenRetrieved() {

        otherTypeDao.insertOtherTypes(testOtherType).blockingAwait()

        otherTypeDao.deleteOtherTypes().blockingAwait()

        assertThrows<EmptyResultSetException> { otherTypeDao.getOtherTypeById(testOtherType.id).blockingGet() }

    }

}