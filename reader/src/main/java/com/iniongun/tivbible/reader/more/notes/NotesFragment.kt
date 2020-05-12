package com.iniongun.tivbible.reader.more.notes

import android.graphics.Typeface
import android.os.Bundle
import androidx.lifecycle.Observer
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.navigation.AppFragmentNavCommands
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.NotesFragmentBinding
import kotlinx.android.synthetic.main.notes_fragment.*
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        backButton.setOnClickListener { navigate(AppFragmentNavCommands.Back) }
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
        observeSettings()
    }

    private fun observeSettings() {
        notesViewModel.settings.observe(this, Observer {
            val typeface = Typeface.createFromAsset(activity!!.assets, "font/${it.fontStyle.name}")
            notesNotFoundTextView.typeface = typeface
            toolbarTitleTextView.typeface = typeface
        })
    }

}