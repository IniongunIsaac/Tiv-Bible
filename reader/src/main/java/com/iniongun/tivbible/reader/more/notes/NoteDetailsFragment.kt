package com.iniongun.tivbible.reader.more.notes

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongun.tivbible.common.utils.navigation.AppFragmentNavCommands
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.NoteDetailsFragmentBinding
import com.iniongun.tivbible.reader.home.HomeActivity
import com.iniongun.tivbible.reader.utils.ModuleType
import com.iniongun.tivbible.reader.utils.TapAction
import com.iniongun.tivbible.reader.utils.copyDataToClipboard
import com.iniongun.tivbible.reader.utils.shareData
import kotlinx.android.synthetic.main.note_details_fragment.*

/**
 * Created by Isaac Iniongun on 14/05/2020.
 * For Tiv Bible project.
 */

class NoteDetailsFragment : BaseFragment<NoteDetailsFragmentBinding, NotesViewModel>() {

    private val notesViewModel by lazy { (requireActivity() as HomeActivity).notesViewModel }

    private lateinit var noteDetailsFragmentBinding: NoteDetailsFragmentBinding

    override fun getViewModel() = notesViewModel

    override fun getLayoutId() = R.layout.note_details_fragment

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: NoteDetailsFragmentBinding) {
        noteDetailsFragmentBinding = binding
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        backButton.setOnClickListener { navigate(AppFragmentNavCommands.Back) }
        readFullChapterButton.setOnClickListener { (requireActivity() as HomeActivity).showModule(ModuleType.READER) }
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
        observeSettings()
        observeShowNotes()
        observeTapActionData()
    }

    private fun observeSettings() {
        notesViewModel.settings.observe(this, Observer {
            val typeface = Typeface.createFromAsset(activity!!.assets, "font/${it.fontStyle.name}")
            toolbarTitleTextView.typeface = typeface
        })
    }

    private fun observeShowNotes() {
        notesViewModel.showNotes.observe(this, LiveDataEventObserver {
            if (it) navigate(AppFragmentNavCommands.Back)
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

    override fun showLoadingDialog() { progressBar.visibility = View.VISIBLE }

    override fun dismissLoadingDialog() { progressBar.visibility = View.GONE }
}