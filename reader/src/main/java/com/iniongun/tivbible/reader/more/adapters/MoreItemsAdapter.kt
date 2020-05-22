package com.iniongun.tivbible.reader.more.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iniongun.tivbible.reader.databinding.SingleMoreItemLayoutBinding
import com.iniongun.tivbible.reader.more.MoreViewModel
import com.iniongun.tivbible.reader.utils.MoreItem

/**
 * Created by Isaac Iniongun on 09/05/2020.
 * For Tiv Bible project.
 */

class MoreItemsAdapter(private val viewModel: MoreViewModel) :
    ListAdapter<MoreItem, MoreItemsAdapter.ViewHolder>(MoreItemsDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val moreItem = getItem(position)
        holder.bind(viewModel, moreItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: SingleMoreItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: MoreViewModel, moreItem: MoreItem) {
            binding.viewModel = viewModel
            binding.moreItem = moreItem
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SingleMoreItemLayoutBinding.inflate(layoutInflater, parent, false)

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
class MoreItemsDiffCallback : DiffUtil.ItemCallback<MoreItem>() {
    override fun areItemsTheSame(oldItem: MoreItem, newItem: MoreItem): Boolean {
        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: MoreItem, newItem: MoreItem): Boolean {
        return oldItem == newItem
    }
}