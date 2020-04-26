package com.iniongungroup.mobile.android.references.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongungroup.mobile.android.references.BR
import com.iniongungroup.mobile.android.references.R
import com.iniongungroup.mobile.android.references.ReferencesActivity
import com.iniongungroup.mobile.android.references.ReferencesViewModel
import com.iniongungroup.mobile.android.references.databinding.FragmentVersesBinding

/**
 * A simple [Fragment] subclass.
 */
class VersesFragment : BaseFragment<FragmentVersesBinding, ReferencesViewModel>() {

    private val referencesViewModel by lazy { (requireActivity() as ReferencesActivity).referencesViewModel }

    private lateinit var fragmentBooksBinding: FragmentVersesBinding

    override fun getViewModel() = referencesViewModel

    override fun getLayoutId() = R.layout.fragment_verses

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: FragmentVersesBinding) {
        fragmentBooksBinding = binding
    }

}
