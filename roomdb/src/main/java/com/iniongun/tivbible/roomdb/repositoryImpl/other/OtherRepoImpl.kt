package com.iniongun.tivbible.roomdb.repositoryImpl.other

import com.iniongun.tivbible.entities.Other
import com.iniongun.tivbible.repository.room.other.IOtherRepo
import com.iniongun.tivbible.roomdb.dao.OtherDao
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class OtherRepoImpl @Inject constructor(
    private val otherDao: OtherDao
): IOtherRepo {

    override fun getAllOthers() = otherDao.getAllOthers()

    override fun getOtherById(otherId: String) = otherDao.getOtherById(otherId)

    override fun insertOthers(others: List<Other>) = otherDao.insertOthers(others)

    override fun deleteOthers(others: List<Other>) = otherDao.deleteOthers(others)

}