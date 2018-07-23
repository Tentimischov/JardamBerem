package com.baktiyar.android.jardamberem.ui.city

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.City
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.main.MainActivity
import com.baktiyar.android.jardamberem.utils.Settings
import kotlinx.android.synthetic.main.activity_choose_city.*
import org.jetbrains.anko.toast

class ChooseCityActivity : BaseActivity(), CityContact.View {
    lateinit var city: Array<String>
    private var cityModel: ArrayList<City>? = null
    private var data: Array<String>? = null
    private var mProgressBar: ProgressDialog? = null
    private var cityId: Array<String>? = null
    private var mdata: ArrayList<String>? = null
    private var isError: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_city)
        title = resources.getString(R.string.city)

        init()

        done.setOnClickListener {
            if (isError!! && cityId != null) {
                toast(getString(R.string.retry))
            } else {
                Settings.setCityId(applicationContext, cityModel!![picker.value].id)
                toast(getString(R.string.city_choosen))
            }
        }
    }


    fun init() {
        initPresenter()
    }

    fun initPresenter() {
        val presenter = CityPresenter(this)
        presenter.getCity()
    }

    private fun initCityPicker() {
        picker.minValue = 0
        picker.maxValue = data?.size?.minus(1)!!
        picker.value = Settings.getCityId(applicationContext) - 1
        picker.displayedValues = data
        picker.descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
    }


    override fun onSuccess(data: ArrayList<City>) {
        mdata = ArrayList()
        cityModel = data
        isError = false
        for (i in 0 until data.size) {
            mdata!!.add(data[i].city_name)
        }
        this.data = mdata?.toTypedArray()
        initCityPicker()


        //save city array as string
        val stringBuilder = StringBuilder()
        for (i in 0 until data.size - 1)
            stringBuilder.append(data[i].city_name + ",")
        if (data.size > 0)
            stringBuilder.append(data[data.size - 1].city_name)
        Settings.setCityNameArray(this, stringBuilder.toString())

        //save cityId array as string
        val stringCityId = StringBuilder()
        for (i in 0 until data.size - 1)
            stringCityId.append("${data[i].id},")
        if (data.size > 0)
            stringCityId.append(data[data.size - 1].id)
        Settings.setCityIdArray(this, stringCityId.toString())

    }

    override fun onError(message: String) {
        isError = true
        this.data = Settings.getCityNameArray(this).split(",").toTypedArray()
        if (Settings.getCityIdArray(this) != null)
            cityId = Settings.getCityIdArray(this).split(",").toTypedArray()
        initCityPicker()

    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()

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
        picker.visibility = View.VISIBLE
    }


}
