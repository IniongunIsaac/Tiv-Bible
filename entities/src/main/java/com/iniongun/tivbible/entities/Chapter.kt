package com.iniongun.tivbible.entities

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
        ForeignKey(entity = Book::class, parentColumns = ["id"], childColumns = ["book_id"])
    ]
)
data class Chapter(
    @ColumnInfo(name = "book_id") val bookId: String,
    @ColumnInfo(name = "chapter_number") val chapterNumber: Int,
    @ColumnInfo(name = "number_of_verses") val numberOfVerses: Int,
    @PrimaryKey val id: String = UUID.randomUUID().toString()
)