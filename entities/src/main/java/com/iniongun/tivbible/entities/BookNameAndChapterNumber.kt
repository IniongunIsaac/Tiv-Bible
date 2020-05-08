package com.iniongun.tivbible.entities

/**
 * Created by Isaac Iniongun on 07/05/2020.
 * For Tiv Bible project.
 */

data class BookNameAndChapterNumber(val bookName: String, val chapterNumber: Int, val chapterId: String)

val BookNameAndChapterNumber.bookNameAndChapterNumber: String get() = "$bookName : $chapterNumber"