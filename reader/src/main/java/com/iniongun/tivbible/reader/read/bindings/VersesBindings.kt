package com.iniongun.tivbible.reader.read.bindings

import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.reader.read.adapters.ChaptersAdapter
import com.iniongun.tivbible.reader.read.adapters.VersesAdapter

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
fun setItems(viewPager: ViewPager2, items: List<Verse>?) {
    items?.let {
        (viewPager.adapter as ChaptersAdapter).submitList(items)
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