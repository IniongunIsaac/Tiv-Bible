package com.iniongun.tivbible.reader.search

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.capitalizeWords
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongun.tivbible.common.utils.navigation.AppFragmentNavCommands
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.SearchFragmentBinding
import com.iniongun.tivbible.reader.home.HomeActivity
import com.iniongun.tivbible.reader.search.adapters.ChaptersAdapter
import com.iniongun.tivbible.reader.search.adapters.HistoryAdapter
import com.iniongun.tivbible.reader.search.adapters.RecentSearchAdapter
import com.iniongun.tivbible.reader.utils.ModuleType
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : BaseFragment<SearchFragmentBinding, SearchViewModel>() {

    private val searchViewModel by lazy { (requireActivity() as HomeActivity).searchViewModel }

    private lateinit var searchFragmentBinding: SearchFragmentBinding

    override fun getViewModel() = searchViewModel

    override fun getLayoutId() = R.layout.search_fragment

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: SearchFragmentBinding) {
        searchFragmentBinding = binding
    }

    private lateinit var chaptersAdapter: ChaptersAdapter

    private val homeActivity by lazy { (requireActivity() as HomeActivity) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setOnClickListeners()
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
        observeBooks()
        observeChapters()
        observeRecentSearches()
        observeHistory()
        observeChapterSelected()
        observeRevisitHistory()
        observeShowSearchResults()
    }

    private fun observeBooks() {
        searchViewModel.books.observe(this, Observer { books ->
            val booksAdapter = ArrayAdapter(requireContext(), R.layout.book_item, books.map { it.bookName.capitalizeWords() })
            bookNameAutoCompleteTextView.setAdapter(booksAdapter)
        })
    }

    private fun observeChapters() {
        searchViewModel.chapters.observe(this, Observer {
            chaptersAdapter = ChaptersAdapter(searchViewModel)
            chapterNumbersRecyclerView.adapter = chaptersAdapter
            chaptersAdapter.submitList(it)
        })
    }

    private fun observeRecentSearches() {
        searchViewModel.recentSearches.observe(this, Observer {
            val recentSearchAdapter = RecentSearchAdapter(searchViewModel)
            recentSearchesRecyclerView.adapter = recentSearchAdapter
            recentSearchAdapter.submitList(it)
            switchRecentSearchEmptyState(it.isEmpty())
        })
    }

    private fun observeHistory() {
        searchViewModel.history.observe(this, Observer {
            val historyAdapter = HistoryAdapter(searchViewModel)
            historyRecyclerView.adapter = historyAdapter
            historyAdapter.submitList(it)
            switchHistoryEmptyState(it.isEmpty())
        })
    }

    private fun observeChapterSelected() {
        searchViewModel.chapterSelected.observe(this, LiveDataEventObserver {
            chaptersAdapter.notifyDataSetChanged()
        })
    }

    private fun observeRevisitHistory() {
        searchViewModel.revisitHistory.observe(this, LiveDataEventObserver {
            homeActivity.showModule(ModuleType.READER)
        })
    }

    private fun observeShowSearchResults() {
        searchViewModel.showSearchResults.observe(this, LiveDataEventObserver {
            if (it) navigate(AppFragmentNavCommands.To(SearchFragmentDirections.actionNavigationSearchToSearchResultsFragment()))
        })
    }

    private fun setOnClickListeners() {
        clearRecentSearchesButton.setOnClickListener { searchViewModel.clearRecentSearches() }

        clearHistoryButton.setOnClickListener { searchViewModel.clearHistory() }

        bookNameAutoCompleteTextView.setOnItemClickListener { _, _, _, _ ->
            hideKeyBoard(view!!.windowToken)
            searchViewModel.getChapters(bookNameAutoCompleteTextView.text.toString())
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewModel.search(query)
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })

    }

    private fun switchRecentSearchEmptyState(state: Boolean) {
        showView(recentSearchesRecyclerView, !state)
        showView(recentSearchesNotFoundTextView, state)
    }

    private fun switchHistoryEmptyState(state: Boolean) {
        showView(historyRecyclerView, !state)
        showView(historyNotFoundTextView, state)
    }

    override fun onResume() {
        super.onResume()
        searchViewModel.getRecentSearches()
        searchViewModel.getHistory()
    }

    override fun setNotificationObserver() {
        searchViewModel.notificationLiveData.observe(this, LiveDataEventObserver {

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
