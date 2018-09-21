package com.baktiyar.android.jardamberem.ui.product.detailed_product

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.Secure
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.baktiyar.android.jardamberem.ApplicationClass
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.ui.full_photo.FullPhotoActivity
import com.baktiyar.android.jardamberem.ui.main.MainActivity
import com.baktiyar.android.jardamberem.utils.Const.Companion.ALL_PHOTO_URLS
import com.baktiyar.android.jardamberem.utils.Const.Companion.GOODS
import com.baktiyar.android.jardamberem.utils.Const.Companion.INDEX
import com.baktiyar.android.jardamberem.utils.Settings
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_dp.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.toast


class DetailedProductActivity : AppCompatActivity(), View.OnClickListener, DetailedProductContract.View {


    private var mDetailedProductPresenter: DetailedProductPresenter? = null
    private var mProgressBar: ProgressDialog? = null
    private var mProduct: Announcements? = null
    private var isMyProduct: Boolean? = null
    private var photoListData: ArrayList<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dp)
        mProduct = intent.getParcelableExtra(GOODS)

        title = getString(R.string.info)
        init()
    }

    private fun init() {
        initToolbar()
        initUi()
        initPresenter()
        initPics()
    }


    private fun initPics() {
        photoListData = ArrayList()

        if (mProduct?.imgPath != null) {
            Picasso.get().load(mProduct?.imgPath).fit().centerCrop().into(f_im)
            f_im.setOnClickListener(this)
            photoListData!!.add(mProduct?.imgPath.toString())
        }
        if (mProduct?.imgPath2 != null) {
            Picasso.get().load(mProduct?.imgPath2).fit().centerCrop().into(s_im)
            s_im.setOnClickListener(this)
            photoListData!!.add(mProduct?.imgPath2.toString())
        }
        if (mProduct?.imgPath3 != null) {
            t_im.setOnClickListener(this)
            Picasso.get().load(mProduct?.imgPath3).fit().centerCrop().into(t_im)
            photoListData!!.add(mProduct?.imgPath3.toString())
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        supportActionBar!!.setDisplayShowTitleEnabled(true)
    }

    fun getCategoryText(): String {
        val categories = Settings.getCategory(this).split(",")
        category.text = categories[mProduct?.category!!]
        for (i in 0 until categories.size) {
            if (categories[i].toInt() == mProduct?.category)
                return categories[i]
        }
        return getString(R.string.all)
    }

    private fun initUi() {

        val cities = Settings.getCityNameArray(this).split(",")
        city.text = cities[mProduct?.city!! - 1]
        val categories = Settings.getCategory(this).split(",")
        if (categories.size > mProduct?.category!!)
            category.text = categories[mProduct?.category!!]
        if (mProduct!!.userImeiCode == getAndroidId()) {
            isMyProduct = true
            button_content_text.text = getString(R.string.delete_product)
            //btDeleteProduct.visibility = View.VISIBLE
            tvPhoneNumberDetailedProduct.visibility = View.VISIBLE
            tvPhoneNumberDetailedProduct.text = mProduct!!.number
            //     btShowNumberProduct.visibility = View.GONE


        } else {
            isMyProduct = false
            //  btDeleteProduct.visibility = View.GONE
            button_content_text.text = getString(R.string.show_number)
            // btShowNumberProduct.visibility = View.VISIBLE
            //  btShowNumberProduct.setOnClickListener(this)
            tvPhoneNumberDetailedProduct.text = mProduct?.number?.substring(0, 3) + "XX XX XX"
        }

        btDeleteProduct.setOnClickListener(this)

        tvTitleDetailedProduct.text = mProduct!!.title
        tvDescriptionDetailedProduct.text = mProduct!!.description
        tvDateDetailedProduct.text = mProduct!!.date?.substring(0, 10)
    }

    override fun onClick(v: View?) {
        when (v) {
            btDeleteProduct -> {
                if (isMyProduct as Boolean) showDeleteProductDialog()
                else {
                    tvPhoneNumberDetailedProduct.text = mProduct!!.number
                    // tvPhoneNumberDetailedProduct.visibility = View.VISIBLE
                    // btShowNumberProduct.visibility = View.GONE
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
                    goToFullImageActivity(0)
            }
            s_im -> {
                    goToFullImageActivity(1)
            }
            t_im -> {
                    goToFullImageActivity(2)
            }
        }
    }


    private fun goToFullImageActivity(index: Int) {
        val intent = Intent(this@DetailedProductActivity, FullPhotoActivity::class.java)
        intent.putExtra(INDEX, index)
        intent.putExtra(ALL_PHOTO_URLS, photoListData)
        startActivity(intent)
    }


    private fun initPresenter() {
        val app = this.applicationContext as ApplicationClass
        mDetailedProductPresenter = DetailedProductPresenter(this)
    }


    @SuppressLint("HardwareIds")
    private fun getAndroidId(): String {
        return Secure.getString(contentResolver, Secure.ANDROID_ID)
    }

    private fun showDeleteProductDialog() {
        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    mDetailedProductPresenter!!.deleteAnnoun(mProduct?.id!!)
                }

                DialogInterface.BUTTON_NEGATIVE -> {

                }
            }
        }
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.check_to_delete, mProduct!!.title!!))
                .setPositiveButton(getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getString(R.string.no), dialogClickListener).show()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun phoneIntent() {
        val number = tvPhoneNumberDetailedProduct.text.toString()
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null))
        startActivity(intent)
    }

    override fun showProgress() {
        if (mProgressBar == null) {
            mProgressBar = ProgressDialog(this)
            mProgressBar!!.setTitle(R.string.loading)
            mProgressBar!!.show()
        }
    }

    override fun hideProgress() {
        if (mProgressBar != null && mProgressBar!!.isShowing) mProgressBar!!.dismiss()
        mProgressBar = null
    }

    override fun onFail(message: String) {
        toast(message)
    }

    override fun onSuccess() {
        toast(R.string.success_delete)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


}

