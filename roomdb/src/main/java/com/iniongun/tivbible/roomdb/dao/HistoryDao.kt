package com.iniongun.tivbible.roomdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.History
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

@Dao
interface HistoryDao {

    @Query("select * from History")
    fun getAllHistory(): Observable<List<History>>

    @Query("select * from History where chapter_id = :historyId limit 1")
    fun getHistoryById(historyId: String): Single<History>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(vararg history: History): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: List<History>): Completable

    @Delete
    fun deleteHistory(history: List<History>): Completable

    @Query("delete from History")
    fun deleteHistories(): Completable

}