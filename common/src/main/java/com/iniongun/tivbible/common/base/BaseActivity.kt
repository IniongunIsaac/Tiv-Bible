package com.iniongun.tivbible.common.base

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.iniongun.tivbible.common.R
import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by Isaac Iniongun on 2019-11-26
 * For Tiv Bible project
 */

abstract class BaseActivity<in D : ViewDataBinding, out V : ViewModel> :
    DaggerAppCompatActivity() {

    private lateinit var dialog: AlertDialog

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V

    abstract fun getBindingVariable(): Int

    abstract fun getBinding(binding: D)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeDataBinding()

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        createDialog()
    }

    private fun hideKeyboardWhenUserTapsOutsideEditText() {
        val viewGroup =
            (this.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        viewGroup.setOnTouchListener { _, _ ->
            hideKeyboard(this)
            true
        }
    }

    private fun initializeDataBinding() {
        val binding: D = DataBindingUtil.setContentView(this, getLayoutId())

        binding.apply {

            setVariable(getBindingVariable(), getViewModel())

            executePendingBindings()
        }

        getBinding(binding)
    }

    private fun createDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setView(R.layout.layout_progress_dialog)
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun hideKeyboard(activity: Activity) {
        val imm = getSystemService<InputMethodManager>()
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showSnackBar(
        rootView: View,
        text: String,
        isError: Boolean = false,
        duration: Int = Snackbar.LENGTH_SHORT,
        isWarning: Boolean = false
    ) {
        val snackBar = Snackbar.make(rootView, text, duration)
        val param = snackBar.view.layoutParams as FrameLayout.LayoutParams
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

        if (isWarning) snackBarLayout.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.warning
            )
        )
        if (isError) snackBarLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        else snackBarLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))

        snackBarLayout.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            .setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        param.gravity = Gravity.TOP
        snackBar.view.layoutParams = param
        snackBar.show()
    }

    fun showLoadingDialog() {
        hideKeyboard(this)
        dialog.show()
    }

    fun dismissLoadingDialog() {
        if (dialog.isShowing) dialog.dismiss()
    }
}