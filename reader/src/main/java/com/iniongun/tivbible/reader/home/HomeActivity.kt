package com.iniongun.tivbible.reader.home

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.google.android.material.chip.Chip
import com.iniongun.tivbible.common.base.BaseActivity
import com.iniongun.tivbible.common.utils.capitalizeWords
import com.iniongun.tivbible.common.utils.getDeviceScreenSize
import com.iniongun.tivbible.common.utils.state.AppState.FAILED
import com.iniongun.tivbible.common.utils.state.AppState.SUCCESS
import com.iniongun.tivbible.entities.FontStyle
import com.iniongun.tivbible.entities.Setting
import com.iniongun.tivbible.entities.Theme
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.ActivityHomeBinding
import com.iniongun.tivbible.reader.more.MoreViewModel
import com.iniongun.tivbible.reader.read.ReadViewModelNew
import com.iniongun.tivbible.reader.read.adapters.HighlightColorsAdapter
import com.iniongun.tivbible.reader.search.SearchViewModel
import com.iniongun.tivbible.reader.utils.LineSpacingType
import com.iniongun.tivbible.reader.utils.LineSpacingType.*
import com.iniongun.tivbible.reader.utils.ModuleType
import com.iniongun.tivbible.reader.utils.ModuleType.*
import com.iniongun.tivbible.reader.utils.copyDataToClipboard
import com.iniongun.tivbible.reader.utils.shareData
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.font_settings_layout.*
import kotlinx.android.synthetic.main.verse_tap_actions_layout.*
import kotlinx.android.synthetic.main.verse_tap_actions_layout.view.*
import javax.inject.Inject


class HomeActivity : BaseActivity<ActivityHomeBinding, HomeActivityViewModel>() {

    @Inject
    lateinit var homeActivityViewModel: HomeActivityViewModel

    @Inject
    lateinit var searchViewModel: SearchViewModel

    @Inject
    lateinit var moreViewModel: MoreViewModel

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

