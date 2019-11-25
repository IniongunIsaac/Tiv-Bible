package com.iniongun.tivbible.roomdb.repositoryImpl.testament

import com.iniongun.tivbible.entities.Testament
import com.iniongun.tivbible.repository.room.testament.ITestamentRoom
import com.iniongun.tivbible.roomdb.dao.TestamentDao
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class TestamentRoomImpl @Inject constructor(
    private val testamentDao: TestamentDao
): ITestamentRoom {

    override fun getAllTestaments() = testamentDao.getAllTestaments()

    override fun getTestamentById(testamentId: String) = testamentDao.getTestamentById(testamentId)

    override fun insertTestaments(testaments: List<Testament>) = testamentDao.insertTestaments(testaments)

    override fun deleteTestaments(testaments: List<Testament>) = testamentDao.deleteTestaments(testaments)
}