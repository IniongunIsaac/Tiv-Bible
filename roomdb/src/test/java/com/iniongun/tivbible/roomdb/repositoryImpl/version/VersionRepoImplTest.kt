package com.iniongun.tivbible.roomdb.repositoryImpl.version

import com.iniongun.tivbible.entities.Version
import com.iniongun.tivbible.roomdb.dao.VersionDao
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
internal class VersionRepoImplTest {

    //class under test
    private lateinit var versionRepoImpl: VersionRepoImpl

    @MockK
    private lateinit var versionDaoMock: VersionDao

    private val testVersions = listOf(
        Version(name = "Old"),
        Version(name = "New")
    )

    @BeforeEach
    fun setUp() {
        versionRepoImpl = VersionRepoImpl(versionDaoMock)
    }

    @Test
    fun getAllVersions_shouldReturnCorrectValues_whenValuesInserted() {

        every { versionDaoMock.getAllVersions() } returns Observable.just(testVersions)

        versionRepoImpl.getAllVersions().test().assertValue(testVersions)
    }

    @Test
    fun getVersionById_shouldReturnValue_whenValuesInserted() {

        val version = testVersions[0]

        every { versionDaoMock.getVersionById(any()) } returns Single.just(testVersions.first { it.id == version.id })

        versionRepoImpl.getVersionById(version.id).test().assertValue(version)
    }

    @Test
    fun insertVersions_shouldCompleteSuccessfully() {

        every { versionDaoMock.insertVersions(testVersions) } returns Completable.complete()

        versionRepoImpl.insertVersions(testVersions).test().assertComplete()
    }

    @Test
    fun deleteVersions_shouldCompleteSuccessfully() {

        every { versionDaoMock.deleteVersions(testVersions) } returns Completable.complete()

        versionRepoImpl.deleteVersions(testVersions).test().assertComplete()
    }

    @Test
    fun getVersionIdByName_shouldReturnValue_whenValuesInserted() {

        val version = testVersions[0]

        every { versionDaoMock.getVersionIdByName(any()) } returns Single.just(testVersions.first { it.name == version.name }.id)

        versionRepoImpl.getVersionIdByName(version.name).test().assertValue(version.id)
    }
}