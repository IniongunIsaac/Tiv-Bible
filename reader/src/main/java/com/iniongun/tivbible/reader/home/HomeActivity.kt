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
import kotlinx.android.synthetic.main.verse_tap_actions_layout.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        bottomSheetBehavior = BottomSheetBehavior.from(versesTapActionsBottomSheet)

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

        closeButton.setOnClickListener { showBottomSheet() }
    }



    fun showBottomSheet() {
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        }
    }

}
