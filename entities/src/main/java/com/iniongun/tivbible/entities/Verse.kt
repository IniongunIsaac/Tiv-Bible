package com.iniongun.tivbible.entities

import android.text.SpannedString
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Isaac Iniongun on 2019-07-21.
 * For TivBible project.
 */

@Entity(
    foreignKeys = [
        ForeignKey(entity = Chapter::class, parentColumns = ["id"], childColumns = ["chapter_id"])
    ]
)
data class Verse(
    @ColumnInfo(name = "chapter_id") val chapterId: String,
    val number: Int,
    val text: String,
    @ColumnInfo(name = "has_title") val hasTitle: Boolean,
    val title: String,
    @PrimaryKey val id: String = UUID.randomUUID().toString()
)

val Verse.formattedText: SpannedString
    get() = buildSpannedString {
        append("\t\t")
        bold { append("$number.\t") }
        append(text)
    }