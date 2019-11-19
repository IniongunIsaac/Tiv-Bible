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
data class Other(
    @Embedded
    @ColumnInfo(name = "other_type")
    val otherType: OtherType,
    val text: String,
    @PrimaryKey val id: String = UUID.randomUUID().toString()
)