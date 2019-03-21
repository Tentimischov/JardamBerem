package com.baktiyar.android.jardamberem.ui.full_photo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_full_photo.view.*


class FullPhotoFragment : Fragment() {
    private var pUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pUri = arguments?.getString("__")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_full_photo, container, false)
        Glide.with(container?.context!!)
                .load(pUri)
                .override(150)
                .into(v.im)
        v.im.setOnTouchListener(ImageMatrixTouchHandler(v.context))
        return v
    }


    companion object {
        fun newInstance(uri: String): FullPhotoFragment {
            val fragment = FullPhotoFragment()
            val bundle = Bundle()
            bundle.putString("__", uri)
            fragment.arguments = bundle
            return fragment
        }
    }
}