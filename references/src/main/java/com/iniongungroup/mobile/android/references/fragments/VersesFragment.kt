package com.iniongungroup.mobile.android.references.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongungroup.mobile.android.references.BR
import com.iniongungroup.mobile.android.references.R
import com.iniongungroup.mobile.android.references.ReferencesActivity
import com.iniongungroup.mobile.android.references.ReferencesViewModel
import com.iniongungroup.mobile.android.references.adapters.VersesAdapter
import com.iniongungroup.mobile.android.references.databinding.FragmentVersesBinding

/**
 * A simple [Fragment] subclass.
 */
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
        versesAdapter = VersesAdapter(referencesViewModel)
        setupVersesRecyclerView()
    }

    private fun setupVersesRecyclerView() {
        fragmentVersesBinding.versesRecyclerView.adapter = versesAdapter
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
        observeVerses()
    }

    private fun observeVerses() {
        referencesViewModel.verses.observe(this, LiveDataEventObserver {
            versesAdapter.submitList(it)
        })
    }

}
