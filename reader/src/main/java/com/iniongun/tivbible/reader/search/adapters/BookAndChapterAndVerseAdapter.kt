package com.iniongun.tivbible.reader.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iniongun.tivbible.entities.BookAndChapterAndVerse
import com.iniongun.tivbible.reader.databinding.SingleSearchResultLayoutBinding
import com.iniongun.tivbible.reader.search.SearchViewModel

/**
 * Created by Isaac Iniongun on 26/04/2020.
 * For Tiv Bible project.
 */

/**
 * Adapter for the task list. Has a reference to the [SearchViewModel] to send actions back to it.
 */
class BookAndChapterAndVerseAdapter(private val viewModel: SearchViewModel) :
    ListAdapter<BookAndChapterAndVerse, BookAndChapterAndVerseAdapter.ViewHolder>(BookAndChapterAndVerseDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookAndChapterAndVerse = getItem(position)
        holder.bind(viewModel, bookAndChapterAndVerse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: SingleSearchResultLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: SearchViewModel, bookAndChapterAndVerse: BookAndChapterAndVerse) {
            binding.viewModel = viewModel
            binding.bookAndChapterAndVerse = bookAndChapterAndVerse
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SingleSearchResultLayoutBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class BookAndChapterAndVerseDiffCallback : DiffUtil.ItemCallback<BookAndChapterAndVerse>() {
    override fun areItemsTheSame(oldItem: BookAndChapterAndVerse, newItem: BookAndChapterAndVerse): Boolean {
        return oldItem.verseId == newItem.verseId
    }

    override fun areContentsTheSame(oldItem: BookAndChapterAndVerse, newItem: BookAndChapterAndVerse): Boolean {
        return oldItem == newItem
    }
}