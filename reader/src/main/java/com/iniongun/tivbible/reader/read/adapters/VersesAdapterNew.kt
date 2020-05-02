package com.iniongun.tivbible.reader.read.adapters

/**
 * Created by Isaac Iniongun on 18/04/2020.
 * For Tiv Bible project.
 */

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.reader.databinding.VerseLayoutNewBinding
import com.iniongun.tivbible.reader.read.ReadViewModelNew

/**
 * Adapter for the task list. Has a reference to the [ReadViewModelNew] to send actions back to it.
 */
class VersesAdapterNew(private val viewModel: ReadViewModelNew) :
    ListAdapter<Verse, VersesAdapterNew.ViewHolder>(VersesNewDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val verse = getItem(position)
        holder.bind(viewModel, verse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: VerseLayoutNewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ReadViewModelNew, verse: Verse) {
            binding.viewModel = viewModel
            binding.verse = verse
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = VerseLayoutNewBinding.inflate(layoutInflater, parent, false)

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
class VersesNewDiffCallback : DiffUtil.ItemCallback<Verse>() {
    override fun areItemsTheSame(oldItem: Verse, newItem: Verse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Verse, newItem: Verse): Boolean {
        return oldItem == newItem
    }
}