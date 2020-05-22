package com.iniongun.tivbible.entities

import androidx.core.text.HtmlCompat
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Isaac Iniongun on 2019-07-21.
 * For TivBible project.
 */
@Entity
data class Other(
    val title: String,
    val subTitle: String,
    val text: String,
    @PrimaryKey val id: String = UUID.randomUUID().toString()
) {
    @Ignore val formattedText = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
}