    val deviceScreenSize by lazy { getDeviceScreenSize(resources) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController(R.id.nav_host_fragment)
        nav_view.setupWithNavController(navController)

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
                    it.setMessage("No shareable verse(s) selected!",  FAILED)
                } else {
                    shareData("${selectedVersesTextView.text}", "${selectedVersesTextView.text}\n\n${it.shareableSelectedVersesText}")
                }
            }
        }

        bookmarkButton.setOnClickListener {
            readViewModel?.let {
                if (it.shareableSelectedVersesText.isEmpty()) {
                    it.setMessage("No verse(s) selected to bookmark!",  FAILED)
                } else {
                    readViewModel?.saveBookmarks()
                }
            }
        }

        copyButton.setOnClickListener {
            readViewModel?.let {
                if (it.shareableSelectedVersesText.isEmpty()) {
                    it.setMessage("No verse(s) selected to copy!", FAILED)
                } else {
                    copyDataToClipboard(selectedVersesTextView.text.toString(), "${selectedVersesTextView.text}\n\n${it.shareableSelectedVersesText}")
                    it.setMessage("Verse(s) copied successfully!", SUCCESS)
                }
            }
        }

        fontSizeMinusButton.setOnClickListener {
            readViewModel?.let {
                it.decreaseFontSize()
            }
        }

        fontSizePlusButton.setOnClickListener {
            readViewModel?.let {
                it.increaseFontSize()
            }
        }

        lineSpacingTwoButton.setOnClickListener {
            readViewModel?.let {
                it.updateLineSpacing(TWO)
            }
        }

        lineSpacingThreeButton.setOnClickListener {
            readViewModel?.let {
                it.updateLineSpacing(THREE)
            }
        }

        lineSpacingFourButton.setOnClickListener {
            readViewModel?.let {
                it.updateLineSpacing(FOUR)
            }
        }

        goToSettingsButton.setOnClickListener {
            toggleFontSettingsBottomSheetVisibility()
            showModule(MORE)
        }
    }

    fun showVerseTapActionsBottomSheet(viewModel: ReadViewModelNew? = null) {

        if (readViewModel == null || highlightColorsAdapter == null) {
            readViewModel = viewModel
            highlightColorsAdapter = HighlightColorsAdapter(readViewModel!!)
            versesTapActionsBottomSheet.verseHighlightColorsRecyclerView.adapter = highlightColorsAdapter
            highlightColorsAdapter?.submitList(readViewModel!!.highlightColorsList)
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

    fun setupFontSettingsBottomSheet(vm: ReadViewModelNew) {
        readViewModel = vm
        toggleFontSettingsBottomSheetVisibility()
    }

    fun setupFontStylesAndThemesChipGroups(data: Pair<List<FontStyle>, List<Theme>>, setting: Setting) {
        fontStyleChipGroup.removeAllViews()
        themeChipGroup.removeAllViews()

        data.first.forEach {
            val chip = layoutInflater.inflate(R.layout.single_chip_layout, null, false) as Chip
            chip.text = it.name.removeSuffix(".ttf").replace("_", " ").capitalizeWords()
            chip.typeface = Typeface.createFromAsset(assets, "font/${it.name}")
            chip.isChecked = it.id == setting.fontStyle.id
            chip.setOnCheckedChangeListener { chipView, isChecked ->
                if (isChecked) {
                    fontStyleChipGroup.children.forEach { chpVw ->
                        val chp = chpVw as Chip
                        if (chp.isChecked && chpVw != chipView)
                            chp.isChecked = false
                    }
                    readViewModel?.let { vm -> vm.changeFontStyle(it) }
                }
            }
            fontStyleChipGroup.addView(chip)
        }

        data.second.forEach {
            val chip = layoutInflater.inflate(R.layout.single_chip_layout, null, false) as Chip
            chip.text = it.name.replace("_", " ").toLowerCase().capitalizeWords()
            chip.isChecked = it.id == setting.theme.id
            readViewModel?.let { vm -> chip.typeface = Typeface.createFromAsset(assets, "font/${vm.currentSettings.fontStyle.name}") }
            chip.setOnCheckedChangeListener { chipView, isChecked ->
                if (isChecked) {
                    themeChipGroup.children.forEach { chpVw ->
                        val chp = chpVw as Chip
                        if (chp.isChecked && chpVw != chipView)
                            chp.isChecked = false
                    }
                    readViewModel?.let { vm -> vm.changeTheme(it) }
                }
            }
            themeChipGroup.addView(chip)
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

    fun updateBottomSheetsUIElementsFontStyles(typeface: Typeface) {
        with(typeface) {
            setBottomNavViewTypeface(typeface = typeface)
            fontStyleChipGroup.children.forEach { (it as Chip).typeface = this }
            themeChipGroup.children.forEach { (it as Chip).typeface = this }
            fontSizeTextView.typeface = this
            fontSizeLabelTextView.typeface = this
            fontSizeMinusButton.typeface = this
            fontSizePlusButton.typeface = this
            lineSpacingLabelTextView.typeface = this
            fontStyleLabelTextView.typeface = this
            themeLabelTextView.typeface = this
            goToSettingsButton.typeface = this
            selectedVersesTextView.typeface = this
            shareButton.typeface = this
            bookmarkButton.typeface = this
            copyButton.typeface = this
            highlightWithLabelTextView.typeface = this
        }
    }

    fun setBottomNavViewTypeface(v: View? = nav_view, typeface: Typeface) {
        try {
            if (v is ViewGroup) {
                val vg = v
                for (i in 0 until vg.childCount) {
                    val child = vg.getChildAt(i)
                    setBottomNavViewTypeface(child, typeface)
                }
            } else if (v is TextView) {
                v.typeface = typeface
            }
        } catch (e: Exception) {
        }
    }

    fun updateFontSettingsUIStates(shouldEnable: Boolean) {
        fontSizePlusButton.isEnabled = shouldEnable
        fontSizeMinusButton.isEnabled = shouldEnable
        lineSpacingTwoButton.isEnabled = shouldEnable
        lineSpacingThreeButton.isEnabled = shouldEnable
        lineSpacingFourButton.isEnabled = shouldEnable
        fontStyleChipGroup.isEnabled = shouldEnable
        themeChipGroup.isEnabled = shouldEnable
    }

    fun updateFontSizeTextViewContent(fontSize: Int) {
        fontSizeTextView.text = getString(R.string.font_size_placeholder, fontSize)
    }

    fun updateSelectedVersesTextViewContent(selectedVersesText: String) {
        selectedVersesTextView.text = selectedVersesText
    }

    fun showModule(moduleType: ModuleType) {
        nav_view.selectedItemId = when (moduleType) {
            READER -> { R.id.navigation_read }
            SEARCH -> { R.id.navigation_search }
            MORE -> { R.id.navigation_more }
        }
    }

    fun setLineSpacingButtonsBackground(lineSpacingType: LineSpacingType) {
        when (lineSpacingType) {

            TWO -> {
                lineSpacingTwoButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                lineSpacingThreeButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
                lineSpacingFourButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
            }

            THREE -> {
                lineSpacingTwoButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
                lineSpacingThreeButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                lineSpacingFourButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
            }

            FOUR -> {
                lineSpacingTwoButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
                lineSpacingThreeButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
                lineSpacingFourButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            }
        }
    }

}
