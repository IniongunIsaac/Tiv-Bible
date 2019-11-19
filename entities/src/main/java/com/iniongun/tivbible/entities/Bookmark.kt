package com.iniongun.tivbible.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime
import java.util.*

/**
 * Created by Isaac Iniongun on 2019-07-21.
 * For TivBible project.
 */

@Entity
data class Bookmark(
    @ColumnInfo(name = "bookmarked_on") val bookmarkedOn: OffsetDateTime,
    @Embedded(prefix = "verse_") val verse: Verse,
    @PrimaryKey val id: String = UUID.randomUUID().toString()
)