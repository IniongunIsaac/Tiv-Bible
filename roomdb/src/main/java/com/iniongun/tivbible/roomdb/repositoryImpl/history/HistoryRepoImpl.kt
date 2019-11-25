package com.iniongun.tivbible.roomdb.repositoryImpl.history

import com.iniongun.tivbible.entities.History
import com.iniongun.tivbible.repository.room.history.IHistoryRepo
import com.iniongun.tivbible.roomdb.dao.HistoryDao
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class HistoryRepoImpl @Inject constructor(
    private val historyDao: HistoryDao
): IHistoryRepo {

    override fun getAllHistory() = historyDao.getAllHistory()

    override fun getHistoryById(historyId: String) = historyDao.getHistoryById(historyId)

    override fun insertHistory(history: List<History>) = historyDao.insertHistory(history)

    override fun deleteHistory(history: List<History>) = historyDao.deleteHistory(history)

}