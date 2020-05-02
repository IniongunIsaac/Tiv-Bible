package com.iniongun.tivbible.reader.read

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongun.tivbible.common.utils.navigation.AppActivityNavCommands
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.FragmentReadNewBinding
import com.iniongun.tivbible.reader.home.HomeActivity
import com.iniongun.tivbible.reader.read.adapters.VersesAdapterNew
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class ReadFragmentNew : BaseFragment<FragmentReadNewBinding, ReadViewModelNew>() {

    @Inject
    lateinit var readViewModel: ReadViewModelNew

    private lateinit var fragmentReadNewBinding: FragmentReadNewBinding

    override fun getViewModel() = readViewModel

    override fun getLayoutId() = R.layout.fragment_read_new

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: FragmentReadNewBinding) { fragmentReadNewBinding = binding }

    private var navigatedToReferencesActivity = false
    private var shouldShowButtons = true
    private var versesAdapter: VersesAdapterNew? = null
    private val homeActivity by lazy {
        (requireActivity() as HomeActivity)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupVersesRecyclerView()
        setOnclickListeners()
    }

    private fun setupVersesRecyclerView() {
        //versesAdapter = VersesAdapterNew(readViewModel)
        //fragmentReadNewBinding.versesRecyclerView.adapter = versesAdapter

        fragmentReadNewBinding.versesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> toggleNextAndPreviousButtonsVisibility()
                    RecyclerView.SCROLL_STATE_DRAGGING -> {}
                    RecyclerView.SCROLL_STATE_SETTLING -> {}
                }
            }
        })
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
        observeVerseNumber()
        observeVerseSelected()
        observeSelectedVersesText()
        observeCurrentVerses()
    }

    private fun observeCurrentVerses() {
        readViewModel.currentVerses.observe(this, Observer {
            versesAdapter = VersesAdapterNew(readViewModel)
            fragmentReadNewBinding.versesRecyclerView.adapter = versesAdapter
            versesAdapter?.submitList(it)
        })
    }

    private fun observeVerseNumber() {
        readViewModel.verseNumber.observe(this, LiveDataEventObserver {
            fragmentReadNewBinding.versesRecyclerView.post {
                fragmentReadNewBinding.versesRecyclerView.smoothScrollToPosition(it)
            }
        })
    }

    private fun observeVerseSelected() {
        readViewModel.verseSelected.observe(this, LiveDataEventObserver {
            versesAdapter?.notifyDataSetChanged()

            with(homeActivity) {
                if (!versesTapActionsBottomSheetShowing && readViewModel.selectedVerses.isNotEmpty())
                    showVerseTapActionsBottomSheet(readViewModel)

                if (readViewModel.selectedVerses.isEmpty())
                    toggleVerseTapActionsBottomSheetVisibility()
            }
        })
    }

    private fun observeSelectedVersesText() {
        readViewModel.selectedVersesText.observe(this, LiveDataEventObserver {
            with(homeActivity) {
                if (it.isNotEmpty()) showSelectedVersesText(it)
            }
        })
    }

    private fun setOnclickListeners() {
        fragmentReadNewBinding.bookNameButton.setOnClickListener {
            if (homeActivity.versesTapActionsBottomSheetShowing) {
                homeActivity.toggleVerseTapActionsBottomSheetVisibility()
            }
            readViewModel.clearSelectedVerses()
            navigateToReferencesActivity()
            navigatedToReferencesActivity = true
        }

        fragmentReadNewBinding.fontStyleButton.setOnClickListener { readViewModel.setMessage("Coming Soon!", AppState.SUCCESS) }

        fragmentReadNewBinding.nextButton.setOnClickListener { readViewModel.getChapterVerses(1) }

        fragmentReadNewBinding.previousButton.setOnClickListener { readViewModel.getChapterVerses(-1) }

    }

    private fun navigateToReferencesActivity() {
        startActivity(AppActivityNavCommands.getReferencesActivityIntent(requireContext()))
    }

    private fun toggleNextAndPreviousButtonsVisibility() {
        if (shouldShowButtons) {
            shouldShowButtons = false
            showView(fragmentReadNewBinding.nextButton, true)
            showView(fragmentReadNewBinding.previousButton, true)

            Timer().schedule(5000) {
                activity?.runOnUiThread {
                    showView(fragmentReadNewBinding.nextButton, false)
                    showView(fragmentReadNewBinding.previousButton, false)
                    shouldShowButtons = true
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        readViewModel.getBookFromSavedPreferencesOrInitializeWithGenese()
    }

    override fun setNotificationObserver() {
        readViewModel.notificationLiveData.observe(this, LiveDataEventObserver {

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

    override fun showLoadingDialog() { fragmentReadNewBinding.progressBar.visibility = View.VISIBLE }

    override fun dismissLoadingDialog() { fragmentReadNewBinding.progressBar.visibility = View.GONE }

}
