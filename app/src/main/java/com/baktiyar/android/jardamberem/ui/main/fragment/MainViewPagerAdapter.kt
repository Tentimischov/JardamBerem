package com.baktiyar.android.jardamberem.ui.main.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.StartApplication
import android.view.View.MeasureSpec



class MainViewPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    private var data = ArrayList<Fragment>()
    override fun getItem(position: Int): Fragment {
        return data[position]
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return  when (position) {
            0 -> StartApplication.INSTANCE.getString(R.string.give_help)
            else -> StartApplication.INSTANCE.getString(R.string.need_help)
        }
    }
    fun addFragment(fragment: Fragment) {
        data.add(fragment)
    }
}