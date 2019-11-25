package com.iniongun.tivbible.repository.room.audioSpeed

import com.iniongun.tivbible.entities.AudioSpeed
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface IAudioSpeedRepo {

    fun getAllAudioSpeeds(): Observable<List<AudioSpeed>>

    fun getAudioSpeedById(audioSpeedId: String): Single<AudioSpeed>

    fun insertAudioSpeeds(audioSpeeds: List<AudioSpeed>): Completable

    fun deleteAudioSpeeds(audioSpeeds: List<AudioSpeed>): Completable

}