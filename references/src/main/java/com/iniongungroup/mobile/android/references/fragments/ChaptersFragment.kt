package com.iniongungroup.mobile.android.references.fragments

import android.os.Bundle
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.ScreenSize.*
import com.iniongun.tivbible.common.utils.getDeviceScreenSize
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongungroup.mobile.android.references.BR
import com.iniongungroup.mobile.android.references.R
import com.iniongungroup.mobile.android.references.ReferencesActivity
import com.iniongungroup.mobile.android.references.ReferencesViewModel
import com.iniongungroup.mobile.android.references.adapters.ChaptersAdapter
import com.iniongungroup.mobile.android.references.databinding.FragmentChaptersBinding

class ChaptersFragment : BaseFragment<FragmentChaptersBinding, ReferencesViewModel>() {

    private val referencesViewModel by lazy { (requireActivity() as ReferencesActivity).referencesViewModel }

    private lateinit var fragmentChaptersBinding: FragmentChaptersBinding

    private lateinit var chaptersAdapter: ChaptersAdapter

    override fun getViewModel() = referencesViewModel

    override fun getLayoutId() = R.layout.fragment_chapters

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: FragmentChaptersBinding) {
        fragmentChaptersBinding = binding
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSpanCount()
        chaptersAdapter = ChaptersAdapter(referencesViewModel)
        setupChaptersRecyclerView()
    }

    private fun setSpanCount() {
        val spanCount = when (getDeviceScreenSize(resources)) {
            SMALL -> 3
            NORMAL -> 5
            LARGE -> 7
            XLARGE -> 9
            UNDEFINED -> 4
        }
        referencesViewModel.setSpanCount(spanCount)
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
        observeChapters()
        observeShowVersesFragment()
    }

    private fun observeShowVersesFragment() {
        referencesViewModel.showVersesFragment.observe(this, LiveDataEventObserver {
            if (it) (requireActivity() as ReferencesActivity).setViewPagerItem(2)
        })
    }

    private fun observeChapters() {
        referencesViewModel.chapters.observe(this, LiveDataEventObserver {
            chaptersAdapter.submitList(it)
        })
    }

    private fun setupChaptersRecyclerView() {
        fragmentChaptersBinding.chaptersRecyclerView.adapter = chaptersAdapter
    }

}
