package com.example.mygithubappsfix.user_interface.detail

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mygithubappsfix.R

class TabsPagerAdapter(private val mCtx: Context, fm: FragmentManager, data: Bundle) :FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    @StringRes
    private val TAB_TITLE = intArrayOf(R.string.tab_satu, R.string.tab_dua)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0-> fragment = FollowersFragment()
            1-> fragment = FollowingFragment()
        }
        fragment?.arguments =  this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mCtx.resources.getString(TAB_TITLE[position])
    }
}