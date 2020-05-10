package com.iniongun.tivbible.reader.more.notes

import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.NotesFragmentBinding
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 10/05/2020.
 * For Tiv Bible project.
 */

class NotesFragment : BaseFragment<NotesFragmentBinding, NotesViewModel>() {

    @Inject
    lateinit var notesViewModel: NotesViewModel

    private lateinit var notesFragmentBinding: NotesFragmentBinding

    override fun getViewModel() = notesViewModel

    override fun getLayoutId() = R.layout.notes_fragment

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: NotesFragmentBinding) {
        notesFragmentBinding = binding
    }

}