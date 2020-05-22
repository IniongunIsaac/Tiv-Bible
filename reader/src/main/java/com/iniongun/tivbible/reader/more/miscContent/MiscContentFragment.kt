package com.iniongun.tivbible.reader.more.miscContent

import android.os.Bundle
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongun.tivbible.common.utils.navigation.AppFragmentNavCommands
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.MiscContentFragmentBinding
import com.iniongun.tivbible.reader.home.HomeActivity
import com.iniongun.tivbible.reader.more.MoreViewModel
import com.iniongun.tivbible.reader.utils.copyDataToClipboard
import com.iniongun.tivbible.reader.utils.shareData
import kotlinx.android.synthetic.main.misc_content_fragment.*

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {

        backButton.setOnClickListener { navigate(AppFragmentNavCommands.Back) }

        shareButton.setOnClickListener {  activity?.shareData(subTitleTextView.text.toString(), contentTextView.text.toString()) }

        copyButton.setOnClickListener {
            activity?.copyDataToClipboard(subTitleTextView.text.toString(), contentTextView.text.toString())
            moreViewModel.postSuccessMessage("Copied ${toolbarTitleTextView.text} successfully!")
        }

    }

    override fun setNotificationObserver() {
        moreViewModel.notificationLiveData.observe(this, LiveDataEventObserver {

            when (it.state) {

                AppState.FAILED -> {
                    dismissLoadingDialog()
                    it.message?.let { message ->
                        showMessage(this.requireView(), message, isError = true)
                    }
                }

                AppState.WARNING -> {
                    dismissLoadingDialog()
                    it.message?.let { message ->
                        showMessage(this.requireView(), message, isWarning = true)
                    }

                }

                AppState.LOADING -> {
                    showLoadingDialog()
                }

                AppState.SUCCESS -> {
                    dismissLoadingDialog()
                    it.message?.let { message ->
                        showMessage(this.requireView(), message)
                    }

                }

            }

        })
    }

}