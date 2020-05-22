package com.iniongun.tivbible.reader.search

import android.os.Bundle
import androidx.lifecycle.Observer
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongun.tivbible.common.utils.navigation.AppFragmentNavCommands
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.FragmentSearchResultsBinding
import com.iniongun.tivbible.reader.home.HomeActivity
import com.iniongun.tivbible.reader.search.adapters.BookAndChapterAndVerseAdapter
import com.iniongun.tivbible.reader.utils.ModuleType
import kotlinx.android.synthetic.main.fragment_search_results.*

class SearchResultsFragment : BaseFragment<FragmentSearchResultsBinding, SearchViewModel>() {

    private val searchViewModel by lazy { (requireActivity() as HomeActivity).searchViewModel }

    private lateinit var searchResultsFragmentBinding: FragmentSearchResultsBinding

    override fun getViewModel() = searchViewModel

    override fun getLayoutId() = R.layout.fragment_search_results

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: FragmentSearchResultsBinding) {
        searchResultsFragmentBinding = binding
    }

    private val homeActivity by lazy { (requireActivity() as HomeActivity) }

    private val bookAndChapterAndVerseAdapter by lazy { BookAndChapterAndVerseAdapter(searchViewModel) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSearchResultsRecyclerView()
        setOnClickListeners()
    }

    private fun setupSearchResultsRecyclerView() {
        searchResultsRecyclerView.adapter = bookAndChapterAndVerseAdapter
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
        observeSearchResultSelected()
        observeBooksAndChaptersAndVerses()
    }

    private fun observeBooksAndChaptersAndVerses() {
        searchViewModel.booksAndChaptersAndVerses.observe(this, Observer {
            bookAndChapterAndVerseAdapter.submitList(it)
        })
    }

    private fun observeSearchResultSelected() {
        searchViewModel.searchResultSelected.observe(this, LiveDataEventObserver {
            if (it) homeActivity.showModule(ModuleType.READER)
        })
    }

    private fun setOnClickListeners() {
        backButton.setOnClickListener { navigate(AppFragmentNavCommands.Back) }
    }

}
