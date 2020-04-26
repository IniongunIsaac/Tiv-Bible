package com.iniongun.tivbible.reader.read

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongun.tivbible.common.utils.navigation.AppActivityNavCommands
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.ReadFragmentBinding
import com.iniongun.tivbible.reader.read.adapters.ChaptersAdapter
import kotlinx.android.synthetic.main.read_fragment.*
import timber.log.Timber
import javax.inject.Inject

class ReadFragment : BaseFragment<ReadFragmentBinding, ReadViewModel>() {

    @Inject
    lateinit var readViewModel: ReadViewModel

    private lateinit var readFragmentBinding: ReadFragmentBinding

    override fun getViewModel() = readViewModel

    override fun getLayoutId() = R.layout.read_fragment

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: ReadFragmentBinding) {
        readFragmentBinding = binding
    }

//    override fun setViewModelObservers() {
//        super.setViewModelObservers()
//    }
//
//    private fun observeChaptersList() {
//
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupChaptersViewPager()
        setOnclickListeners()
    }

    private fun setOnclickListeners() {
        readFragmentBinding.bookNameButton.setOnClickListener {
            navigateToReferencesActivity()
        }

        readFragmentBinding.fontStyleButton.setOnClickListener {  }
    }

    private fun navigateToReferencesActivity() {
        startActivity(AppActivityNavCommands.getReferencesActivityIntent(requireContext()))
    }

    private fun setupChaptersViewPager() {
        val viewModel = readFragmentBinding.viewModel
        if (viewModel != null)
            readFragmentBinding.chaptersViewPager.adapter = ChaptersAdapter(viewModel)
        else
            Timber.w("ViewModel not initialized when attempting to setup ViewPager adapter.")

    }

    override fun setNotificationObserver() {
        readViewModel.notificationLiveData.observe(this, LiveDataEventObserver {

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

    override fun showLoadingDialog() {
        progressBar.visibility = VISIBLE
    }

    override fun dismissLoadingDialog() {
        progressBar.visibility = GONE
    }

}
