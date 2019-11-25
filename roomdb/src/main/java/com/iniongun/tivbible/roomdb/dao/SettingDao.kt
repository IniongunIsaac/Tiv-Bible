package com.iniongun.tivbible.roomdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.Setting
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

@Dao
interface SettingDao {

    @Query("select * from Setting")
    fun getAllSettings(): Observable<List<Setting>>

    @Query("select * from Setting where id = :settingId limit 1")
    fun getSettingById(settingId: String): Single<Setting>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSettings(vararg settings: Setting): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSettings(settings: List<Setting>): Completable

    @Delete
    fun deleteSettings(settings: List<Setting>): Completable

}