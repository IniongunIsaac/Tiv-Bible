package com.iniongun.tivbible.reader.more.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iniongun.tivbible.entities.Highlight
import com.iniongun.tivbible.reader.databinding.HighlightItemLayoutBinding
import com.iniongun.tivbible.reader.more.highlights.HighlightsViewModel

/**
 * Created by Isaac Iniongun on 09/05/2020.
 * For Tiv Bible project.
 */

class HighlightsAdapter(private val viewModel: HighlightsViewModel) :
    ListAdapter<Highlight, HighlightsAdapter.ViewHolder>(HighlightsDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val highlight = getItem(position)
        holder.bind(viewModel, highlight)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: HighlightItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: HighlightsViewModel, highlight: Highlight) {
            binding.viewModel = viewModel
            binding.highlight = highlight
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HighlightItemLayoutBinding.inflate(layoutInflater, parent, false)

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
class HighlightsDiffCallback : DiffUtil.ItemCallback<Highlight>() {
    override fun areItemsTheSame(oldItem: Highlight, newItem: Highlight): Boolean {
        return oldItem.verse.id == newItem.verse.id
    }

    override fun areContentsTheSame(oldItem: Highlight, newItem: Highlight): Boolean {
        return oldItem == newItem
    }
}