package com.iniongun.tivbible.roomdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.RecentSearch
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

@Dao
interface RecentSearchDao {

    @Query("select * from RecentSearch")
    fun getAllRecentSearches(): Observable<List<RecentSearch>>

    @Query("select * from RecentSearch where id = :recentSearchId limit 1")
    fun getRecentSearchById(recentSearchId: String): Single<RecentSearch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentSearches(vararg recentSearches: RecentSearch): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentSearches(recentSearches: List<RecentSearch>): Completable

    @Delete
    fun deleteRecentSearches(recentSearches: List<RecentSearch>): Completable

}