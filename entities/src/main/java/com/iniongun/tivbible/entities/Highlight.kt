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
data class Highlight(
    @ColumnInfo(name = "highlighted_on") val highlightedOn: OffsetDateTime,
    @Embedded val color: HighlightColor,
    @Embedded val verse: Verse,
    @PrimaryKey val id: String = UUID.randomUUID().toString()
)