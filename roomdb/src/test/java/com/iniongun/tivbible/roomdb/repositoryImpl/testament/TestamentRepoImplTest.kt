package com.iniongun.tivbible.roomdb.repositoryImpl.testament

import com.iniongun.tivbible.entities.Testament
import com.iniongun.tivbible.roomdb.dao.TestamentDao
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
internal class TestamentRepoImplTest {

    //class under test
    private lateinit var testamentRepoImpl: TestamentRepoImpl

    @MockK
    private lateinit var testamentDaoMock: TestamentDao

    private val testTestaments = listOf(
        Testament(name = "Old"),
        Testament(name = "New")
    )

    @BeforeEach
    fun setUp() {
        testamentRepoImpl = TestamentRepoImpl(testamentDaoMock)
    }

    @Test
    fun getAllTestaments_shouldReturnCorrectValues_whenValuesInserted() {

        every { testamentDaoMock.getAllTestaments() } returns Observable.just(testTestaments)

        testamentRepoImpl.getAllTestaments().test().assertValue(testTestaments)
    }

    @Test
    fun getTestamentById_shouldReturnValue_whenValuesInserted() {

        val testament = testTestaments[0]

        every { testamentDaoMock.getTestamentById(any()) } returns Single.just(testTestaments.first { it.id == testament.id })

        testamentRepoImpl.getTestamentById(testament.id).test().assertValue(testament)
    }

    @Test
    fun insertTestaments_shouldCompleteSuccessfully() {

        every { testamentDaoMock.insertTestaments(testTestaments) } returns Completable.complete()

        testamentRepoImpl.insertTestaments(testTestaments).test().assertComplete()
    }

    @Test
    fun deleteTestaments_shouldCompleteSuccessfully() {

        every { testamentDaoMock.deleteTestaments(testTestaments) } returns Completable.complete()

        testamentRepoImpl.deleteTestaments(testTestaments).test().assertComplete()
    }

    @Test
    fun getTestamentIdByName_shouldReturnValue_whenValuesInserted() {

        val testament = testTestaments[0]

        every { testamentDaoMock.getTestamentIdByName(any()) } returns Single.just(testTestaments.first { it.name == testament.name }.id)

        testamentRepoImpl.getTestamentIdByName(testament.name).test().assertValue(testament.id)
    }
}