package com.iniongungroup.mobile.android.references.fragments

import android.os.Bundle
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongungroup.mobile.android.references.BR
import com.iniongungroup.mobile.android.references.R
import com.iniongungroup.mobile.android.references.ReferencesActivity
import com.iniongungroup.mobile.android.references.ReferencesViewModel
import com.iniongungroup.mobile.android.references.adapters.BooksAdapter
import com.iniongungroup.mobile.android.references.databinding.FragmentBooksBinding
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
    }

    private fun setupBooksRecyclerView() {
        val viewModel = fragmentBooksBinding.viewModel
        if (viewModel != null)
            fragmentBooksBinding.booksRecyclerView.adapter = BooksAdapter(viewModel)
        else
            Timber.w("ViewModel not initialized when attempting to setup ViewPager adapter.")
    }

}
