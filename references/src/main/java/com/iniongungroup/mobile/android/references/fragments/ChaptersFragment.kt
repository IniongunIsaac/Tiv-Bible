package com.iniongungroup.mobile.android.references.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongungroup.mobile.android.references.BR
import com.iniongungroup.mobile.android.references.R
import com.iniongungroup.mobile.android.references.ReferencesActivity
import com.iniongungroup.mobile.android.references.ReferencesViewModel
import com.iniongungroup.mobile.android.references.adapters.ChaptersAdapter
import com.iniongungroup.mobile.android.references.databinding.FragmentChaptersBinding

/**
 * A simple [Fragment] subclass.
 */
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
        chaptersAdapter = ChaptersAdapter(referencesViewModel)
        setupChaptersRecyclerView()
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
        observeChapters()
    }

    private fun observeChapters() {
        referencesViewModel.chapters.observe(this, LiveDataEventObserver {
            chaptersAdapter.submitList(it)
        })
    }

    private fun setupChaptersRecyclerView() {

        fragmentChaptersBinding.chaptersRecyclerView.adapter = chaptersAdapter

//        val viewModel = fragmentChaptersBinding.viewModel
//        if (viewModel != null)
//            fragmentChaptersBinding.chaptersRecyclerView.adapter = ChaptersAdapter(viewModel)
//        else
//            Timber.e("ViewModel not initialized when attempting to setup ViewPager adapter.")
    }

}
