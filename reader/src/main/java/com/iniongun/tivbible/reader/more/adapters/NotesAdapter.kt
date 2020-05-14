package com.iniongun.tivbible.reader.more.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iniongun.tivbible.entities.Note
import com.iniongun.tivbible.reader.databinding.NoteItemLayoutBinding
import com.iniongun.tivbible.reader.more.notes.NotesViewModel

/**
 * Created by Isaac Iniongun on 09/05/2020.
 * For Tiv Bible project.
 */

class NotesAdapter(private val viewModel: NotesViewModel) :
    ListAdapter<Note, NotesAdapter.ViewHolder>(NotesDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(viewModel, note)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: NoteItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: NotesViewModel, note: Note) {
            binding.viewModel = viewModel
            binding.note = note
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoteItemLayoutBinding.inflate(layoutInflater, parent, false)

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
class NotesDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}