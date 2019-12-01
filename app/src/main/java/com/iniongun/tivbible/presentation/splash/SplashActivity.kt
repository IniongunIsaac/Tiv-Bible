package com.iniongun.tivbible.presentation.splash

import android.view.View.GONE
import android.view.View.VISIBLE
import com.iniongun.tivbible.R
import com.iniongun.tivbible.common.base.BaseActivity
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongun.tivbible.databinding.ActivitySplashBinding
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashActivityViewModel>() {

    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var splashActivityViewModel: SplashActivityViewModel

    override fun getLayoutId() = R.layout.activity_splash

    override fun getViewModel() = splashActivityViewModel

    override fun getBindingVariable() = 0

    override fun getBinding(binding: ActivitySplashBinding) {
        this.binding = binding
    }

    override fun setNotificationObserver() {
        splashActivityViewModel.notificationLiveData.observe(this, LiveDataEventObserver {

            when (it.state) {

                AppState.FAILED -> {
                    dismissLoadingDialog()
                    it.message?.let { message ->
                        showMessage(currentFocus!!, message, isError = true)
                    }
                }

                AppState.WARNING -> {
                    dismissLoadingDialog()
                    it.message?.let { message ->
                        showMessage(currentFocus!!, message, isWarning = true)
                    }

                }

                AppState.LOADING -> {
                    showLoadingDialog()
                }

                AppState.SUCCESS -> {
                    dismissLoadingDialog()
                    it.message?.let { message ->
                        showMessage(currentFocus!!, message)
                    }

                    //navigate to next screen of the app

                }

            }

        })
    }

    override fun showLoadingDialog() {
        setupProgressGroup.visibility = VISIBLE
    }

    override fun dismissLoadingDialog() {
        setupProgressGroup.visibility = GONE
        copyrightVersionTextView.visibility = VISIBLE
    }

}
