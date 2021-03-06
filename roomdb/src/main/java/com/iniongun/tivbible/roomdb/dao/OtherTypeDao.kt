package com.iniongun.tivbible.roomdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.OtherType
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

@Dao
interface OtherTypeDao {

    @Query("select * from OtherType")
    fun getAllOtherTypes(): Observable<List<OtherType>>

    @Query("select * from OtherType where id = :otherTypeId limit 1")
    fun getOtherTypeById(otherTypeId: String): Single<OtherType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOtherTypes(vararg otherTypes: OtherType): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOtherTypes(otherTypes: List<OtherType>): Completable

    @Delete
    fun deleteOtherTypes(otherTypes: List<OtherType>): Completable

    @Query("delete from OtherType")
    fun deleteOtherTypes(): Completable

}