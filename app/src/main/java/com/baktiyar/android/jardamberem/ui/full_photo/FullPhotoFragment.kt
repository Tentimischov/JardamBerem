package com.baktiyar.android.jardamberem.ui.full_photo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.baktiyar.android.jardamberem.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_full_photo.view.*
import com.baktiyar.android.jardamberem.R.id.view
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler


class FullPhotoFragment : Fragment() {
    private var pUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pUri = arguments?.getString("__")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_full_photo, container, false)
        Picasso.get().load(pUri).into(v.im)
        v.im.setOnTouchListener(ImageMatrixTouchHandler(v.context))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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