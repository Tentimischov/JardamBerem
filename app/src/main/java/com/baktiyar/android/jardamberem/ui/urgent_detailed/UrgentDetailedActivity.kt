package com.baktiyar.android.jardamberem.ui.urgent_detailed

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Urgent
import com.baktiyar.android.jardamberem.ui.delete_fragment_dialog.FragDialog
import com.baktiyar.android.jardamberem.ui.full_photo.FullPhotoActivity
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTION_URGENT
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_dp.*
import kotlinx.android.synthetic.main.toolbar.*

class UrgentDetailedActivity : AppCompatActivity(), View.OnClickListener {

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        return super.onTouchEvent(event)
    }
    var data: Urgent? = null
    var isMyProduct: Boolean = false
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

        /* * * * *  *
         * initPics *
         * * * * *  */

        if (data?.imgPath != null) {
            Picasso.get().load(data?.imgPath).fit().centerCrop().into(f_im)
            f_im.setOnClickListener(this)
        }
        if (data?.imgPath2 != null) {
            Picasso.get().load(data?.imgPath2).fit().centerCrop().into(s_im)

            s_im.setOnClickListener(this)
        }
        if (data?.imgPath3 != null) {
            Picasso.get().load(data?.imgPath3).fit().centerCrop().into(t_im)

            t_im.setOnClickListener(this)
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

            f_im -> {
                goToFullImageActivity(data?.imgPath.toString())
            }
            s_im -> {
                goToFullImageActivity(data?.imgPath2.toString())
            }

            t_im -> {
                goToFullImageActivity(data?.imgPath3.toString())
            }

        }
    }

    private fun goToFullImageActivity(url: String) {
        val intent = Intent(this@UrgentDetailedActivity, FullPhotoActivity::class.java)
        intent.putExtra("im", url)
        startActivity(intent)
    }


    private fun phoneIntent() {
        val number = tvPhoneNumberDetailedProduct.text.toString()
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null))
        startActivity(intent)
    }

    private fun initData() {
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition()
            } else {
                onBackPressed()
            }
        } else if (item?.itemId == R.id.delete) {

            initDialog()
        }
        // onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
