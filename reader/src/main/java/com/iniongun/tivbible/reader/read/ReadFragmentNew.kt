package com.iniongun.tivbible.reader.read

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.getDeviceLineSpacingFour
import com.iniongun.tivbible.common.utils.getDeviceLineSpacingThree
import com.iniongun.tivbible.common.utils.getDeviceLineSpacingTwo
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongun.tivbible.common.utils.navigation.AppActivityNavCommands
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongun.tivbible.entities.Setting
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.FragmentReadNewBinding
import com.iniongun.tivbible.reader.home.HomeActivity
import com.iniongun.tivbible.reader.read.adapters.VersesAdapterNew
import com.iniongun.tivbible.reader.utils.LineSpacingType.*
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
    private val homeActivity by lazy { (requireActivity() as HomeActivity) }
    private val deviceScreenSize by lazy { homeActivity.deviceScreenSize }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupVersesRecyclerView()
        setOnclickListeners()
    }

    private fun setupVersesRecyclerView() {
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
        observeShouldEnableFontSettingsUIControls()
        observeSettings()
        observeFontStylesAndThemes()
        observeHighlightColors()
        observeHighlights()
        observeShouldDismissNotesDialog()
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
                fragmentReadNewBinding.versesRecyclerView.scrollToPosition(it)
            }
        })
    }

    private fun observeVerseSelected() {
        readViewModel.verseSelected.observe(this, LiveDataEventObserver {
            versesAdapter?.notifyDataSetChanged()

            with(homeActivity) {
                if (fontSettingsBottomSheetShowing)
                    toggleFontSettingsBottomSheetVisibility()

                if (!versesTapActionsBottomSheetShowing && readViewModel.selectedVerses.isNotEmpty()) {
                    readViewModel.getHighlightColors()
                    if (readViewModel.highlightColorsList.isNotEmpty())
                        toggleVerseTapActionsBottomSheetVisibility()
                }

                if (readViewModel.selectedVerses.isEmpty())
                    toggleVerseTapActionsBottomSheetVisibility()
            }
        })
    }

    private fun observeHighlightColors() {
        readViewModel.highlightColors.observe(this, Observer {
            homeActivity.showVerseTapActionsBottomSheet(readViewModel)
        })
    }

    private fun observeHighlights() {
        readViewModel.highlights.observe(this, Observer {
            versesAdapter?.notifyDataSetChanged()
        })
    }

    private fun observeSelectedVersesText() {
        readViewModel.selectedVersesText.observe(this, LiveDataEventObserver {
            with(homeActivity) {
                if (it.isNotEmpty()) updateSelectedVersesTextViewContent(it)
            }
        })
    }

    private fun observeShouldEnableFontSettingsUIControls() {
        readViewModel.shouldEnableFontSettingsUIControls.observe(this, Observer {
            homeActivity.updateFontSettingsUIStates(it)
        })
    }

    private fun observeSettings() {
        readViewModel.settings.observe(this, Observer {
            versesAdapter?.notifyDataSetChanged()

            updateToolbarButtonsFont(it)

            homeActivity.updateFontSizeTextViewContent(it.fontSize)

            if (it.stayAwake) {
                activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            } else {
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }

            val typeface = Typeface.createFromAsset(activity!!.assets, "font/${it.fontStyle.name}")
            homeActivity.updateBottomSheetsUIElementsFontStyles(typeface)

            when (it.lineSpacing) {
                getDeviceLineSpacingTwo(deviceScreenSize) -> { homeActivity.setLineSpacingButtonsBackground(TWO) }
                getDeviceLineSpacingThree(deviceScreenSize) -> { homeActivity.setLineSpacingButtonsBackground(THREE) }
                getDeviceLineSpacingFour(deviceScreenSize) -> { homeActivity.setLineSpacingButtonsBackground(FOUR) }
            }
        })
    }

    private fun updateToolbarButtonsFont(settings: Setting) {
        with(settings) {
            val typeface = Typeface.createFromAsset(activity!!.assets, "font/${this.fontStyle.name}")
            fragmentReadNewBinding.bookNameButton.typeface = typeface
            fragmentReadNewBinding.fontStyleButton.typeface = typeface
        }
    }

    private fun observeFontStylesAndThemes() {
        readViewModel.fontStylesAndThemes.observe(this, Observer {
            homeActivity.setupFontStylesAndThemesChipGroups(it, readViewModel.currentSettings)
        })
    }

    private fun observeShouldDismissNotesDialog() {
        readViewModel.shouldDismissNotesDialog.observe(this, LiveDataEventObserver {
            if (it) homeActivity.dismissNotesDialog()
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

        fragmentReadNewBinding.fontStyleButton.setOnClickListener {
            readViewModel.getAppFontStylesAndThemes()
            with(homeActivity) {
                if (versesTapActionsBottomSheetShowing)
                    toggleVerseTapActionsBottomSheetVisibility()

                setupFontSettingsBottomSheet(readViewModel)
            }
        }

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
