package com.iniongun.tivbible.reader.more.highlights

import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.HighlightsFragmentBinding
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 09/05/2020.
 * For Tiv Bible project.
 */

class HighlightsFragment : BaseFragment<HighlightsFragmentBinding, HighlightsViewModel>() {

    @Inject
    lateinit var highlightsViewModel: HighlightsViewModel

    private lateinit var highlightsFragmentBinding: HighlightsFragmentBinding

    override fun getViewModel() = highlightsViewModel

    override fun getLayoutId() = R.layout.highlights_fragment

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: HighlightsFragmentBinding) {
        highlightsFragmentBinding = binding
    }
}