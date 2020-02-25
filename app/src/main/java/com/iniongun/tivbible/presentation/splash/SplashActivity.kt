package com.iniongun.tivbible.presentation.splash

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.iniongun.tivbible.BR
import com.iniongun.tivbible.R
import com.iniongun.tivbible.common.base.BaseActivity
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongun.tivbible.common.utils.navigation.AppActivityNavCommands
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongun.tivbible.databinding.ActivitySplashBinding
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashActivityViewModel>() {

    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var splashActivityViewModel: SplashActivityViewModel

    override fun getLayoutId() = R.layout.activity_splash

    override fun getViewModel() = splashActivityViewModel

    override fun getBindingVariable() = BR.ViewModel

    override fun getBinding(binding: ActivitySplashBinding) {
        this.binding = binding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideStatusAndNavigationBar()

        observeStartHomeActivityLiveData()

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

                    startHomeActivity()

                }

            }

        })
    }

    private fun startHomeActivity() {
        //navigate to reader section of the app
        startActivity(AppActivityNavCommands.getHomeActivityIntent(this))
        //remove splash activity from backstack
        finish()
    }

    private fun observeStartHomeActivityLiveData() {
        splashActivityViewModel.startHomeLiveData.observe(this, LiveDataEventObserver {
            if (it) {
                dismissLoadingDialog()
                Timer().schedule(3000) {
                    startHomeActivity()
                }
            }
        })
    }

    override fun showLoadingDialog() {
        setupProgressGroup.visibility = VISIBLE
        copyrightVersionTextView.visibility = GONE
    }

    override fun dismissLoadingDialog() {
        setupProgressGroup.visibility = GONE
        progressBar.visibility = GONE
        setupInProgressTextView.visibility = GONE
        copyrightVersionTextView.visibility = VISIBLE
    }

}
