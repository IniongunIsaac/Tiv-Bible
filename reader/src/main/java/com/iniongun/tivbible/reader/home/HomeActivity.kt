package com.iniongun.tivbible.reader.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.iniongun.tivbible.common.base.BaseActivity
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.ActivityHomeBinding
import com.iniongun.tivbible.reader.read.ReadViewModelNew
import com.iniongun.tivbible.reader.read.adapters.HighlightColorsAdapter
import kotlinx.android.synthetic.main.font_settings_layout.*
import kotlinx.android.synthetic.main.verse_tap_actions_layout.*
import kotlinx.android.synthetic.main.verse_tap_actions_layout.view.*
import javax.inject.Inject


class HomeActivity : BaseActivity<ActivityHomeBinding, HomeActivityViewModel>() {

    @Inject
    lateinit var homeActivityViewModel: HomeActivityViewModel

    private lateinit var activityHomeBinding: ActivityHomeBinding

    override fun getLayoutId() = R.layout.activity_home

    override fun getViewModel() = homeActivityViewModel

    override fun getBindingVariable() = BR.ViewModel

    override fun getBinding(binding: ActivityHomeBinding) {
        activityHomeBinding = binding
    }

    private lateinit var versesTapActionsBottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var fontSettingsBottomSheetBehavior: BottomSheetBehavior<View>
    var versesTapActionsBottomSheetShowing = false
    var fontSettingsBottomSheetShowing = false
    private var readViewModel: ReadViewModelNew? = null

    private var highlightColorsAdapter: HighlightColorsAdapter? = null
    private val highlightColors = arrayListOf(
        R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5, R.color.color6, R.color.color7, R.color.color8, R.color.color9, R.color.color10,
        R.color.color11, R.color.color12, R.color.color13, R.color.color14, R.color.color15, R.color.color16, R.color.color17, R.color.color18, R.color.color19, R.color.color20
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_read, R.id.navigation_search, R.id.navigation_more
            )
        )

        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setOnClickListeners()

        versesTapActionsBottomSheetBehavior = from(versesTapActionsBottomSheet)
        fontSettingsBottomSheetBehavior = from(fontSettingsBottomSheet)
    }

    private fun setOnClickListeners() {
        closeButton.setOnClickListener { toggleVerseTapActionsBottomSheetVisibility() }

        closeFontSettingsButton.setOnClickListener { toggleFontSettingsBottomSheetVisibility() }

        shareButton.setOnClickListener {
            readViewModel?.let {
                if (it.shareableSelectedVersesText.isEmpty()) {
                    it.setMessage("No shareable verse(s) selected!",  AppState.FAILED)
                } else {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, selectedVersesTextView.text)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "${selectedVersesTextView.text}\n\n${it.shareableSelectedVersesText}")
                    startActivity(Intent.createChooser(shareIntent, "Share Via"))
                }
            }
        }

        bookmarkButton.setOnClickListener {
            readViewModel?.let {
                if (it.shareableSelectedVersesText.isEmpty()) {
                    it.setMessage("No verse(s) selected to bookmark!",  AppState.FAILED)
                } else {
                    readViewModel?.setMessage("Coming soon!", AppState.SUCCESS)
                }
            }
        }

        copyButton.setOnClickListener {

            readViewModel?.let {
                if (it.shareableSelectedVersesText.isEmpty()) {
                    it.setMessage("No verse(s) selected to copy!", AppState.FAILED)
                } else {
                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText(selectedVersesTextView.text, "${selectedVersesTextView.text}\n\n${it.shareableSelectedVersesText}")
                    clipboard.setPrimaryClip(clip)
                    it.setMessage("Verse(s) copied successfully!", AppState.SUCCESS)
                }
            }
        }
    }


    fun showVerseTapActionsBottomSheet(viewModel: ReadViewModelNew? = null) {
        viewModel?.let {
            readViewModel = it
            if (highlightColorsAdapter == null) {
                highlightColorsAdapter = HighlightColorsAdapter(it)
                versesTapActionsBottomSheet.verseHighlightColorsRecyclerView.adapter = highlightColorsAdapter
                highlightColorsAdapter!!.submitList(highlightColors)
            }
        }
        toggleVerseTapActionsBottomSheetVisibility()
    }

    fun toggleVerseTapActionsBottomSheetVisibility() {
        with(versesTapActionsBottomSheetBehavior) {
            if (state != STATE_EXPANDED) {
                state = STATE_EXPANDED
                versesTapActionsBottomSheetShowing = true
            } else {
                state = STATE_COLLAPSED
                versesTapActionsBottomSheetShowing = false
            }
        }
    }

    fun toggleFontSettingsBottomSheetVisibility() {
        with(fontSettingsBottomSheetBehavior) {
            if (state != STATE_EXPANDED) {
                state = STATE_EXPANDED
                fontSettingsBottomSheetShowing = true
            } else {
                state = STATE_COLLAPSED
                fontSettingsBottomSheetShowing = false
            }
        }
    }

    fun showSelectedVersesText(selectedVersesText: String) {
        selectedVersesTextView.text = selectedVersesText
    }

}
