package com.iniongun.tivbible.roomdb.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iniongun.tivbible.entities.Version
import com.iniongun.tivbible.roomdb.database.TivBibleDatabase
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith

/**
 * Created by Isaac Iniongun on 2019-12-12.
 * For Tiv Bible project.
 */

@RunWith(AndroidJUnit4::class)
class VersionDaoTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mTivBibleDatabase: TivBibleDatabase

    //SUT
    private lateinit var versionDao: VersionDao

    private val newVersion = Version(name = "New")
    private val oldVersion = Version(name = "Old")

    @Before
    internal fun setUp() {
        // using an in-memory database because the information stored here disappears when the process is killed
        mTivBibleDatabase = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            TivBibleDatabase::class.java
        )
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

        versionDao = mTivBibleDatabase.versionDao()
    }

    @After
    internal fun tearDown() {
        mTivBibleDatabase.close()
    }

    @Test
    internal fun insertVersionAndGetVersionById_returnsInsertedVersion() {

        versionDao.insertVersions(newVersion).blockingAwait()

        val retrievedVersion = versionDao.getVersionById(newVersion.id).blockingGet()

        assertThat(newVersion.id, `is`(retrievedVersion.id))
        assertTrue(newVersion.name == retrievedVersion.name)

    }

    @Test
    internal fun insertVersion_shouldReplaceExistingVersionOnConflict() {

        versionDao.insertVersions(newVersion).blockingAwait()

        val version1 = Version(newVersion.id, "New Version")
        versionDao.insertVersions(version1).blockingAwait()

        val retrievedVersion = versionDao.getVersionById(newVersion.id).blockingGet()

        assertThat(retrievedVersion.name, `is`(version1.name))
        assertThat(retrievedVersion.id, `is`(version1.id))
        assertThat(retrievedVersion.id, `is`(newVersion.id))

    }

    @Test
    internal fun getVersions_shouldReturnEmpty_whenNoVersionsInserted() {

        val versions = versionDao.getAllVersions().blockingFirst()

        assertThat(versions.isNullOrEmpty(), `is`(true))

    }

    @Test
    internal fun getAllVersions_returnsCorrectNumberOfVersions() {

        versionDao.insertVersions(newVersion, oldVersion).blockingAwait()

        val retrievedVersions = versionDao.getAllVersions().blockingFirst()

        assertThat(2, `is`(retrievedVersions.count()))

    }

    @Test
    internal fun getVersionIdByName_shouldReturnVersion() {

        versionDao.insertVersions(newVersion).blockingAwait()

        val retrievedVersionId = versionDao.getVersionIdByName("new").blockingGet()

        assertThat(retrievedVersionId, `is`(newVersion.id))
    }

    @Test
    internal fun deleteAllVersionsAndGetAllVersions_shouldReturnEmpty() {

        versionDao.insertVersions(newVersion, oldVersion).blockingAwait()

        versionDao.deleteVersions(listOf(newVersion, oldVersion)).blockingAwait()

        val retrievedVersions = versionDao.getAllVersions().blockingFirst()

        assertThat(retrievedVersions.isNullOrEmpty(), `is`(true))

    }

    @Test
    internal fun deleteVersionById_shouldReturnNoVersion_whenRetrievedWithSameId() {

        versionDao.insertVersions(newVersion).blockingAwait()

        versionDao.deleteVersionById(newVersion.id).blockingAwait()

        assertThrows<EmptyResultSetException> { versionDao.getVersionById(newVersion.id).blockingGet() }

    }

}