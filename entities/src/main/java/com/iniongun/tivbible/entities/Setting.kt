package com.iniongun.tivbible.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Isaac Iniongun on 2019-07-21.
 * For TivBible project.
 */

@Entity
data class Setting(
    @ColumnInfo(name = "font_size") val fontSize: Int,
    @ColumnInfo(name = "line_spacing")val lineSpacing: Int,
    @Embedded
    @ColumnInfo(name = "font_style")
    val fontStyle: FontStyle,
    val theme: Theme,
    @ColumnInfo(name = "stay_awake")val stayAwake: Boolean,
    @Embedded
    @ColumnInfo(name = "audio_speed")
    val audioSpeed: AudioSpeed,
    @PrimaryKey val id: String = UUID.randomUUID().toString()
)