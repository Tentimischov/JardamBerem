package com.baktiyar.android.jardamberem.ui.city

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.City
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.main.MainActivity
import com.baktiyar.android.jardamberem.utils.Const
import com.baktiyar.android.jardamberem.utils.Const.Companion.HIDE_DRAWER
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
    private var hideNav: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_city)
        hideNav = intent.getBooleanExtra(HIDE_DRAWER, true)
        setDrawerState(hideNav)
        title = resources.getString(R.string.city)

        init()

        done.setOnClickListener {
            if (isError!! && cityId != null) {
                toast(getString(R.string.retry))
            } else {
                Settings.setCityId(cityModel!![picker.value].id)
                toast(getString(R.string.city_choosen))
                if (!hideNav) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }


    fun setDrawerState(hide: Boolean) {
        if (hide) {
            super.drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            super.toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED)
            super.toggle.isDrawerIndicatorEnabled = true
            super.toggle.syncState()

        } else {
            super.drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            super.toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            super.toggle.isDrawerIndicatorEnabled = false
            super.toggle.syncState()
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
        picker.value = Settings.getCityId() - 1
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
        Settings.setCityNameArray(stringBuilder.toString())

        //save cityId array as string
        val stringCityId = StringBuilder()
        for (i in 0 until data.size - 1)
            stringCityId.append("${data[i].id},")
        if (data.size > 0)
            stringCityId.append(data[data.size - 1].id)
        Settings.setCityIdArray(stringCityId.toString())

    }

    override fun onError(message: String) {
        isError = true
        this.data = Settings.getCityNameArray().split(",").toTypedArray()
        if (Settings.getCityIdArray() != null)
            cityId = Settings.getCityIdArray().split(",").toTypedArray()
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
