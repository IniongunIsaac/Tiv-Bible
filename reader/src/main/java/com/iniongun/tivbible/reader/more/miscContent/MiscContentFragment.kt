package com.iniongun.tivbible.reader.more.miscContent

import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.MiscContentFragmentBinding
import com.iniongun.tivbible.reader.home.HomeActivity
import com.iniongun.tivbible.reader.more.MoreViewModel

/**
 * Created by Isaac Iniongun on 09/05/2020.
 * For Tiv Bible project.
 */

class MiscContentFragment : BaseFragment<MiscContentFragmentBinding, MoreViewModel>() {

    private val moreViewModel by lazy { (requireActivity() as HomeActivity).moreViewModel }

    private lateinit var miscContentFragmentBinding: MiscContentFragmentBinding

    override fun getViewModel() = moreViewModel

    override fun getLayoutId() = R.layout.misc_content_fragment

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: MiscContentFragmentBinding) {
        miscContentFragmentBinding = binding
    }

}