package com.iniongun.tivbible.localdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.Version
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

@Dao
interface VersionDao {

    @Query("select * from Version")
    fun getAllVersions(): Observable<List<Version>>

    @Query("select * from Version where id = :versionId limit 1")
    fun getVersionById(versionId: String): Single<Version>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVersions(vararg versions: Version): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVersions(versions: List<Version>): Maybe<Int>

    @Delete
    fun deleteVersions(versions: List<Version>): Single<Int>

}