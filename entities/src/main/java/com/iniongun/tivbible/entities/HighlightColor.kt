package com.iniongun.tivbible.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Isaac Iniongun on 2019-07-21.
 * For TivBible project.
 */

@Entity
data class HighlightColor(
    @ColumnInfo(name = "hex_code") val hexCode: Int,
    @PrimaryKey val id: String = UUID.randomUUID().toString()
)