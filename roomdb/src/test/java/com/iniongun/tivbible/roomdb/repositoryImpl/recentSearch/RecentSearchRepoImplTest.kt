package com.iniongun.tivbible.roomdb.repositoryImpl.recentSearch

import com.iniongun.tivbible.entities.RecentSearch
import com.iniongun.tivbible.roomdb.dao.RecentSearchDao
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
internal class RecentSearchRepoImplTest {

    //class under test
    private lateinit var recentSearchRepoImpl: RecentSearchRepoImpl

    @MockK
    private lateinit var recentSearchDaoMock: RecentSearchDao

    private val testRecentSearches = listOf(
        RecentSearch("Yesu vaa"),
        RecentSearch("Tso Aondo kaa er"),
        RecentSearch("Ter gba sha man tar man zegemnger kua akaa a lu ke a ve cii"),
        RecentSearch("Yesu gema kaa a na er:"),
        RecentSearch("civir teru man ngou")
    )

    @BeforeEach
    fun setUp() {
        recentSearchRepoImpl = RecentSearchRepoImpl(recentSearchDaoMock)
    }

    @Test
    fun getAllRecentSearches_shouldReturnCorrectValues_whenValuesInserted() {

        every { recentSearchDaoMock.getAllRecentSearches() } returns Observable.just(testRecentSearches)

        recentSearchRepoImpl.getAllRecentSearches().test().assertValue(testRecentSearches)

    }

    @Test
    fun getRecentSearchById_shouldReturnValue_whenValuesInserted() {

        val recentSearch = testRecentSearches[0]

        every { recentSearchDaoMock.getRecentSearchById(any()) } returns Single.just(testRecentSearches.first { it.id == recentSearch.id })

        recentSearchRepoImpl.getRecentSearchById(recentSearch.id).test().assertValue(recentSearch)
    }

    @Test
    fun insertRecentSearches_shouldCompleteSuccessfully() {

        every { recentSearchDaoMock.insertRecentSearches(testRecentSearches) } returns Completable.complete()

        recentSearchRepoImpl.insertRecentSearches(testRecentSearches).test().assertComplete()
    }

    @Test
    fun deleteRecentSearches_shouldCompleteSuccessfully() {

        every { recentSearchDaoMock.deleteRecentSearches(testRecentSearches) } returns Completable.complete()

        recentSearchRepoImpl.deleteRecentSearches(testRecentSearches).test().assertComplete()
    }
}