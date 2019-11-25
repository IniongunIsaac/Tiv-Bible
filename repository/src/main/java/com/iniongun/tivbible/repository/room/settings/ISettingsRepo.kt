package com.iniongun.tivbible.repository.room.settings

import com.iniongun.tivbible.entities.Setting
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface ISettingsRepo {

    fun getAllSettings(): Observable<List<Setting>>

    fun getSettingById(settingId: String): Single<Setting>

    fun insertSettings(settings: List<Setting>): Completable

    fun deleteSettings(settings: List<Setting>): Completable

}