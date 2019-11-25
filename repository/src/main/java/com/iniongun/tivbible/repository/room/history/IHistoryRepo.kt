package com.iniongun.tivbible.repository.room.history

import com.iniongun.tivbible.entities.History
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface IHistoryRepo {

    fun getAllHistory(): Observable<List<History>>

    fun getHistoryById(historyId: String): Single<History>

    fun insertHistory(history: List<History>): Completable

    fun deleteHistory(history: List<History>): Completable

}