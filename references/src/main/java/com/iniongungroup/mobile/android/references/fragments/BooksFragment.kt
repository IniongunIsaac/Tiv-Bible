package com.iniongungroup.mobile.android.references.fragments

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongungroup.mobile.android.references.BR
import com.iniongungroup.mobile.android.references.R
import com.iniongungroup.mobile.android.references.ReferencesActivity
import com.iniongungroup.mobile.android.references.ReferencesViewModel
import com.iniongungroup.mobile.android.references.adapters.BooksAdapter
import com.iniongungroup.mobile.android.references.databinding.FragmentBooksBinding
import kotlinx.android.synthetic.main.fragment_books.*
import timber.log.Timber

class BooksFragment : BaseFragment<FragmentBooksBinding, ReferencesViewModel>() {

    private val referencesViewModel by lazy { (requireActivity() as ReferencesActivity).referencesViewModel }

    private lateinit var fragmentBooksBinding: FragmentBooksBinding

    override fun getViewModel() = referencesViewModel

    override fun getLayoutId() = R.layout.fragment_books

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: FragmentBooksBinding) {
        fragmentBooksBinding = binding
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupBooksRecyclerView()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        fragmentBooksBinding.doneButton.setOnClickListener {

        }

        searchEditText.addTextChangedListener {
            it?.toString()?.let { newText ->
                referencesViewModel.filterBooks(newText)
            }
        }

//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?) = false
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                newText?.let { referencesViewModel.filterBooks(it) }
//                return false
//            }
//
//        })

    }

    private fun setupBooksRecyclerView() {
        val viewModel = fragmentBooksBinding.viewModel
        if (viewModel != null)
            fragmentBooksBinding.booksRecyclerView.adapter = BooksAdapter(viewModel)
        else
            Timber.w("ViewModel not initialized when attempting to setup ViewPager adapter.")
    }

}
