package com.iniongun.tivbible.reader.read

import android.os.Bundle
import android.view.View
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.ReadFragmentBinding
import javax.inject.Inject

class ReadFragment : BaseFragment<ReadFragmentBinding, ReadViewModel>() {

    @Inject
    lateinit var readViewModel: ReadViewModel

    private lateinit var readFragmentBinding: ReadFragmentBinding

    override fun getViewModel() = readViewModel

    override fun getLayoutId() = R.layout.read_fragment

    override fun getBindingVariable() = BR.ViewModel

    override fun getLayoutBinding(binding: ReadFragmentBinding) {
        readFragmentBinding = binding
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        (activity as AppCompatActivity).supportActionBar?.hide()
//        (activity as AppCompatActivity).setSupportActionBar(readToolbar)
    }


}
