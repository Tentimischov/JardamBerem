package com.baktiyar.android.jardamberem.ui.product.post_product


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.Settings.Secure
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import com.baktiyar.android.jardamberem.ApplicationClass
import com.baktiyar.android.jardamberem.utils.MyContextWrapper
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.PostProduct
import com.baktiyar.android.jardamberem.ui.main.MainActivity
import com.baktiyar.android.jardamberem.ui.product.post_product.adapter.ImageAdapter
import com.baktiyar.android.jardamberem.utils.Settings
import kotlinx.android.synthetic.main.activity_add.*
import org.jetbrains.anko.toast
import java.util.*

class NewProductActivity : PhotoPickActivity(), NewProductContract.View, View.OnClickListener, ImageAdapter.OnItemClickListener {
    private var progressBar: ProgressDialog? = null

    private var mCategoryList: Array<String>? = null
    private var mCityList: Array<String>? = null
    private var mCityId: Int? = null
    private var mPresenter: NewProductPresenter? = null
    private var mImagePaths: ArrayList<String>? = null
    private var cityData: List<String>? = null
    private var categoryData: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Settings.setLocale(applicationContext, Locale(Settings.getLanguage(applicationContext)))
        setContentView(R.layout.activity_add)
        title = getString(R.string.add_product)
        mCityId = Settings.getCityId(this)
        init()
    }

    private fun init() {
        getDataFromSettings()
        initArray()
        initSpinner()
        initPresenter()
        //initViewPager()
        f_im.setOnClickListener(this)
        s_im.setOnClickListener(this)
        t_im.setOnClickListener(this)
        btAddNewProduct.setOnClickListener(this)
    }


    private fun getDataFromSettings() {
        cityData = Settings.getCityNameArray(this).split(",")
        categoryData = Settings.getCategory(this).split(",")
    }

    private fun sendData() {
        var ok = true
        val isNeeded = (one.isChecked)
        val title: String = etTitleNewProduct.text.toString()
        val description: String = etDescriptionNewProduct.text.toString()
        val phoneNumber: String = etPhoneNumberNewProduct.text.toString()
        val idCategory: Int = spinnerCategory.selectedItemPosition
        val idCity: Int = spinnerCity.selectedItemPosition.inc()
        val imeiUserCode: String = getAndroidId()


        if (idCategory == 0) {
            styleInp.error = getString(R.string.choose_category)
            ok = false
        }
        if (TextUtils.isEmpty(title)) {
            etTitleNewProduct.error = getString(R.string.error_empty)
            ok = false
        }
        if (TextUtils.isEmpty(description)) {
            etDescriptionNewProduct.error = getString(R.string.error_empty)
            ok = false
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            etPhoneNumberNewProduct.error = getString(R.string.error_empty)
            ok = false
        }


        if (ok) {
            val mProduct = PostProduct(idCity, idCategory, isNeeded, title, description, phoneNumber, imeiUserCode, null, null, null)
            mPresenter!!.sendProduct(idCategory, idCity, mProduct, mImagePaths)
        }

    }

    private fun initArray() {
        mImagePaths = ArrayList()

        mCategoryList = categoryData?.toTypedArray()
        mCityList = cityData?.toTypedArray()
    }

    /*
    private fun initViewPager(){
        viewPagerOfImage.adapter = ImageAdapter(this, arrayListOf(""), this)
        indicator.setViewPager(viewPagerOfImage)
    }*/
    private fun initPresenter() {
        val app = this.applicationContext as ApplicationClass
        mPresenter = NewProductPresenter(this, app.service!!, this)
    }

    private fun initSpinner() {
        var arrayAdapter = ArrayAdapter(this, R.layout.item_category_spinner, mCategoryList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = arrayAdapter
        spinnerCategory.setSelection(0)

        arrayAdapter = ArrayAdapter(this, R.layout.item_category_spinner, mCityList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCity.adapter = arrayAdapter
        spinnerCity.setSelection(mCityId?.minus(1)!!)
    }


    override fun setImagePaths(imgPaths: String?, index: Int?) {
        mImagePaths?.add(imgPaths!!)

        when (index) {
            0 -> f_im.setImageBitmap(BitmapFactory.decodeFile(imgPaths))
            1 -> s_im.setImageBitmap(BitmapFactory.decodeFile(imgPaths))
            2 -> t_im.setImageBitmap(BitmapFactory.decodeFile(imgPaths))
        }
        //viewPagerOfImage.adapter = ImageAdapter(this, mImagePaths, this)
    }


    @SuppressLint("HardwareIds")
    private fun getAndroidId(): String {
        return Secure.getString(contentResolver, Secure.ANDROID_ID)
    }


    override fun showProgress() {
        if (progressBar == null) {
            progressBar = ProgressDialog(this)
            progressBar!!.setTitle(R.string.loading)
            progressBar!!.show()
        }
    }


    override fun hideProgress() {
        if (progressBar != null && progressBar!!.isShowing) progressBar!!.dismiss()
        progressBar = null
    }

    override fun onClick(position: Int) {
        mImagePaths!!.removeAt(position)
    }

    override fun onClick(v: View?) {
        when (v) {
            f_im -> showPickImageDialog(0)
            s_im -> showPickImageDialog(1)
            t_im -> showPickImageDialog(2)
            btAddNewProduct -> sendData()
        }
    }

    override fun onFail(message: String) {
        toast(message)
    }

    override fun onSuccess() {
        toast(R.string.success_add)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun attachBaseContext(newBase: Context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            super.attachBaseContext(MyContextWrapper.wrap(newBase, Settings.getLanguage(newBase.applicationContext)))
        } else {
            super.attachBaseContext(newBase)
        }
    }


}
