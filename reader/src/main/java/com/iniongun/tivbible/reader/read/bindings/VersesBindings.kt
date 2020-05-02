package com.iniongun.tivbible.reader.read.bindings

import android.graphics.Paint
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.reader.read.adapters.ChaptersAdapter
import com.iniongun.tivbible.reader.read.adapters.HighlightColorsAdapter
import com.iniongun.tivbible.reader.read.adapters.VersesAdapter
import com.iniongun.tivbible.reader.read.adapters.VersesAdapterNew


/**
 * Created by Isaac Iniongun on 18/04/2020.
 * For Tiv Bible project.
 */

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Verse>?) {
    items?.let {
        (listView.adapter as VersesAdapter).submitList(items)
    }
}

@BindingAdapter("app:items")
fun setItemsNew(listView: RecyclerView, items: List<Verse>?) {
    items?.let {
        (listView.adapter as VersesAdapterNew).submitList(items)
    }
}

@BindingAdapter("app:items")
fun setItems(viewPager: ViewPager2, items: List<Verse>?) {
    items?.let {
        (viewPager.adapter as ChaptersAdapter).submitList(items)
    }
}

@BindingAdapter("app:items")
fun setHighlightColorItems(recyclerView: RecyclerView, items: List<Int>?) {
    items?.let {
        (recyclerView.adapter as HighlightColorsAdapter).submitList(items)
    }
}

@BindingAdapter("app:formattedText")
fun setFormattedText(textView: AppCompatTextView, verse: Verse) {
    textView.text = with(verse) {
        buildSpannedString {
            append("\t\t")
            bold { append("$number.") }
            append(text)
        }
    }
}

@BindingAdapter("android:background")
fun setBackgroundColor(button: MaterialButton, @ColorRes color: Int) {
    button.setBackgroundColor(ContextCompat.getColor(button.context, color))
}

@BindingAdapter("android:background")
fun setDottedLineBackground(textView: AppCompatTextView, enabled: Boolean) {
    if (enabled) {
        //textView.text = HtmlCompat.fromHtml("<u>${textView.text}</u>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    } else {
        textView.paintFlags = textView.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
        //textView.text = textView.text.toString().replace("<u>", "").replace("</u>", "")
    }
}