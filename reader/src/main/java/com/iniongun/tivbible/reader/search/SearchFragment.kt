package com.iniongun.tivbible.reader.search

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.capitalizeWords
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.SearchFragmentBinding
import com.iniongun.tivbible.reader.search.adapters.ChaptersAdapter
import kotlinx.android.synthetic.main.search_fragment.*
import timber.log.Timber
import javax.inject.Inject

class SearchFragment : BaseFragment<SearchFragmentBinding, SearchViewModel>() {

    @Inject
    lateinit var searchViewModel: SearchViewModel

    private lateinit var searchFragmentBinding: SearchFragmentBinding

    override fun getViewModel() = searchViewModel

    override fun getLayoutId() = R.layout.search_fragment

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: SearchFragmentBinding) {
        searchFragmentBinding = binding
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //navigate(AppFragmentNavCommands.To(SearchFragmentDirections.actionNavigationSearchToSearchResultsFragment()))
        setOnClickListeners()
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
        observeBooks()
        observeChapters()
        observeRecentSearches()
        observeHistory()
    }

    private fun observeBooks() {
        searchViewModel.books.observe(this, Observer { books ->
            val booksAdapter = ArrayAdapter(requireContext(), R.layout.book_item, books.map { it.bookName.capitalizeWords() })
            bookNameAutoCompleteTextView.setAdapter(booksAdapter)
        })
    }

    private fun observeChapters() {
        searchViewModel.chapters.observe(this, Observer {
            val chaptersAdapter = ChaptersAdapter(searchViewModel)
            chapterNumbersRecyclerView.adapter = chaptersAdapter
            chaptersAdapter.submitList(it)
        })
    }

    private fun observeRecentSearches() {
        searchViewModel.recentSearches.observe(this, Observer {

        })
    }

    private fun observeHistory() {
        searchViewModel.history.observe(this, Observer {

        })
    }

    private fun setOnClickListeners() {
        clearRecentSearchesButton.setOnClickListener {

        }

        clearRecentSearchesButton.setOnClickListener {

        }

        bookNameAutoCompleteTextView.setOnItemClickListener { _, _, position, id ->
            searchViewModel.getChapters(position)
            Timber.e("Id: $id")
        }

    }

    override fun onResume() {
        super.onResume()
        searchViewModel.getRecentSearches()
        searchViewModel.getHistory()
    }

}
