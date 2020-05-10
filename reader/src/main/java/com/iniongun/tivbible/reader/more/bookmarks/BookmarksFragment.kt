package com.iniongun.tivbible.reader.more.bookmarks

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
import com.iniongun.tivbible.reader.databinding.BookmarksFragmentBinding
import com.iniongun.tivbible.reader.home.HomeActivity
import com.iniongun.tivbible.reader.more.adapters.BookmarksAdapter
import com.iniongun.tivbible.reader.utils.ModuleType
import kotlinx.android.synthetic.main.bookmarks_fragment.*
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 09/05/2020.
 * For Tiv Bible project.
 */

class BookmarksFragment : BaseFragment<BookmarksFragmentBinding, BookmarksViewModel>() {

    @Inject
    lateinit var bookmarksViewModel: BookmarksViewModel

    private lateinit var bookmarksFragmentBinding: BookmarksFragmentBinding

    override fun getViewModel() = bookmarksViewModel

    override fun getLayoutId() = R.layout.bookmarks_fragment

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: BookmarksFragmentBinding) {
        bookmarksFragmentBinding = binding
    }

    private val bookmarksAdapter by lazy { BookmarksAdapter(bookmarksViewModel) }

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
        observeBookmarks()
        observeShowReaderModule()
    }

    private fun observeSettings() {
        bookmarksViewModel.settings.observe(this, Observer {
            toolbarTitleTextView.typeface = Typeface.createFromAsset(activity!!.assets, "font/${it.fontStyle.name}")
            bookmarksAdapter.notifyDataSetChanged()
        })
    }

    private fun observeBookmarks() {
        bookmarksViewModel.bookmarks.observe(this, Observer {
            bookmarksRecyclerView.adapter = bookmarksAdapter
            bookmarksAdapter.submitList(it)
            switchBookmarksEmptyState(it.isEmpty())
        })
    }

    private fun observeShowReaderModule() {
        bookmarksViewModel.showReaderModule.observe(this, LiveDataEventObserver {
            if (it) homeActivity.showModule(ModuleType.READER)
        })
    }

    private fun switchBookmarksEmptyState(state: Boolean) {
        showView(bookmarksRecyclerView, !state)
        showView(bookmarksNotFoundTextView, state)
    }

    override fun setNotificationObserver() {
        bookmarksViewModel.notificationLiveData.observe(this, LiveDataEventObserver {

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