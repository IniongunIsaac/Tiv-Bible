package com.iniongun.tivbible.entities

/**
 * Created by Isaac Iniongun on 2019-07-23.
 * For TivBible project.
 */

data class TivBibleData(
    val book: String,
    val chapter: Int,
    val verse: Int,
    val text: String,
    val title: String,
    val testament: String,
    val orderNo: Int
)