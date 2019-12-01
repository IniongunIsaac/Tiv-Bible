package com.iniongun.tivbible.roomdb.repositoryImpl.testament

import com.iniongun.tivbible.entities.Testament
import com.iniongun.tivbible.repository.room.testament.ITestamentRepo
import com.iniongun.tivbible.roomdb.dao.TestamentDao
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class TestamentRepoImpl @Inject constructor(
    private val testamentDao: TestamentDao
): ITestamentRepo {

    override fun getAllTestaments() = testamentDao.getAllTestaments()

    override fun getTestamentById(testamentId: String) = testamentDao.getTestamentById(testamentId)

    override fun insertTestaments(testaments: List<Testament>) = testamentDao.insertTestaments(testaments)

    override fun deleteTestaments(testaments: List<Testament>) = testamentDao.deleteTestaments(testaments)

    override fun getTestamentIdByName(testamentName: String) = testamentDao.getTestamentIdByName(testamentName)
}