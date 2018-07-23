package com.baktiyar.android.jardamberem.ui.product.detailed_product.adapter


import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Parcelable
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.baktiyar.android.jardamberem.R
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import java.util.*
import com.bumptech.glide.request.RequestOptions




class DetailedImageAdapter(private val context: Context,
                           private val imagePaths: ArrayList<String>?) : PagerAdapter() {
    private val inflater: LayoutInflater


    init {
        if (imagePaths?.size == 0) {
            val uri = Uri.parse("android.resource://com.baktiyar.android.jardamberem/drawable/img_no_photo")
            imagePaths.add(uri.toString())
        }
        inflater = LayoutInflater.from(context)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return imagePaths!!.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.item_detailed_image, view, false)!!
        val imageView = imageLayout.findViewById(R.id.ivImageDetailedProduct) as ImageView
        Glide.with(context).load(imagePaths!![position]).apply(RequestOptions().fitCenter()).into(imageView)

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