package com.iniongun.tivbible.common.base

import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongun.tivbible.common.utils.navigation.AppFragmentNavCommands
import com.iniongun.tivbible.common.utils.state.AppState
import dagger.android.support.DaggerFragment

/**
 * Created by Isaac Iniongun on 2019-11-26
 * For Tiv Bible project
 */

abstract class BaseFragment<in D : ViewDataBinding, out V : BaseViewModel> : DaggerFragment() {

    abstract fun getViewModel(): V

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getBindingVariable(): Int

    abstract fun getLayoutBinding(binding: D)

    open fun setViewModelObservers() {}

    private val baseActivity by lazy {
        activity as BaseActivity<*, *>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: D = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        binding.apply {

            setVariable(getBindingVariable(), getViewModel())

            executePendingBindings()

            lifecycleOwner = this@BaseFragment
        }

        setViewModelObservers()

        setNotificationObserver()

        getLayoutBinding(binding)

        return binding.root
    }

    open fun setNotificationObserver() {
        //baseActivity.setNotificationObserver()

        getViewModel().notificationLiveData.observe(this, LiveDataEventObserver {

            when (it.state) {

                AppState.FAILED -> {
                    dismissLoadingDialog()
                    it.message?.let { message ->
                        showMessage(view!!, message, isError = true)
                    }
                }

                AppState.WARNING -> {
                    dismissLoadingDialog()
                    it.message?.let { message ->
                        showMessage(view!!, message, isWarning = true)
                    }

                }

                AppState.LOADING -> {
                    showLoadingDialog()
                }

                AppState.SUCCESS -> {
                    dismissLoadingDialog()
                    it.message?.let { message ->
                        showMessage(view!!, message)
                    }

                }

            }

        })

    }

    protected fun navigate(fragmentNavCommand: AppFragmentNavCommands) {
        when (fragmentNavCommand) {
            is AppFragmentNavCommands.Back -> findNavController().navigateUp()
            is AppFragmentNavCommands.To -> findNavController().navigate(fragmentNavCommand.direction)
        }
    }

    fun hideKeyBoard(token: IBinder) {
        val inputMethodManager = activity?.getSystemService<InputMethodManager>()
        inputMethodManager?.hideSoftInputFromWindow(token, 0)
    }

    fun showKeyBoard() {
        val inputMethodManager = activity?.getSystemService<InputMethodManager>()
        inputMethodManager?.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    fun showMessage(
        view: View,
        message: String,
        isError: Boolean = false,
        duration: Int = Snackbar.LENGTH_SHORT,
        isWarning: Boolean = false
    ) = (activity as BaseActivity<*, *>).showMessage(view, message, isError, duration, isWarning)



    open fun showLoadingDialog() = (activity as BaseActivity<*, *>).showLoadingDialog()

    open fun dismissLoadingDialog() = (activity as BaseActivity<*, *>).dismissLoadingDialog()

    fun showView(view: View, shouldShow: Boolean) {
        activity?.let {
            (it as BaseActivity<*, *>).showView(view, shouldShow)
        }
    }

}