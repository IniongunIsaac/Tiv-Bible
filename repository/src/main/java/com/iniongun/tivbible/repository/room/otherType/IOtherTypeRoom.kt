package com.iniongun.tivbible.repository.room.otherType

import com.iniongun.tivbible.entities.OtherType
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface IOtherTypeRoom {

    fun getAllOtherTypes(): Observable<List<OtherType>>

    fun getOtherTypeById(otherTypeId: String): Single<OtherType>

    fun insertOtherTypes(otherTypes: List<OtherType>): Completable

    fun deleteOtherTypes(otherTypes: List<OtherType>): Completable

}