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
import com.iniongungroup.mobile.android.references.adapters.VersesAdapter
import com.iniongungroup.mobile.android.references.databinding.FragmentVersesBinding

class VersesFragment : BaseFragment<FragmentVersesBinding, ReferencesViewModel>() {

    private val referencesViewModel by lazy { (requireActivity() as ReferencesActivity).referencesViewModel }

    private lateinit var versesAdapter: VersesAdapter

    private lateinit var fragmentVersesBinding: FragmentVersesBinding

    override fun getViewModel() = referencesViewModel

    override fun getLayoutId() = R.layout.fragment_verses

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: FragmentVersesBinding) {
        fragmentVersesBinding = binding
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSpanCount()
        versesAdapter = VersesAdapter(referencesViewModel)
        setupVersesRecyclerView()
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

    private fun setupVersesRecyclerView() {
        fragmentVersesBinding.versesRecyclerView.adapter = versesAdapter
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
        observeVerses()
        observeShowReaderFragment()
    }

    private fun observeShowReaderFragment() {
        referencesViewModel.showReaderFragment.observe(this, LiveDataEventObserver {
            if (it) requireActivity().finish()
        })
    }

    private fun observeVerses() {
        referencesViewModel.verses.observe(this, LiveDataEventObserver {
            versesAdapter.submitList(it)
        })
    }

}
