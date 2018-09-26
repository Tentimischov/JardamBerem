package com.baktiyar.android.jardamberem.ui.full_photo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.utils.Const.Companion.ALL_PHOTO_URLS
import com.baktiyar.android.jardamberem.utils.Const.Companion.INDEX
import kotlinx.android.synthetic.main.activity_full_photo.*
import kotlinx.android.synthetic.main.activity_main.*

class FullPhotoActivity : AppCompatActivity(), ViewPageAdapter.mClickListener {
    override fun onClick(position: Int) {

    }

    private var viewPageAdapter: ViewPageAdapter? = null
    private var index: Int? = null
    private var photoDataList: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_photo)


        init()



    }

    private fun init() {
        initArraysAdapter()
        initToolbar()
    }

    private fun initArraysAdapter() {
        photoDataList = ArrayList()
        photoDataList = intent.getStringArrayListExtra(ALL_PHOTO_URLS)
        index = intent.getIntExtra(INDEX, 0)

        viewPageAdapter = ViewPageAdapter(photoDataList!!, this, true)
        viewpager.adapter = viewPageAdapter
        viewpager.currentItem = index!!


    }

    private fun getFragmentList(): List<Fragment> {
        val data: ArrayList<Fragment> = ArrayList()
        for (i in 0 until photoDataList?.size!!) {
            val f = FullPhotoFragment.newInstance(photoDataList!![i])
            data.add(f)
        }

        return data
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
