package com.iniongun.tivbible.reader.search.bindings

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.iniongun.tivbible.entities.BookAndChapterAndVerse
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.search.adapters.BookAndChapterAndVerseAdapter

/**
 * Created by Isaac Iniongun on 08/05/2020.
 * For Tiv Bible project.
 */

@BindingAdapter("app:selectedBackgroundColor")
fun setSelectedBackgroundColor(button: MaterialButton, selected: Boolean) {
    if (selected) {
        button.setBackgroundColor(ContextCompat.getColor(button.context, R.color.colorPrimary))
        button.setTextColor(ContextCompat.getColor(button.context, R.color.selected_chapter_color))
    }
    else {
        button.setBackgroundColor(ContextCompat.getColor(button.context, android.R.color.transparent))
        button.setTextColor(ContextCompat.getColor(button.context, R.color.unselected_chapter_color))
    }

}

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<BookAndChapterAndVerse>?) {
    items?.let {
        (listView.adapter as BookAndChapterAndVerseAdapter).submitList(items)
    }
}