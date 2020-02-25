package com.iniongun.tivbible.reader.search

import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.SearchFragmentBinding
import javax.inject.Inject

class SearchFragment : BaseFragment<SearchFragmentBinding, SearchViewModel>() {

    @Inject
    lateinit var searchViewModel: SearchViewModel

    private lateinit var searchFragmentBinding: SearchFragmentBinding

    override fun getViewModel() = searchViewModel

    override fun getLayoutId() = R.layout.search_fragment

    override fun getBindingVariable() = BR.ViewModel

    override fun getLayoutBinding(binding: SearchFragmentBinding) {
        searchFragmentBinding = binding
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
    }

}
