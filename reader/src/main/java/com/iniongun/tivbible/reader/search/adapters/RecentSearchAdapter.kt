package com.iniongun.tivbible.reader.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iniongun.tivbible.entities.RecentSearch
import com.iniongun.tivbible.reader.databinding.SingleRecentSearchLayoutBinding
import com.iniongun.tivbible.reader.search.SearchViewModel

/**
 * Created by Isaac Iniongun on 26/04/2020.
 * For Tiv Bible project.
 */

/**
 * Adapter for the task list. Has a reference to the [SearchViewModel] to send actions back to it.
 */
class RecentSearchAdapter(private val viewModel: SearchViewModel) :
    ListAdapter<RecentSearch, RecentSearchAdapter.ViewHolder>(RecentSearchDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recentSearch = getItem(position)
        holder.bind(viewModel, recentSearch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: SingleRecentSearchLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: SearchViewModel, recentSearch: RecentSearch) {
            binding.viewModel = viewModel
            binding.recentSearch = recentSearch
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SingleRecentSearchLayoutBinding.inflate(layoutInflater, parent, false)

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
class RecentSearchDiffCallback : DiffUtil.ItemCallback<RecentSearch>() {
    override fun areItemsTheSame(oldItem: RecentSearch, newItem: RecentSearch): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(oldItem: RecentSearch, newItem: RecentSearch): Boolean {
        return oldItem == newItem
    }
}