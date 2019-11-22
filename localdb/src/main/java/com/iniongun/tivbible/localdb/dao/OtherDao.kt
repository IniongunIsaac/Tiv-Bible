package com.iniongun.tivbible.localdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.Other
import io.reactivex.Completable
import io.reactivex.Maybe
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOthers(vararg others: Other): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOthers(others: List<Other>): Maybe<Int>

    @Delete
    fun deleteHistory(others: List<Other>): Single<Int>

}