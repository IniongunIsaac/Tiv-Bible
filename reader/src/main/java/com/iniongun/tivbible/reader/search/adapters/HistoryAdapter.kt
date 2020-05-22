package com.iniongun.tivbible.reader.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iniongun.tivbible.entities.History
import com.iniongun.tivbible.reader.databinding.SingleHistoryLayoutBinding
import com.iniongun.tivbible.reader.search.SearchViewModel

/**
 * Created by Isaac Iniongun on 26/04/2020.
 * For Tiv Bible project.
 */

/**
 * Adapter for the task list. Has a reference to the [SearchViewModel] to send actions back to it.
 */
class HistoryAdapter(private val viewModel: SearchViewModel) :
    ListAdapter<History, HistoryAdapter.ViewHolder>(HistoryDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(viewModel, history)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: SingleHistoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: SearchViewModel, history: History) {
            binding.viewModel = viewModel
            binding.history = history
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SingleHistoryLayoutBinding.inflate(layoutInflater, parent, false)

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
class HistoryDiffCallback : DiffUtil.ItemCallback<History>() {
    override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem.chapter.id == newItem.chapter.id
    }

    override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem == newItem
    }
}