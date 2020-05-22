package com.iniongungroup.mobile.android.references.fragments

import android.graphics.Typeface
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongungroup.mobile.android.references.BR
import com.iniongungroup.mobile.android.references.R
import com.iniongungroup.mobile.android.references.ReferencesActivity
import com.iniongungroup.mobile.android.references.ReferencesViewModel
import com.iniongungroup.mobile.android.references.adapters.BooksAdapter
import com.iniongungroup.mobile.android.references.databinding.FragmentBooksBinding
import kotlinx.android.synthetic.main.fragment_books.*

class BooksFragment : BaseFragment<FragmentBooksBinding, ReferencesViewModel>() {

    private val referencesViewModel by lazy { (requireActivity() as ReferencesActivity).referencesViewModel }

    private lateinit var booksAdapter: BooksAdapter

    private lateinit var fragmentBooksBinding: FragmentBooksBinding

    override fun getViewModel() = referencesViewModel

    override fun getLayoutId() = R.layout.fragment_books

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: FragmentBooksBinding) {
        fragmentBooksBinding = binding
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        booksAdapter = BooksAdapter(referencesViewModel)
        setupBooksRecyclerView()
        setOnClickListeners()
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
        observeBooks()
        observeShowChaptersFragment()
        observeSettings()
    }

    private fun observeShowChaptersFragment() {
        referencesViewModel.showChaptersFragment.observe(this, LiveDataEventObserver {
            if (it) (requireActivity() as ReferencesActivity).setViewPagerItem(1)
        })
    }

    private fun setOnClickListeners() {
        fragmentBooksBinding.doneButton.setOnClickListener {

        }

        searchEditText.addTextChangedListener {
            it?.toString()?.let { newText ->
                referencesViewModel.filterBooks(newText)
            }
        }

    }

    private fun setupBooksRecyclerView() {
        fragmentBooksBinding.booksRecyclerView.adapter = booksAdapter
    }

    private fun observeBooks() {
        referencesViewModel.books.observe(this, Observer {
            booksAdapter.submitList(it)
        })
    }

    private fun observeSettings() {
        referencesViewModel.settings.observe(this, Observer { setting ->
            val typeface = Typeface.createFromAsset(activity!!.assets, "font/${setting.fontStyle.name}")
            searchEditText.typeface = typeface
        })
    }

}
