package com.iniongun.tivbible.roomdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.Testament
import io.reactivex.Completable
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

    @Query("select id from Testament where name = :testamentName limit 1")
    fun getTestamentIdByName(testamentName: String): Single<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTestaments(testaments: List<Testament>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTestaments(vararg testaments: Testament): Completable

    @Delete
    fun deleteTestaments(testaments: List<Testament>): Completable

}