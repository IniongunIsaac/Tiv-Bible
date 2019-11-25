package com.iniongun.tivbible.repository.room.other

import com.iniongun.tivbible.entities.Other
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface IOtherRoom {

    fun getAllOthers(): Observable<List<Other>>

    fun getOtherById(otherId: String): Single<Other>

    fun insertOthers(others: List<Other>): Completable

    fun deleteOthers(others: List<Other>): Completable

}