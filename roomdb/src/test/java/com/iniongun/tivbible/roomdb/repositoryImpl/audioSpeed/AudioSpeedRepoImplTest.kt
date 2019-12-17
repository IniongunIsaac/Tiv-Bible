package com.iniongun.tivbible.roomdb.repositoryImpl.audioSpeed

import com.iniongun.tivbible.entities.AudioSpeed
import com.iniongun.tivbible.roomdb.dao.AudioSpeedDao
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
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
internal class AudioSpeedRepoImplTest {

    //class under test
    private lateinit var audioSpeedRepoImpl: AudioSpeedRepoImpl

    @MockK
    private lateinit var audioSpeedDaoMock: AudioSpeedDao

    private val testAudioSpeeds = listOf(
        AudioSpeed(name = "High"),
        AudioSpeed(name = "Medium"),
        AudioSpeed(name = "Low")
    )

    @BeforeEach
    fun setUp() { audioSpeedRepoImpl = AudioSpeedRepoImpl(audioSpeedDaoMock) }

    @Test
    fun getAllAudioSpeeds_shouldReturnValues_whenValuesAdded() {

        every { audioSpeedDaoMock.getAllAudioSpeeds() } returns Observable.just(testAudioSpeeds)

        audioSpeedRepoImpl.getAllAudioSpeeds().test().assertValue { it.count() == testAudioSpeeds.count() && it.containsAll(testAudioSpeeds) }

        verify { audioSpeedDaoMock.getAllAudioSpeeds() }

        confirmVerified(audioSpeedDaoMock)
    }

    @Test
    fun getAudioSpeedById_shouldReturnQueriedAudioSpeed() {
        val speed = testAudioSpeeds[0]
        every { audioSpeedDaoMock.getAudioSpeedById(any()) } returns Single.just(testAudioSpeeds.first { it.id == speed.id })

        audioSpeedRepoImpl.getAudioSpeedById(speed.id).test().assertValue(speed)

        verify { audioSpeedDaoMock.getAudioSpeedById(any()) }

        confirmVerified(audioSpeedDaoMock)
    }

    @Test
    fun insertAudioSpeeds_shouldInsertSuccessfully() {
        every { audioSpeedDaoMock.insertAudioSpeeds(testAudioSpeeds) } returns Completable.complete()

        audioSpeedRepoImpl.insertAudioSpeeds(testAudioSpeeds).test().assertComplete()

        verify { audioSpeedDaoMock.insertAudioSpeeds(testAudioSpeeds) }

        confirmVerified(audioSpeedDaoMock)
    }

    @Test
    fun deleteAudioSpeeds_shouldReturnEmptyAfterDelete() {
        every { audioSpeedDaoMock.deleteAudioSpeeds(testAudioSpeeds) } returns Completable.complete()
        every { audioSpeedDaoMock.getAllAudioSpeeds() } returns Observable.just(listOf())

        audioSpeedRepoImpl.deleteAudioSpeeds(testAudioSpeeds).test().assertComplete()
        audioSpeedRepoImpl.getAllAudioSpeeds().test().assertValue { it.isNullOrEmpty() }

        verify {
            audioSpeedDaoMock.deleteAudioSpeeds(testAudioSpeeds)
            audioSpeedRepoImpl.getAllAudioSpeeds()
        }

        confirmVerified(audioSpeedDaoMock)
    }
}