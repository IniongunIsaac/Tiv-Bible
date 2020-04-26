package com.iniongungroup.mobile.android.references.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.iniongungroup.mobile.android.references.fragments.BooksFragment
import com.iniongungroup.mobile.android.references.fragments.ChaptersFragment
import com.iniongungroup.mobile.android.references.fragments.VersesFragment

/**
 * Created by Isaac Iniongun on 26/04/2020.
 * For Tiv Bible project.
 */

class ReferencesAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> BooksFragment()
        1 -> ChaptersFragment()
        else -> VersesFragment()
    }

}