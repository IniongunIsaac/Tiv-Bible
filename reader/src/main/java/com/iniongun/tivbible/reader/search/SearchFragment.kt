package com.iniongun.tivbible.reader.search

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.SearchFragmentBinding
import kotlinx.android.synthetic.main.search_fragment.*
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

        val items = listOf("Material", "Design", "Components", "Android")
        val adapter = ArrayAdapter(requireContext(), R.layout.book_name_item, items)
        (bookNamesTextInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

}
