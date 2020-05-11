package com.iniongun.tivbible.reader.more.highlights

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
import com.iniongun.tivbible.reader.databinding.HighlightsFragmentBinding
import com.iniongun.tivbible.reader.home.HomeActivity
import com.iniongun.tivbible.reader.more.adapters.HighlightsAdapter
import com.iniongun.tivbible.reader.utils.ModuleType
import com.iniongun.tivbible.reader.utils.TapAction
import com.iniongun.tivbible.reader.utils.copyDataToClipboard
import com.iniongun.tivbible.reader.utils.shareData
import kotlinx.android.synthetic.main.highlights_fragment.*
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 09/05/2020.
 * For Tiv Bible project.
 */

class HighlightsFragment : BaseFragment<HighlightsFragmentBinding, HighlightsViewModel>() {

    @Inject
    lateinit var highlightsViewModel: HighlightsViewModel

    private lateinit var highlightsFragmentBinding: HighlightsFragmentBinding

    override fun getViewModel() = highlightsViewModel

    override fun getLayoutId() = R.layout.highlights_fragment

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: HighlightsFragmentBinding) {
        highlightsFragmentBinding = binding
    }

    private val highlightsAdapter by lazy { HighlightsAdapter(highlightsViewModel) }

    private val homeActivity by lazy { (requireActivity() as HomeActivity) }

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
        observeHighlights()
        observeShowReaderModule()
        observeTapActionData()
    }

    private fun observeSettings() {
        highlightsViewModel.settings.observe(this, Observer {
            toolbarTitleTextView.typeface = Typeface.createFromAsset(activity!!.assets, "font/${it.fontStyle.name}")
            highlightsAdapter.notifyDataSetChanged()
        })
    }

    private fun observeHighlights() {
        highlightsViewModel.highlights.observe(this, Observer {
            highlightsRecyclerView.adapter = highlightsAdapter
            highlightsAdapter.submitList(it)
            switchHighlightsEmptyState(it.isEmpty())
        })
    }

    private fun observeShowReaderModule() {
        highlightsViewModel.showReaderModule.observe(this, LiveDataEventObserver {
            if (it) homeActivity.showModule(ModuleType.READER)
        })
    }

    private fun observeTapActionData() {
        highlightsViewModel.tapActionData.observe(this, Observer {
            when (it.first) {
                TapAction.SHARE -> { activity?.shareData(it.second.bookNameAndChapterNumberAndVerseNumberString, it.second.verse.text) }
                TapAction.COPY -> {
                    activity?.copyDataToClipboard(it.second.bookNameAndChapterNumberAndVerseNumberString, it.second.verse.text)
                    highlightsViewModel.postSuccessMessage("Highlighted verse copied successfully!")
                }
            }
        })
    }

    private fun switchHighlightsEmptyState(state: Boolean) {
        showView(highlightsRecyclerView, !state)
        showView(highlightsNotFoundTextView, state)
    }

    override fun setNotificationObserver() {
        highlightsViewModel.notificationLiveData.observe(this, LiveDataEventObserver {

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

    override fun showLoadingDialog() { progressBar.visibility = View.VISIBLE
    }

    override fun dismissLoadingDialog() { progressBar.visibility = View.GONE
    }
}