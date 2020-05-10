package com.iniongun.tivbible.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by Isaac Iniongun on 2019-07-21.
 * For TivBible project.
 */

@Entity(primaryKeys = ["book_id", "chapter_id", "verse_number"])
data class Bookmark(
    @ColumnInfo(name = "bookmarked_on") val bookmarkedOn: OffsetDateTime,
    @Embedded(prefix = "verse_") val verse: Verse,
    @Embedded(prefix = "book_") val book: Book,
    @Embedded(prefix = "chapter_") val chapter: Chapter
) {
    @Ignore val bookNameAndChapterNumberAndVerseNumberString = "${book.name} ${chapter.chapterNumber}:${verse.number}"
    @Ignore val dateString = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(bookmarkedOn)
}