package com.iniongun.tivbible.roomdb.repositoryImpl.other

import com.iniongun.tivbible.entities.Other
import com.iniongun.tivbible.entities.OtherType
import com.iniongun.tivbible.roomdb.dao.OtherDao
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
internal class OtherRepoImplTest {

    //class under test
    private lateinit var otherRepoImpl: OtherRepoImpl

    @MockK
    private lateinit var otherDaoMock: OtherDao

    private val testOtherType = OtherType(name = "Atindi a pue")
    private val testOthers = listOf(
        Other(testOtherType, "De lu a mba aondo mba genev ga saa mo"),
        Other(testOtherType, "De gbe eev ga, shin kwagh u a bee ma kwagh u alu sha kwavaondo"),
        Other(testOtherType, "De iin ga"),
        Other(testOtherType, "De eren idya ga")
    )

    @BeforeEach
    fun setUp() {
        otherRepoImpl = OtherRepoImpl(otherDaoMock)
    }

    @Test
    fun getAllOthers_shouldReturnCorrectValues_whenValuesInserted() {

        every { otherDaoMock.getAllOthers() } returns Observable.just(testOthers)

        otherRepoImpl.getAllOthers().test().assertValue(testOthers)

    }

    @Test
    fun getOtherById_shouldReturnValue_whenValuesInserted() {

        val other = testOthers[0]

        every { otherDaoMock.getOtherById(any()) } returns Single.just(testOthers.first { it.id == other.id })

        otherRepoImpl.getOtherById(other.id).test().assertValue(other)

    }

    @Test
    fun insertOthers_shouldCompleteSuccessfully() {

        every { otherDaoMock.insertOthers(testOthers) } returns Completable.complete()

        otherRepoImpl.insertOthers(testOthers).test().assertComplete()

    }

    @Test
    fun deleteOthers_shouldCompleteSuccessfully() {

        every { otherDaoMock.deleteOthers(testOthers) } returns Completable.complete()

        otherRepoImpl.deleteOthers(testOthers).test().assertComplete()
    }
}