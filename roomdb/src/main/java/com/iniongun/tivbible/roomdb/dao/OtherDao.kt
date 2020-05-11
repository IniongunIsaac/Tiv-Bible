package com.iniongun.tivbible.roomdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.Other
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

@Dao
interface OtherDao {

    @Query("select * from Other")
    fun getAllOthers(): Observable<List<Other>>

    @Query("select * from Other where id = :otherId limit 1")
    fun getOtherById(otherId: String): Single<Other>

    @Query("select * from Other where title like '%' || :searchText || '%' limit 1")
    fun getOtherByText(searchText: String): Single<Other>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOthers(vararg others: Other): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOthers(others: List<Other>): Completable

    @Delete
    fun deleteOthers(others: List<Other>): Completable

    @Query("delete from Other")
    fun deleteOthers(): Completable

}