package com.iniongun.tivbible.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import org.threeten.bp.OffsetDateTime

/**
 * Created by Isaac Iniongun on 2019-07-21.
 * For TivBible project.
 */

@Entity(primaryKeys = ["verse_id", "verse_number"])
data class Highlight(
    @ColumnInfo(name = "highlighted_on") val highlightedOn: OffsetDateTime,
    @Embedded(prefix = "highlight_color_") val color: HighlightColor,
    @Embedded(prefix = "verse_") val verse: Verse
    //val id: String = UUID.randomUUID().toString()
)