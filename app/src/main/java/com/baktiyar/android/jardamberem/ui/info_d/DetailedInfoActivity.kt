package com.baktiyar.android.jardamberem.ui.info_d

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Info
import com.baktiyar.android.jardamberem.utils.Const.Companion.INFO_DETAILED
import kotlinx.android.synthetic.main.activity_info_detailed.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailedInfoActivity : AppCompatActivity() {

    var data: Info? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_detailed)
        init()

    }
    fun init() {
        initToolbar()
        val  mimeType: String = "text/html"
        val  encoding: String = "UTF-8"
        data = intent.getParcelableExtra(INFO_DETAILED)
        title_d.text = data?.title
        body.loadDataWithBaseURL("", data?.description!!, mimeType, encoding, "")
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
