package com.baktiyar.android.jardamberem.ui.full_photo

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class Adapter(var data: List<Fragment>, manager: FragmentManager) : FragmentStatePagerAdapter(manager) {
    override fun getItem(position: Int): Fragment {
        return data[position]
    }

    override fun getCount(): Int = data.size


}