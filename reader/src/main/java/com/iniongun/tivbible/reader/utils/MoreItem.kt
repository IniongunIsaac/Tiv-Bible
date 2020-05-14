package com.iniongun.tivbible.reader.utils

import androidx.annotation.DrawableRes
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.utils.MoreItemType.*

/**
 * Created by Isaac Iniongun on 09/05/2020.
 * For Tiv Bible project.
 */

data class MoreItem(@DrawableRes val imageRes: Int, val title: String, val type: MoreItemType)

enum class MoreItemType {
    BOOKMARKS, HIGHLIGHTS, NOTES, CREED, COMMANDMENTS, LORDS_PRAYER, SETTINGS, SHARE, ABOUT, RATING, HELP
}

val moreItems = listOf(
    MoreItem(R.drawable.ic_bookmark, "My Bookmarks", BOOKMARKS),
    MoreItem(R.drawable.ic_highlight, "My Highlights", HIGHLIGHTS),
    MoreItem(R.drawable.ic_notes, "My Notes", NOTES),
    MoreItem(R.drawable.ic__book, "Akar A Puekarahar", CREED),
    MoreItem(R.drawable.ic__book, "Atindi A Pue", COMMANDMENTS),
    MoreItem(R.drawable.ic__book, "Msen U Terwase", LORDS_PRAYER),
    MoreItem(R.drawable.ic_share, "Share Tiv Bible App With Friends", SHARE),
//    MoreItem(R.drawable.ic_info, "About Tiv Bible App", ABOUT),
    MoreItem(R.drawable.ic_rate, "Rate us on Playstore", RATING),
//    MoreItem(R.drawable.ic_help, "Help", HELP),
    MoreItem(R.drawable.ic_settings, "Settings", SETTINGS)
)