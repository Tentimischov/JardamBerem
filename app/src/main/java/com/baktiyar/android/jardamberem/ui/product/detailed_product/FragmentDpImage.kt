package com.baktiyar.android.jardamberem.ui.product.detailed_product

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_full_photo.view.*


class FragmentDpImage : Fragment() {
    private var pUri: String? = null
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pUri = arguments?.getString("__")
        id = arguments?.getInt("id")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_full_photo, container, false)
        Picasso.get().load(pUri).into(v.im)
        v.im.setOnTouchListener(ImageMatrixTouchHandler(v.context))
        v.setOnClickListener {
            e("__________", id.toString())
        }

        return v
    }

    companion object {
        fun newInstance(uri: String, id: Int): FragmentDpImage {
            val fragment = FragmentDpImage()
            val bundle = Bundle()
            bundle.putString("__", uri)
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }
}