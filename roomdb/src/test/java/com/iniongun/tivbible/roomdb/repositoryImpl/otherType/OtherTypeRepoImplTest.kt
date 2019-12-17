package com.iniongun.tivbible.roomdb.repositoryImpl.otherType

import com.iniongun.tivbible.entities.OtherType
import com.iniongun.tivbible.roomdb.dao.OtherTypeDao
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
internal class OtherTypeRepoImplTest {

    //class under test
    private lateinit var otherTypeRepoImpl: OtherTypeRepoImpl

    @MockK
    private lateinit var otherTypeDaoMock: OtherTypeDao

    private val testOtherTypes = listOf(
        OtherType(name = "Atindi a pue"),
        OtherType(name = "Akaa a pue kar ahar"),
        OtherType(name = "Msen u Ter wase"),
        OtherType(name = "Fom u tebul")
    )

    @BeforeEach
    fun setUp() {
        otherTypeRepoImpl = OtherTypeRepoImpl(otherTypeDaoMock)
    }

    @Test
    fun getAllOtherTypes_shouldReturnCorrectValues_whenValuesInserted() {

        every { otherTypeDaoMock.getAllOtherTypes() } returns Observable.just(testOtherTypes)

        otherTypeRepoImpl.getAllOtherTypes().test().assertValue(testOtherTypes)

    }

    @Test
    fun getOtherTypeById_shouldReturnValue_whenValuesInserted() {

        val otherType = testOtherTypes[0]

        every { otherTypeDaoMock.getOtherTypeById(any()) } returns Single.just(testOtherTypes.first { it.id == otherType.id })

        otherTypeRepoImpl.getOtherTypeById(otherType.id).test().assertValue(otherType)

    }

    @Test
    fun insertOtherTypes_shouldCompleteSuccessfully() {

        every { otherTypeDaoMock.insertOtherTypes(testOtherTypes) } returns Completable.complete()

        otherTypeRepoImpl.insertOtherTypes(testOtherTypes).test().assertComplete()

    }

    @Test
    fun deleteOtherTypes_shouldCompleteSuccessfully() {

        every { otherTypeDaoMock.deleteOtherTypes(testOtherTypes) } returns Completable.complete()

        otherTypeRepoImpl.deleteOtherTypes(testOtherTypes).test().assertComplete()
    }
}