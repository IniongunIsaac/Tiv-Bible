package com.iniongun.tivbible.roomdb.repositoryImpl.history

import com.iniongun.tivbible.entities.History
import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.roomdb.dao.HistoryDao
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
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
internal class HistoryRepoImplTest {

    //class under test
    private lateinit var historyRepoImpl: HistoryRepoImpl

    @MockK
    private lateinit var historyDaoMock: HistoryDao

    private val testVerse = Verse("123", 1, "Ter ngurumun mo", false, "")
    private val testVerse1 = Verse("321", 1, "Aondo ka Ter tswen", false, "")
    private val testHistories = listOf(
        History(testVerse),
        History(testVerse),
        History(testVerse1),
        History(testVerse1)
    )

    @BeforeEach
    fun setUp() {
        historyRepoImpl = HistoryRepoImpl(historyDaoMock)
    }

    @Test
    fun getAllHistory_shouldReturnCorrectValues_whenValuesInserted() {

        every { historyDaoMock.getAllHistory() } returns Observable.just(testHistories)

        historyRepoImpl.getAllHistory().test().assertValue(testHistories)

    }

    @Test
    fun getHistoryById_shouldReturnCorrectValue_whenValuesInserted() {

        val history = testHistories[0]

        every { historyDaoMock.getHistoryById(any()) } returns Single.just(testHistories.first { it.id == history.id })

        historyRepoImpl.getHistoryById(history.id).test().assertValue(history)

    }

    @Test
    fun insertHistory_shouldCompleteSuccessfully() {

        every { historyDaoMock.insertHistory(testHistories) } returns Completable.complete()

        historyRepoImpl.insertHistory(testHistories).test().assertComplete()

    }

    @Test
    fun deleteHistory_shouldCompleteSuccessfully() {

        every { historyDaoMock.deleteHistory(testHistories) } returns Completable.complete()

        historyRepoImpl.deleteHistory(testHistories).test().assertComplete()
    }
}