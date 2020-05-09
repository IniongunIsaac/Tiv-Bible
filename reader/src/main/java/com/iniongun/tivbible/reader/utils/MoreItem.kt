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
    BOOKMARKS, HIGHLIGHTS, CREED, COMMANDMENTS, LORDS_PRAYER, SETTINGS, SHARE, ABOUT, RATING, HELP
}

val moreItems = listOf(
    MoreItem(R.drawable.ic_bookmark, "My Bookmarks", BOOKMARKS),
    MoreItem(R.drawable.ic_highlight, "My Highlights", HIGHLIGHTS),
    MoreItem(R.drawable.ic_book, "Akar a puekarahar", CREED),
    MoreItem(R.drawable.ic_book, "Atindi a pue", COMMANDMENTS),
    MoreItem(R.drawable.ic_book, "Msen u Terwase", LORDS_PRAYER),
    MoreItem(R.drawable.ic_share, "Share Tiv Bible with others", SHARE),
    MoreItem(R.drawable.ic_info, "About Tiv Bible App", ABOUT),
    MoreItem(R.drawable.ic_rate, "Rating us on Playstore", RATING),
    MoreItem(R.drawable.ic_help, "Help", HELP),
    MoreItem(R.drawable.ic_settings, "Settings", SETTINGS)
)