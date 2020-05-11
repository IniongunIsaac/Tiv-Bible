package com.iniongun.tivbible.reader.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity

/**
 * Created by Isaac Iniongun on 08/05/2020.
 * For Tiv Bible project.
 */

fun FragmentActivity.shareData(subject: String, content: String) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
    shareIntent.putExtra(Intent.EXTRA_TEXT, content)
    startActivity(Intent.createChooser(shareIntent, "Share Via"))
}

fun FragmentActivity.copyDataToClipboard(subject: String, content: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(subject, content)
    clipboard.setPrimaryClip(clip)
}