package com.iniongungroup.mobile.android.references

import android.graphics.Typeface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.iniongun.tivbible.common.base.BaseActivity
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongungroup.mobile.android.references.adapters.ReferencesAdapter
import com.iniongungroup.mobile.android.references.databinding.ActivityReferencesBinding
import kotlinx.android.synthetic.main.activity_references.*
import javax.inject.Inject

class ReferencesActivity : BaseActivity<ActivityReferencesBinding, ReferencesViewModel>() {

    @Inject
    lateinit var referencesViewModel: ReferencesViewModel

    private lateinit var activityReferencesBinding: ActivityReferencesBinding

    override fun getLayoutId() = R.layout.activity_references

    override fun getViewModel() = referencesViewModel

    override fun getBindingVariable() = BR.viewModel

    override fun getBinding(binding: ActivityReferencesBinding) {
        activityReferencesBinding = binding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureViewPagerAndTabLayout()

        configureToolbar()
        observeSettings()
    }

    private fun configureViewPagerAndTabLayout() {
        viewPager.adapter = ReferencesAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Books"
                1 -> "Chapters"
                else -> "Verses"
            }
        }.attach()
    }

    private fun observeSettings() {
        referencesViewModel.settings.observe(this, Observer { setting ->

            val typeface = Typeface.createFromAsset(assets, "font/${setting.fontStyle.name}")
            toolbarTitleTextView.typeface = typeface
            updateTabLayoutFontStyle(typeface)
        })
    }

    private fun updateTabLayoutFontStyle(typeface: Typeface?) {
        val vg = tabLayout.getChildAt(0) as ViewGroup
        for (i: Int in 0..vg.childCount) {
            val vgTab = vg.getChildAt(i) as ViewGroup?
            vgTab?.let {
                for (j: Int in 0..vgTab.childCount) {
                    val tab = vgTab.getChildAt(j)
                    if (tab is TextView) {
                        tab.typeface = typeface
                    }
                }
            }
        }
    }

    fun setViewPagerItem(position: Int) {
        viewPager.currentItem = position
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> { super.onOptionsItemSelected(item) }
    }

    override fun setNotificationObserver() {
        referencesViewModel.notificationLiveData.observe(this, LiveDataEventObserver {

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

                }

            }

        })
    }

    override fun showLoadingDialog() {
        progressBar.visibility = View.VISIBLE
    }

    override fun dismissLoadingDialog() {
        progressBar.visibility = View.GONE
    }
}
