package com.baktiyar.android.jardamberem.ui.product.post_product.adapter


/**
 * Created by admin on 12.03.2018.
 */

import android.content.Context
import android.os.Parcelable
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.baktiyar.android.jardamberem.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*


class ImageAdapter(
        private val context: Context,
        private val imagePaths: ArrayList<String>?,
        private var listener: OnItemClickListener) : PagerAdapter(){

    private var inflater: LayoutInflater

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return imagePaths!!.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.item_image, view, false)!!
        val imageView = imageLayout.findViewById(R.id.ivImageNewProduct) as ImageView
        /*
        val imageViewRemove = imageLayout.findViewById(R.id.ivRemoveImageFromList) as ImageView*/
        Glide
                .with(context)
                .asBitmap()
                .load(imagePaths!![position])
                .into(imageView)
        /*if (imagePaths.size > 1) {
            imageViewRemove.visibility = View.VISIBLE
            imageViewRemove.setOnClickListener(
                    object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            imagePaths.removeAt(position - 1)
                            notifyDataSetChanged()
                            listener.onClick(position)
                        }
                    }
            )
        }*/

        view.addView(imageLayout)
        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }


}


