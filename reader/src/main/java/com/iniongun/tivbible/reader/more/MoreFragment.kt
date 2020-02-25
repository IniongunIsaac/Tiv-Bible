package com.iniongun.tivbible.reader.more

import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.MoreFragmentBinding
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

    override fun setViewModelObservers() {
        super.setViewModelObservers()
    }
}
