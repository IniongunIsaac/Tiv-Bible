package com.iniongun.tivbible.entities

import androidx.room.*
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Isaac Iniongun on 2019-07-21.
 * For TivBible project.
 */

@Entity
data class Note(
    @ColumnInfo(name = "taken_on") val takenOn: OffsetDateTime,
    val verses: List<Verse>,
    @Embedded(prefix = "book_") val book: Book,
    @Embedded(prefix = "chapter_") val chapter: Chapter,
    val comment: String,
    @PrimaryKey val id: String = UUID.randomUUID().toString()
) {
    @Ignore
    val bookNameAndChapterNumberAndVerseNumbersString = "${book.name} ${chapter.chapterNumber}:${getFormattedVerseNumbers()}"
    @Ignore val formattedVersesText = ArrayList(verses).sortedBy { it.number }.joinToString("\n") { "${it.number}.\t${it.text}" }
    @Ignore val dateString = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(takenOn)

    private fun getFormattedVerseNumbers(): String {
        val verses = ArrayList(verses).sortedBy { it.number }.map { it.number }
        var left: Int? = null
        var right: Int? = null
        val groups = arrayListOf<String>()

        for (index in verses.first()..verses.last() + 1) {
            if (verses.contains(index)) {
                if (left == null) {
                    left = index
                } else {
                    right = index
                }
            } else {
                if (left == null) continue
                val leftx = left
                if (right != null) {
                    groups.add("$leftx - $right")
                } else {
                    groups.add("$leftx")
                }
                left = null
                right = null
            }
        }

        return groups.joinToString(separator = ", ")
    }
}