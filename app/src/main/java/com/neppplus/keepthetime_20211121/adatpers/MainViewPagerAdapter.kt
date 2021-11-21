package com.neppplus.keepthetime_20211121.adatpers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.keepthetime_20211121.fragments.MyProfileFragment
import com.neppplus.keepthetime_20211121.fragments.ScheduleListFragment

class MainViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount() = 2

    override fun getItem(position: Int): Fragment {

        return when(position) {
            0 -> ScheduleListFragment()
            else -> MyProfileFragment()
        }

    }
}