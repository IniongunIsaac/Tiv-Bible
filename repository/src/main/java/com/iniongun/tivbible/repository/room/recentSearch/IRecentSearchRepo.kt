package com.iniongun.tivbible.repository.room.recentSearch

import com.iniongun.tivbible.entities.RecentSearch
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface IRecentSearchRepo {

    fun getAllRecentSearches(): Observable<List<RecentSearch>>

    fun getRecentSearchById(recentSearchId: String): Single<RecentSearch>

    fun insertRecentSearches(recentSearches: List<RecentSearch>): Completable

    fun deleteRecentSearches(recentSearches: List<RecentSearch>): Completable

}