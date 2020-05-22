package com.iniongun.tivbible.reader.more.help

import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.HelpFragmentBinding
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 09/05/2020.
 * For Tiv Bible project.
 */

class HelpFragment : BaseFragment<HelpFragmentBinding, HelpViewModel>() {

    @Inject
    lateinit var bookmarksViewModel: HelpViewModel

    private lateinit var helpFragmentBinding: HelpFragmentBinding

    override fun getViewModel() = bookmarksViewModel

    override fun getLayoutId() = R.layout.help_fragment

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: HelpFragmentBinding) {
        helpFragmentBinding = binding
    }
}