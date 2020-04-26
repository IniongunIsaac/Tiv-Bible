package com.iniongungroup.mobile.android.references.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iniongun.tivbible.entities.Book
import com.iniongungroup.mobile.android.references.adapters.BooksAdapter

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