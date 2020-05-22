package com.iniongun.tivbible.reader.more.notes

import android.graphics.Typeface
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongun.tivbible.common.utils.navigation.AppFragmentNavCommands
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.NotesFragmentBinding
import com.iniongun.tivbible.reader.home.HomeActivity
import com.iniongun.tivbible.reader.more.adapters.NotesAdapter
import com.iniongun.tivbible.reader.utils.TapAction
import com.iniongun.tivbible.reader.utils.copyDataToClipboard
import com.iniongun.tivbible.reader.utils.shareData
import kotlinx.android.synthetic.main.notes_fragment.*

/**
 * Created by Isaac Iniongun on 10/05/2020.
 * For Tiv Bible project.
 */

class NotesFragment : BaseFragment<NotesFragmentBinding, NotesViewModel>() {

    private val notesViewModel by lazy { (requireActivity() as HomeActivity).notesViewModel }

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
        observeNotes()
        observeTapActionData()
        observeShowReaderModule()
    }

    private fun observeSettings() {
        notesViewModel.settings.observe(this, Observer {
            val typeface = Typeface.createFromAsset(activity!!.assets, "font/${it.fontStyle.name}")
            notesNotFoundTextView.typeface = typeface
            toolbarTitleTextView.typeface = typeface
        })
    }

    private fun observeNotes() {
        notesViewModel.notes.observe(this, Observer {
            val notesAdapter = NotesAdapter(notesViewModel)
            notesRecyclerView.adapter = notesAdapter
            notesAdapter.submitList(it)
            switchNotesEmptyState(it.isEmpty())
        })
    }

    private fun observeShowReaderModule() {
        notesViewModel.showNoteDetails.observe(this, LiveDataEventObserver {
            if (it) navigate(AppFragmentNavCommands.To(NotesFragmentDirections.actionNotesFragmentToNoteDetailsFragment()))
        })
    }

    private fun observeTapActionData() {
        notesViewModel.tapActionData.observe(this, LiveDataEventObserver {
            when (it.first) {
                TapAction.SHARE -> { activity?.shareData(it.second.bookNameAndChapterNumberAndVerseNumbersString, it.second.formattedVersesText) }
                TapAction.COPY -> {
                    activity?.copyDataToClipboard(it.second.bookNameAndChapterNumberAndVerseNumbersString, it.second.formattedVersesText)
                    notesViewModel.postSuccessMessage("Verse(s) copied successfully!")
                }
            }
        })
    }

    private fun switchNotesEmptyState(state: Boolean) {
        showView(notesRecyclerView, !state)
        showView(notesNotFoundTextView, state)
    }

    override fun onResume() {
        super.onResume()
        notesViewModel.getNotesAndSettings()
    }

    override fun setNotificationObserver() {
        notesViewModel.notificationLiveData.observe(this, LiveDataEventObserver {

            when (it.state) {

                AppState.FAILED -> {
                    dismissLoadingDialog()
                    it.message?.let { message ->
                        showMessage(this.requireView(), message, isError = true)
                    }
                }

                AppState.WARNING -> {
                    dismissLoadingDialog()
                    it.message?.let { message ->
                        showMessage(this.requireView(), message, isWarning = true)
                    }

                }

                AppState.LOADING -> {
                    showLoadingDialog()
                }

                AppState.SUCCESS -> {
                    dismissLoadingDialog()
                    it.message?.let { message ->
                        showMessage(this.requireView(), message)
                    }

                }

            }

        })
    }

    override fun showLoadingDialog() { progressBar.visibility = VISIBLE }

    override fun dismissLoadingDialog() { progressBar.visibility = GONE }

}