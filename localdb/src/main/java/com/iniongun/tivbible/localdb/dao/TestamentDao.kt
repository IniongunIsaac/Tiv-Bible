package com.iniongun.tivbible.localdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.Testament
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

@Dao
interface TestamentDao {

    @Query("select * from Testament")
    fun getAllTestaments(): Observable<List<Testament>>

    @Query("select * from Testament where id = :testamentId limit 1")
    fun getTestamentById(testamentId: String): Single<Testament>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTestaments(testaments: List<Testament>): Maybe<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTestaments(vararg testaments: Testament): Completable

    @Delete
    fun deleteTestaments(testaments: List<Testament>): Single<Int>

}