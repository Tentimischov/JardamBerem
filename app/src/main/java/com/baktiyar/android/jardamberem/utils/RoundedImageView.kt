package com.baktiyar.android.jardamberem.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.baktiyar.android.jardamberem.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.dip
import com.squareup.picasso.Target
import java.lang.Exception

class RoundedImageView(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet), Target {
    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        drawable = errorDrawable
    }

    constructor(context: Context) : this(context, null)

    private var drawable: Drawable? = null
        set(value) {
            field = value
            postInvalidate()
        }

    fun loadImage(url: String?) {
        if (url == null) {
            drawable = null
        } else {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.mountain)
                    .error(R.drawable.mountain)
                    .into(this)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        drawable?.setBounds(0, 0, width, height)
        drawable?.draw(canvas)
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        drawable = placeHolderDrawable
    }


    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        val roundedDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
        roundedDrawable.cornerRadius = dip(DEFAULT_RADIUS).toFloat()
        drawable = roundedDrawable
    }

    companion object {
        private const val DEFAULT_RADIUS = 5
    }
}
