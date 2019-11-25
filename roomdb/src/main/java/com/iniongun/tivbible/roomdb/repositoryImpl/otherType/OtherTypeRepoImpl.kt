package com.iniongun.tivbible.roomdb.repositoryImpl.otherType

import com.iniongun.tivbible.entities.OtherType
import com.iniongun.tivbible.repository.room.otherType.IOtherTypeRepo
import com.iniongun.tivbible.roomdb.dao.OtherTypeDao
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class OtherTypeRepoImpl @Inject constructor(
    private val otherTypeDao: OtherTypeDao
): IOtherTypeRepo {

    override fun getAllOtherTypes() = otherTypeDao.getAllOtherTypes()

    override fun getOtherTypeById(otherTypeId: String) = otherTypeDao.getOtherTypeById(otherTypeId)

    override fun insertOtherTypes(otherTypes: List<OtherType>) = otherTypeDao.insertOtherTypes(otherTypes)

    override fun deleteOtherTypes(otherTypes: List<OtherType>) = otherTypeDao.deleteOtherTypes(otherTypes)

}