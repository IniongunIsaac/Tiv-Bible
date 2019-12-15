package com.iniongun.tivbible.roomdb.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iniongun.tivbible.entities.AudioSpeed
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
class AudioSpeedDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mTivBibleDatabase: TivBibleDatabase

    private lateinit var audioSpeedDao: AudioSpeedDao

    private val testAudioSpeed = AudioSpeed(name = "High")

    @Before
    internal fun setUp() {

        mTivBibleDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(), TivBibleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        audioSpeedDao = mTivBibleDatabase.audioSpeedDao()

    }

    @After
    internal fun tearDown() {
        mTivBibleDatabase.close()
    }

    @Test
    internal fun getAllAudioSpeeds_shouldReturnNoValues_whenNoValuesInserted() {

        val speeds = audioSpeedDao.getAllAudioSpeeds().blockingFirst()

        assertTrue(speeds.isNullOrEmpty())

    }

    @Test
    internal fun getAllAudioSpeeds_shouldReturnValues_whenValuesInserted() {

        audioSpeedDao.insertAudioSpeeds(testAudioSpeed).blockingAwait()

        val speeds = audioSpeedDao.getAllAudioSpeeds().blockingFirst()

        assertTrue(speeds.isNotEmpty())
        assertEquals(speeds[0].id, testAudioSpeed.id)
        assertEquals(speeds[0].name, testAudioSpeed.name)

    }

    @Test
    internal fun getAudioSpeedById_shouldReturnNoValue_whenNoValueInserted() {

        assertThrows<EmptyResultSetException> { audioSpeedDao.getAudioSpeedById(testAudioSpeed.id).blockingGet() }

    }

    @Test
    internal fun getAudioSpeedById_shouldReturnValue_whenValueInserted() {

        audioSpeedDao.insertAudioSpeeds(testAudioSpeed).blockingAwait()

        val speed = audioSpeedDao.getAudioSpeedById(testAudioSpeed.id).blockingGet()

        assertNotNull(speed)
        assertEquals(speed.id, testAudioSpeed.id)
        assertEquals(speed.name, testAudioSpeed.name)

    }

    @Test
    internal fun deleteAudioSpeeds_shouldReturnNoValue_whenValuesInserted_andThenDeleted_andThenRetrieved() {

        audioSpeedDao.insertAudioSpeeds(testAudioSpeed).blockingAwait()

        audioSpeedDao.deleteAudioSpeeds().blockingAwait()

        assertThrows<EmptyResultSetException> { audioSpeedDao.getAudioSpeedById(testAudioSpeed.id).blockingGet() }

    }

}