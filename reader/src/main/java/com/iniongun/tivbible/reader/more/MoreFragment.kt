package com.iniongun.tivbible.reader.more

import android.os.Bundle
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.MoreFragmentBinding
import com.iniongun.tivbible.reader.more.adapters.MoreItemsAdapter
import com.iniongun.tivbible.reader.utils.moreItems
import kotlinx.android.synthetic.main.more_fragment.*
import javax.inject.Inject

class MoreFragment : BaseFragment<MoreFragmentBinding, MoreViewModel>() {

    @Inject
    lateinit var moreViewModel: MoreViewModel

    private lateinit var moreFragmentBinding: MoreFragmentBinding

    override fun getViewModel() = moreViewModel

    override fun getLayoutId() = R.layout.more_fragment

    override fun getBindingVariable() = BR.ViewModel

    override fun getLayoutBinding(binding: MoreFragmentBinding) {
        moreFragmentBinding = binding
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMoreItemsRecyclerView()
    }

    private fun setupMoreItemsRecyclerView() {
        val moreItemsAdapter = MoreItemsAdapter(moreViewModel)
        moreItemsRecyclerView.adapter = moreItemsAdapter
        moreItemsAdapter.submitList(moreItems)
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
    }

}
