package com.iniongun.tivbible.reader.home

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.iniongun.tivbible.common.base.BaseActivity
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.ActivityHomeBinding
import com.iniongun.tivbible.reader.read.ReadViewModel
import com.iniongun.tivbible.reader.read.adapters.HighlightColorsAdapter
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

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

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

        closeButton.setOnClickListener { showVerseTapActionsBottomSheet() }

        bottomSheetBehavior = BottomSheetBehavior.from(versesTapActionsBottomSheet)
    }



    fun showVerseTapActionsBottomSheet(viewModel: ReadViewModel? = null) {

        viewModel?.let {
            if (highlightColorsAdapter == null) {
                highlightColorsAdapter = HighlightColorsAdapter(it)
                versesTapActionsBottomSheet.verseHighlightColorsRecyclerView.adapter = highlightColorsAdapter
                highlightColorsAdapter!!.submitList(highlightColors)
            }
        }

        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        }
    }

}
