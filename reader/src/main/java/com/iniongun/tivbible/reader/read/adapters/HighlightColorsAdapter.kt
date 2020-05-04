package com.iniongun.tivbible.reader.read.adapters

/**
 * Created by Isaac Iniongun on 30/04/2020.
 * For Tiv Bible project.
 */

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iniongun.tivbible.entities.HighlightColor
import com.iniongun.tivbible.reader.databinding.SingleHighlightLayoutBinding
import com.iniongun.tivbible.reader.read.ReadViewModel
import com.iniongun.tivbible.reader.read.ReadViewModelNew

/**
 * Adapter for the task list. Has a reference to the [ReadViewModel] to send actions back to it.
 */
class HighlightColorsAdapter(private val viewModel: ReadViewModelNew) :
    ListAdapter<HighlightColor, HighlightColorsAdapter.ViewHolder>(HighlightColorsDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = getItem(position)
        holder.bind(viewModel, color)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: SingleHighlightLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ReadViewModelNew, color: HighlightColor) {
            binding.viewModel = viewModel
            binding.highlightColor = color
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SingleHighlightLayoutBinding.inflate(layoutInflater, parent, false)

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
class HighlightColorsDiffCallback : DiffUtil.ItemCallback<HighlightColor>() {
    override fun areItemsTheSame(oldItem: HighlightColor, newItem: HighlightColor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HighlightColor, newItem: HighlightColor): Boolean {
        return oldItem.id == newItem.id
    }
}