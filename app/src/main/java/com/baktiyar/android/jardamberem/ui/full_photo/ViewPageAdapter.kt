package com.baktiyar.android.jardamberem.ui.full_photo

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.utils.Utils.Companion.e
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_full_photo.view.*


class ViewPageAdapter(var data: ArrayList<String>, var mClick: mClickListener, private val zoomable: Boolean) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.fragment_full_photo, container, false)
        Picasso.get().load(data[position]).into(view.im)
        if (zoomable)
            view.im.setOnTouchListener(ImageMatrixTouchHandler(view.getContext()))

        view.setOnClickListener {
            mClick.onClick(position)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    interface mClickListener {
        fun onClick(position: Int)
    }

}