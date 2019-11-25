package com.iniongun.tivbible.repository.room.testament

import com.iniongun.tivbible.entities.Testament
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface ITestamentRoom {

    fun getAllTestaments(): Observable<List<Testament>>

    fun getTestamentById(testamentId: String): Single<Testament>

    fun insertTestaments(testaments: List<Testament>): Completable

    fun deleteTestaments(testaments: List<Testament>): Completable

}