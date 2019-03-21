package com.baktiyar.android.jardamberem.ui.main.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.StartApplication


class ViewPagerAdapter(fm: FragmentManager, var fragments: Array<Fragment>) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0)
            StartApplication.INSTANCE.getString(R.string.give_help)
        else
            StartApplication.INSTANCE.getString(R.string.need_help)
    }
}