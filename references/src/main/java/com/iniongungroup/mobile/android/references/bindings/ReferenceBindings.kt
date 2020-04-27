package com.iniongungroup.mobile.android.references.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iniongun.tivbible.entities.Book
import com.iniongun.tivbible.entities.Chapter
import com.iniongungroup.mobile.android.references.adapters.BooksAdapter
import com.iniongungroup.mobile.android.references.adapters.ChaptersAdapter

/**
 * Created by Isaac Iniongun on 26/04/2020.
 * For Tiv Bible project.
 */

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Book>?) {
    items?.let {
        (listView.adapter as BooksAdapter).submitList(items)
    }
}

@BindingAdapter("app:items")
fun setChapterItems(listView: RecyclerView, items: List<Chapter>?) {
    items?.let {
        (listView.adapter as ChaptersAdapter).submitList(it)
    }
}