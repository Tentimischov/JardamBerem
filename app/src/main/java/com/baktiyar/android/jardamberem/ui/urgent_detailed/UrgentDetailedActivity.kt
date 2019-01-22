package com.baktiyar.android.jardamberem.ui.urgent_detailed

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.provider.Settings
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Urgent
import com.baktiyar.android.jardamberem.ui.delete_fragment_dialog.FragDialog
import com.baktiyar.android.jardamberem.ui.full_photo.ViewPageAdapter
import com.baktiyar.android.jardamberem.ui.full_photo.FullPhotoActivity
import com.baktiyar.android.jardamberem.ui.product.detailed_product.FragmentDpImage
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTION_URGENT
import com.baktiyar.android.jardamberem.utils.Const.Companion.ALL_PHOTO_URLS
import com.baktiyar.android.jardamberem.utils.Const.Companion.INDEX
import kotlinx.android.synthetic.main.activity_dp.*
import kotlinx.android.synthetic.main.toolbar.*

class UrgentDetailedActivity() : AppCompatActivity(), View.OnClickListener, ViewPageAdapter.mClickListener {
    override fun onClick(position: Int) {
        goToFullImageActivity(position)
    }


    private var data: Urgent? = null
    private var isMyProduct: Boolean = false
    private var imageViewPageAdapter: ViewPageAdapter? = null

    private var photoListData: ArrayList<String>? = null

    constructor(parcel: Parcel) : this() {
        data = parcel.readParcelable(Urgent::class.java.classLoader)
        isMyProduct = parcel.readByte() != 0.toByte()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dp)
        title = getString(R.string.urgent_help)
        init()

    }

    fun init() {
        initToolbar()
        initData()
        initUi()
    }

    private fun initUi() {

        if (data?.userImeiCode == getAndroidId()) {
            isMyProduct = true
            button_content_text.text = getString(R.string.delete_product)
            tvPhoneNumberDetailedProduct.visibility = View.VISIBLE
            tvPhoneNumberDetailedProduct.text = data?.phoneNumber


        } else {
            button_content_text.text = getString(R.string.show_number)

            if (data?.phoneNumber?.length == 10) {
                tvPhoneNumberDetailedProduct.text = data?.phoneNumber?.substring(0, 3) + "XX XX XX"
            } else {
                button_content_text.text = getString(R.string.no_number)
                button_content_text.isClickable = false
                button_content_text.isEnabled = false
                button_content_text.isFocusable = false
                tvPhoneNumberDetailedProduct.isClickable = false
                tvPhoneNumberDetailedProduct.isEnabled = false
                tvPhoneNumberDetailedProduct.isFocusable = false
                tvPhoneNumberDetailedProduct.visibility = View.GONE
            }
        }

        btDeleteProduct.setOnClickListener(this)

        tvTitleDetailedProduct.text = data?.title
        tvDescriptionDetailedProduct.text = data?.description
        tvDateDetailedProduct.text = data?.date?.substring(0, 10)

        /** * * * *  *
         * initPics  *
         * * * * *  **/

        if (data?.imgPath != null) {
            photoListData!!.add(data?.imgPath.toString())
        }
        if (data?.imgPath2 != null) {
            photoListData!!.add(data?.imgPath2.toString())
        }
        if (data?.imgPath3 != null) {
            photoListData!!.add(data?.imgPath3.toString())
        }
        if (photoListData?.isEmpty()!!) {
            image_container.visibility = View.GONE
        } else {
            imageViewPageAdapter = ViewPageAdapter(photoListData!!, this, false)
            viewpager.adapter = imageViewPageAdapter
            viewpager.currentItem = 0

            tab_layout.setupWithViewPager(viewpager, true)
        }

    }


    override fun onClick(v: View?) {
        when (v) {
            btDeleteProduct -> {
                if (isMyProduct) initDialog()
                else if (data?.phoneNumber?.length == 10) {
                    tvPhoneNumberDetailedProduct.text = data?.phoneNumber
                    tvPhoneNumberDetailedProduct.isClickable = true
                    tvPhoneNumberDetailedProduct.setOnClickListener(this)
                    btDeleteProduct.visibility = View.GONE
                    btDeleteProduct.background = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        tvPhoneNumberDetailedProduct.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, (R.drawable.ic_call_24dp), 0)
                    }
                }
            }
            tvPhoneNumberDetailedProduct -> phoneIntent()
        }
    }

    private fun goToFullImageActivity(index: Int) {
        val intent = Intent(this@UrgentDetailedActivity, FullPhotoActivity::class.java)
        intent.putExtra(INDEX, index)
        intent.putExtra(ALL_PHOTO_URLS, photoListData)
        startActivity(intent)
    }


    private fun phoneIntent() {
        val number = tvPhoneNumberDetailedProduct.text.toString()
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null))
        startActivity(intent)
    }

    private fun initData() {
        photoListData = ArrayList()
        data = intent.getParcelableExtra(ACTION_URGENT)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @SuppressLint("HardwareIds")
    private fun getAndroidId(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }

    private fun initDialog() {
        val fm = supportFragmentManager
        val dialogFragment = FragDialog().newInstance(data?.title!!, data?.id!!)
        dialogFragment.show(fm, "dialog")
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
                onBackPressed()
        } else if (item?.itemId == R.id.delete) {

            initDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object CREATOR : Parcelable.Creator<UrgentDetailedActivity> {
        override fun createFromParcel(parcel: Parcel): UrgentDetailedActivity {
            return UrgentDetailedActivity(parcel)
        }

        override fun newArray(size: Int): Array<UrgentDetailedActivity?> {
            return arrayOfNulls(size)
        }
    }
}
