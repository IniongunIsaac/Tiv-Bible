package com.iniongun.tivbible.presentation.splash

import com.iniongun.tivbible.R
import com.iniongun.tivbible.common.base.BaseActivity
import com.iniongun.tivbible.databinding.ActivitySplashBinding
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

}
