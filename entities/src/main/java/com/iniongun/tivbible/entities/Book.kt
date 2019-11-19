package com.iniongun.tivbible.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * Created by Isaac Iniongun on 2019-07-19.
 * For TivBible project.
 */

@Entity(
    foreignKeys = [
        ForeignKey(entity = Testament::class, parentColumns = ["id"], childColumns = ["testament_id"]),
        ForeignKey(entity = Version::class, parentColumns = ["id"], childColumns = ["version_id"])
    ]
)
data class Book(
    @ColumnInfo(name = "testament_id") val testamentId: String,
    @ColumnInfo(name = "version_id") val versionId: String,
    @ColumnInfo(name = "order_no") val orderNo: Int,
    @ColumnInfo(name = "number_of_chapters") val numberOfChapters: Int,
    @ColumnInfo(name = "number_of_verses") val numberOfVerses: Int
) : Base()