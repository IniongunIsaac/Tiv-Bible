package com.iniongun.tivbible.roomdb.repositoryImpl.audioSpeed

import com.iniongun.tivbible.entities.AudioSpeed
import com.iniongun.tivbible.repository.room.audioSpeed.IAudioSpeedsRoom
import com.iniongun.tivbible.roomdb.dao.AudioSpeedDao
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class AudioSpeedsRoomImpl @Inject constructor(
    private val audioSpeedDao: AudioSpeedDao
): IAudioSpeedsRoom {

    override fun getAllAudioSpeeds() = audioSpeedDao.getAllAudioSpeeds()

    override fun getAudioSpeedById(audioSpeedId: String) = audioSpeedDao.getAudioSpeedById(audioSpeedId)

    override fun insertAudioSpeeds(audioSpeeds: List<AudioSpeed>) = audioSpeedDao.insertAudioSpeeds(audioSpeeds)

    override fun deleteAudioSpeeds(audioSpeeds: List<AudioSpeed>) = audioSpeedDao.deleteAudioSpeeds(audioSpeeds)

}