package com.iniongun.tivbible.reader.read.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.reader.databinding.ChapterLayoutBinding
import com.iniongun.tivbible.reader.read.ReadViewModel

/**
 * Created by Isaac Iniongun on 18/04/2020.
 * For Tiv Bible project.
 */

class ChaptersAdapter(private val viewModel: ReadViewModel) :
    ListAdapter<Verse, ChaptersAdapter.ViewHolder>(ChaptersDiffCallback()) {
    var versesAdapter: VersesAdapter? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val verse = getItem(position)
        holder.bind(viewModel, verse)
        versesAdapter = holder.versesAdapter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ChapterLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var versesAdapter: VersesAdapter? = null

        fun bind(viewModel: ReadViewModel, verse: Verse) {
            viewModel.getCurrentVerses(verse.chapterId)
            binding.viewModel = viewModel
            versesAdapter = VersesAdapter(viewModel)
            binding.versesRecyclerView.adapter = versesAdapter
            binding.executePendingBindings()

            binding.versesRecyclerView.post {
                binding.versesRecyclerView.smoothScrollToPosition(viewModel.verseNum)
            }

            binding.versesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE -> viewModel.setVersesRecyclerViewTouched(true)
                        RecyclerView.SCROLL_STATE_DRAGGING -> {}
                        RecyclerView.SCROLL_STATE_SETTLING -> {}
                    }
                }
            })

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChapterLayoutBinding.inflate(layoutInflater, parent, false)

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
class ChaptersDiffCallback : DiffUtil.ItemCallback<Verse>() {
    override fun areItemsTheSame(oldItem: Verse, newItem: Verse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Verse, newItem: Verse): Boolean {
        return oldItem == newItem
    }
}