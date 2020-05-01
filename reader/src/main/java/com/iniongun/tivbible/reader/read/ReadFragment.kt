package com.iniongun.tivbible.reader.read

import android.os.Bundle
import android.os.Handler
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongun.tivbible.common.utils.navigation.AppActivityNavCommands
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.ReadFragmentBinding
import com.iniongun.tivbible.reader.home.HomeActivity
import com.iniongun.tivbible.reader.read.adapters.ChaptersAdapter
import kotlinx.android.synthetic.main.read_fragment.*
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class ReadFragment : BaseFragment<ReadFragmentBinding, ReadViewModel>() {

    @Inject
    lateinit var readViewModel: ReadViewModel

    private lateinit var chaptersAdapter: ChaptersAdapter

    private lateinit var readFragmentBinding: ReadFragmentBinding

    override fun getViewModel() = readViewModel

    override fun getLayoutId() = R.layout.read_fragment

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: ReadFragmentBinding) {
        readFragmentBinding = binding
    }

    private val chaptersViewPagerPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            readViewModel.setChapterNumber(position + 1)
        }
    }

    private var shouldShowButtons = true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        chaptersAdapter = ChaptersAdapter(readViewModel)
        setupChaptersViewPager()
        setOnclickListeners()
        setChaptersViewPagerPageChangeCallback()
        showView(nextButton, false)
        showView(previousButton, false)
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
        observeChapters()
        observeVersesRecyclerViewTouched()
        observeVerseSelected()
        observeSelectedVersesText()
    }

    private fun observeVersesRecyclerViewTouched() {
        readViewModel.versesRecyclerViewTouched.observe(this, LiveDataEventObserver {
            if (it && shouldShowButtons) {
                shouldShowButtons = false
                showView(nextButton, true)
                showView(previousButton, true)

                Timer().schedule(5000) {
                    activity?.runOnUiThread {
                        showView(nextButton, false)
                        showView(previousButton, false)
                        shouldShowButtons = true
                    }
                }
            }
        })
    }

    private fun observeVerseSelected() {
        readViewModel.verseSelected.observe(this, LiveDataEventObserver {
            chaptersAdapter.versesAdapter?.notifyDataSetChanged()

            with((requireActivity() as HomeActivity)) {
                if (!versesTapActionsBottomSheetShowing)
                    showVerseTapActionsBottomSheet(readViewModel)
            }
        })
    }

    private fun observeSelectedVersesText() {
        readViewModel.selectedVersesText.observe(this, LiveDataEventObserver {
            with((requireActivity() as HomeActivity)) {
                showSelectedVersesText(it)
            }
        })
    }

    private fun observeChapters() {
        readViewModel.chapters.observe(this, Observer {
            chaptersAdapter.submitList(it)
            Handler().postDelayed({
                setCurrentChaptersViewPagerItem(readViewModel.chapterNum - 1)
            }, 100)
        })
    }

    private fun setCurrentChaptersViewPagerItem(index: Int) {
        readFragmentBinding.chaptersViewPager.setCurrentItem(index, true)
    }

    private fun setOnclickListeners() {
        readFragmentBinding.bookNameButton.setOnClickListener {
            navigateToReferencesActivity()
        }

        readFragmentBinding.fontStyleButton.setOnClickListener {  }

        readFragmentBinding.nextButton.setOnClickListener {
            if (getChaptersViewPagerItem(1) <= chaptersAdapter.itemCount)
                setCurrentChaptersViewPagerItem(getChaptersViewPagerItem(1))
        }

        readFragmentBinding.previousButton.setOnClickListener {
            if (getChaptersViewPagerItem(-1) >= 0)
                setCurrentChaptersViewPagerItem(getChaptersViewPagerItem(-1))
        }

        fontStyleButton.setOnClickListener {

        }

    }

    private fun getChaptersViewPagerItem(index: Int) = readFragmentBinding.chaptersViewPager.currentItem + index
    
    private fun setChaptersViewPagerPageChangeCallback() {
        readFragmentBinding.chaptersViewPager.registerOnPageChangeCallback(chaptersViewPagerPageChangeCallback)
    }

    private fun navigateToReferencesActivity() {
        startActivity(AppActivityNavCommands.getReferencesActivityIntent(requireContext()))
    }

    private fun setupChaptersViewPager() {
        readFragmentBinding.chaptersViewPager.adapter = chaptersAdapter

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

    override fun showLoadingDialog() { progressBar.visibility = VISIBLE }

    override fun dismissLoadingDialog() { progressBar.visibility = GONE }

    override fun onResume() {
        super.onResume()
        readViewModel.getBookFromSavedPreferencesOrInitializeWithGenese()
        readViewModel.getSavedChapterNumber()
        readViewModel.getSavedVerseNumber()
    }

    override fun onDestroy() {
        super.onDestroy()
        readFragmentBinding.chaptersViewPager.unregisterOnPageChangeCallback(chaptersViewPagerPageChangeCallback)
    }

}
