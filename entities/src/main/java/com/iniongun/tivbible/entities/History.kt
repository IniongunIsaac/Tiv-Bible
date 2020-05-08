package com.iniongun.tivbible.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by Isaac Iniongun on 2019-07-21.
 * For TivBible project.
 */

@Entity(primaryKeys = ["chapter_id"])
data class History(
    @Embedded(prefix = "chapter_") val chapter: Chapter,
    @Embedded(prefix = "book_") val book: Book,
    @ColumnInfo(name = "book_name_and_chapter_number") val bookNameAndChapterNumber: String,
    @ColumnInfo(name = "history_date") val historyDate: OffsetDateTime = OffsetDateTime.now(ZoneId.systemDefault())
    //@PrimaryKey val id: String = UUID.randomUUID().toString()
) {
    @Ignore val dateString = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(historyDate) //historyDate.format(SimpleDateFormat(). )// .toLocalDate().toString()
}