package com.iniongun.tivbible.reader.more.settings

import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.SettingsFragmentBinding
import javax.inject.Inject

class SettingsFragment : BaseFragment<SettingsFragmentBinding, SettingsViewModel>() {

    @Inject
    lateinit var settingsViewModel: SettingsViewModel

    private lateinit var settingsFragmentBinding: SettingsFragmentBinding

    override fun getViewModel() = settingsViewModel

    override fun getLayoutId() = R.layout.settings_fragment

    override fun getBindingVariable() = BR.ViewModel

    override fun getLayoutBinding(binding: SettingsFragmentBinding) {
        settingsFragmentBinding = binding
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
    }

}
