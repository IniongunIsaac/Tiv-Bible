package com.iniongun.tivbible.reader.more.settings

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.common.utils.*
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEventObserver
import com.iniongun.tivbible.common.utils.navigation.AppFragmentNavCommands
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.SettingsFragmentBinding
import com.iniongun.tivbible.reader.utils.LineSpacingType
import com.iniongun.tivbible.reader.utils.LineSpacingType.*
import kotlinx.android.synthetic.main.settings_fragment.*
import javax.inject.Inject

class SettingsFragment : BaseFragment<SettingsFragmentBinding, SettingsViewModel>() {

    @Inject
    lateinit var settingsViewModel: SettingsViewModel

    private lateinit var settingsFragmentBinding: SettingsFragmentBinding

    override fun getViewModel() = settingsViewModel

    override fun getLayoutId() = R.layout.settings_fragment

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: SettingsFragmentBinding) {
        settingsFragmentBinding = binding
    }

    private val deviceScreenSize by lazy { getDeviceScreenSize(context!!.resources) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {

        backButton.setOnClickListener { navigate(AppFragmentNavCommands.Back) }

        fontSizeMinusButton.setOnClickListener {
            settingsViewModel.decreaseFontSize()
        }

        fontSizePlusButton.setOnClickListener {
            settingsViewModel.increaseFontSize()
        }

        lineSpacingTwoButton.setOnClickListener {
            settingsViewModel.updateLineSpacing(TWO)
        }

        lineSpacingThreeButton.setOnClickListener {
            settingsViewModel.updateLineSpacing(THREE)
        }

        lineSpacingFourButton.setOnClickListener {
            settingsViewModel.updateLineSpacing(FOUR)
        }

        stayAwakeCheckBox.setOnCheckedChangeListener { _, isChecked -> settingsViewModel.changeStayAwakeSetting(isChecked) }
    }

    override fun setViewModelObservers() {
        super.setViewModelObservers()
        observeAudioSpeeds()
        observeFontStyles()
        observeSettings()
        observeThemes()
        observeShouldEnableFontSettingsUIControls()
    }

    private fun observeSettings() {
        settingsViewModel.settings.observe(this, Observer { setting ->

            fontSizeTextView.text = getString(R.string.font_size_placeholder, setting.fontSize)
            with(stayAwakeCheckBox) {
                isChecked = setting.stayAwake
                text = if (isChecked) "Yes" else "No"
            }

            with(Typeface.createFromAsset(activity!!.assets, "font/${setting.fontStyle.name}")) {
                fontStyleChipGroup.children.forEach { (it as Chip).typeface = this }
                themeChipGroup.children.forEach { (it as Chip).typeface = this }
                audioSpeedChipGroup.children.forEach { (it as Chip).typeface = this }
                toolbarTitleTextView.typeface = this
                fontSizeTextView.typeface = this
                fontSizeLabelTextView.typeface = this
                fontSizeMinusButton.typeface = this
                fontSizePlusButton.typeface = this
                lineSpacingLabelTextView.typeface = this
                fontStyleLabelTextView.typeface = this
                themeLabelTextView.typeface = this
                stayAwakeLabelTextView.typeface = this
                stayAwakeCheckBox.typeface = this
                audioSpeedLabelTextView.typeface = this
            }

            when (setting.lineSpacing) {
                getDeviceLineSpacingTwo(deviceScreenSize) -> { setLineSpacingButtonsBackground(TWO) }
                getDeviceLineSpacingThree(deviceScreenSize) -> { setLineSpacingButtonsBackground(THREE) }
                getDeviceLineSpacingFour(deviceScreenSize) -> { setLineSpacingButtonsBackground(FOUR) }
            }
        })
    }

    private fun setLineSpacingButtonsBackground(lineSpacingType: LineSpacingType) {
        when (lineSpacingType) {

            TWO -> {
                lineSpacingTwoButton.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                lineSpacingThreeButton.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.transparent))
                lineSpacingFourButton.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.transparent))
            }

            THREE -> {
                lineSpacingTwoButton.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.transparent))
                lineSpacingThreeButton.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                lineSpacingFourButton.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.transparent))
            }

            FOUR -> {
                lineSpacingTwoButton.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.transparent))
                lineSpacingThreeButton.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.transparent))
                lineSpacingFourButton.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
            }
        }
    }

    private fun observeFontStyles() {
        settingsViewModel.fontStyles.observe(this, Observer { fontStyles ->
            fontStyleChipGroup.removeAllViews()
            fontStyles.forEach {
                val chip = layoutInflater.inflate(R.layout.single_chip_layout, null, false) as Chip
                chip.text = it.name.removeSuffix(".ttf").replace("_", " ").capitalizeWords()
                chip.isChecked = it.id == settingsViewModel.currentSettings.fontStyle.id
                chip.typeface = Typeface.createFromAsset(activity!!.assets, "font/${it.name}")
                chip.setOnCheckedChangeListener { chipView, isChecked ->
                    if (isChecked) {
                        fontStyleChipGroup.children.forEach { chpVw ->
                            val chp = chpVw as Chip
                            if (chp.isChecked && chpVw != chipView)
                                chp.isChecked = false
                        }
                        settingsViewModel.changeFontStyle(it)
                    }
                }
                fontStyleChipGroup.addView(chip)
            }

        })
    }

    private fun observeThemes() {
        settingsViewModel.themes.observe(this, Observer { themes ->
            themeChipGroup.removeAllViews()
            themes.forEach {
                val chip = layoutInflater.inflate(R.layout.single_chip_layout, null, false) as Chip
                chip.text = it.name.replace("_", " ").toLowerCase().capitalizeWords()
                chip.typeface = Typeface.createFromAsset(activity!!.assets, "font/${settingsViewModel.currentSettings.fontStyle.name}")
                chip.isChecked = it.id == settingsViewModel.currentSettings.theme.id
                chip.setOnCheckedChangeListener { chipView, isChecked ->
                    if (isChecked) {
                        themeChipGroup.children.forEach { chpVw ->
                            val chp = chpVw as Chip
                            if (chp.isChecked && chpVw != chipView)
                                chp.isChecked = false
                        }
                        settingsViewModel.changeTheme(it)
                    }
                }
                themeChipGroup.addView(chip)
            }
        })
    }

    private fun observeAudioSpeeds() {
        settingsViewModel.audioSpeeds.observe(this, Observer { audioSpeeds ->
            audioSpeedChipGroup.removeAllViews()
            audioSpeeds.forEach {
                val chip = layoutInflater.inflate(R.layout.single_chip_layout, null, false) as Chip
                chip.text = it.name.replace("_", " ").toLowerCase().capitalizeWords()
                chip.typeface = Typeface.createFromAsset(activity!!.assets, "font/${settingsViewModel.currentSettings.fontStyle.name}")
                chip.isChecked = it.id == settingsViewModel.currentSettings.audioSpeed.id
                chip.setOnCheckedChangeListener { chipView, isChecked ->
                    if (isChecked) {
                        audioSpeedChipGroup.children.forEach { chpVw ->
                            val chp = chpVw as Chip
                            if (chp.isChecked && chpVw != chipView)
                                chp.isChecked = false
                        }
                        settingsViewModel.changeAudioSpeed(it)
                    }
                }
                audioSpeedChipGroup.addView(chip)
            }
        })
    }

    private fun observeShouldEnableFontSettingsUIControls() {
        settingsViewModel.shouldEnableFontSettingsUIControls.observe(this, Observer {
            updateFontSettingsUIStates(it)
        })
    }

    private fun updateFontSettingsUIStates(shouldEnable: Boolean) {
        fontSizePlusButton.isEnabled = shouldEnable
        fontSizeMinusButton.isEnabled = shouldEnable
        lineSpacingTwoButton.isEnabled = shouldEnable
        lineSpacingThreeButton.isEnabled = shouldEnable
        lineSpacingFourButton.isEnabled = shouldEnable
        fontStyleChipGroup.isEnabled = shouldEnable
        themeChipGroup.isEnabled = shouldEnable
        audioSpeedChipGroup.isEnabled = shouldEnable
        stayAwakeCheckBox.isEnabled = shouldEnable
    }

    override fun setNotificationObserver() {
        settingsViewModel.notificationLiveData.observe(this, LiveDataEventObserver {

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

    override fun showLoadingDialog() { progressBar.visibility = View.VISIBLE }

    override fun dismissLoadingDialog() { progressBar.visibility = View.GONE }

}
