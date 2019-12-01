package com.iniongun.tivbible.repository.room.version

import com.iniongun.tivbible.entities.Version
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface IVersionRepo {

    fun getAllVersions(): Observable<List<Version>>

    fun getVersionById(versionId: String): Single<Version>

    fun insertVersions(versions: List<Version>): Completable

    fun deleteVersions(versions: List<Version>): Completable

    fun getVersionIdByName(versionName: String): Single<String>

}