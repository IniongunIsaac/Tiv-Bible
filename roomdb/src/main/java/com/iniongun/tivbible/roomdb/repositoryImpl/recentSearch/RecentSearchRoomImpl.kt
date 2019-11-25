package com.iniongun.tivbible.roomdb.repositoryImpl.recentSearch

import com.iniongun.tivbible.entities.RecentSearch
import com.iniongun.tivbible.repository.room.recentSearch.IRecentSearchRoom
import com.iniongun.tivbible.roomdb.dao.RecentSearchDao
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class RecentSearchRoomImpl @Inject constructor(
    private val recentSearchDao: RecentSearchDao
): IRecentSearchRoom {

    override fun getAllRecentSearches() = recentSearchDao.getAllRecentSearches()

    override fun getRecentSearchById(recentSearchId: String) = recentSearchDao.getRecentSearchById(recentSearchId)

    override fun insertRecentSearches(recentSearches: List<RecentSearch>) = recentSearchDao.insertRecentSearches(recentSearches)

    override fun deleteRecentSearches(recentSearches: List<RecentSearch>) = recentSearchDao.deleteRecentSearches(recentSearches)

}