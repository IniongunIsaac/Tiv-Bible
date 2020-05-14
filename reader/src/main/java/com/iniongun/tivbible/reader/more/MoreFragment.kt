package com.iniongun.tivbible.reader.more

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongun.tivbible.common.utils.navigation.AppFragmentNavCommands
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.MoreFragmentBinding
import com.iniongun.tivbible.reader.home.HomeActivity
import com.iniongun.tivbible.reader.more.adapters.MoreItemsAdapter
import com.iniongun.tivbible.reader.utils.MoreItemType.*
import com.iniongun.tivbible.reader.utils.moreItems
import com.iniongun.tivbible.reader.utils.shareDataHTML
import kotlinx.android.synthetic.main.more_fragment.*


class MoreFragment : BaseFragment<MoreFragmentBinding, MoreViewModel>() {

    private val moreViewModel by lazy { (requireActivity() as HomeActivity).moreViewModel }

    private lateinit var moreFragmentBinding: MoreFragmentBinding

    override fun getViewModel() = moreViewModel

    override fun getLayoutId() = R.layout.more_fragment

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: MoreFragmentBinding) {
        moreFragmentBinding = binding
    }

    private val moreItemsAdapter by lazy { MoreItemsAdapter(moreViewModel) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMoreItemsRecyclerView()
    }

    private fun setupMoreItemsRecyclerView() {
        moreItemsRecyclerView.adapter = moreItemsAdapter
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
        observeSettings()
        observeMoreItemSelected()
    }

    private fun observeSettings() {
        moreViewModel.settings.observe(this, Observer {
            val typeface = Typeface.createFromAsset(activity!!.assets, "font/${it.fontStyle.name}")
            moreTitleTextView.typeface = typeface
            (requireActivity() as HomeActivity).setBottomNavViewTypeface(typeface = typeface)
            moreItemsAdapter.submitList(moreItems)
        })
    }

    private fun observeMoreItemSelected() {
        moreViewModel.itemSelected.observe(this, LiveDataEventObserver {
            when (it.type) {
                BOOKMARKS -> { navigate(AppFragmentNavCommands.To(MoreFragmentDirections.actionNavigationMoreToBookmarksFragment())) }
                HIGHLIGHTS -> { navigate(AppFragmentNavCommands.To(MoreFragmentDirections.actionNavigationMoreToHighlightsFragment())) }
                NOTES -> { navigate(AppFragmentNavCommands.To(MoreFragmentDirections.actionNavigationMoreToNotesFragment())) }
                CREED, COMMANDMENTS, ABOUT, LORDS_PRAYER -> { navigate(AppFragmentNavCommands.To(MoreFragmentDirections.actionNavigationMoreToMiscContentFragment())) }
                SHARE -> { activity?.shareDataHTML("Tiv Bible Mobile App", Html.fromHtml(getString(R.string.share_tiv_bible_app_content))) }
                RATING -> { handleRatingSelected() }
                HELP -> { moreViewModel.postSuccessMessage("Coming Soon!") }
                SETTINGS -> { navigate(AppFragmentNavCommands.To(MoreFragmentDirections.actionNavigationMoreToSettingsFragment())) }
            }
        })
    }

    private fun handleRatingSelected() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.iniongun.tivbible")))
        } catch (e: ActivityNotFoundException) {
            startActivity( Intent( Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.iniongun.tivbible")))
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

    override fun showLoadingDialog() { progressBar.visibility = VISIBLE }

    override fun dismissLoadingDialog() { progressBar.visibility = GONE }

}
