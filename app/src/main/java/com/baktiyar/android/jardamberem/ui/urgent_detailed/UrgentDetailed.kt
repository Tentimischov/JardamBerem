package com.baktiyar.android.jardamberem.ui.urgent_detailed

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Urgent
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTION_URGENT
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_action_d.*
import kotlinx.android.synthetic.main.toolbar.*

class UrgentDetailed : AppCompatActivity() {

    var data: Urgent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_d)
        title = getString(R.string.urgent_help)
        init()
    }
    fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        data = intent.getParcelableExtra(ACTION_URGENT)
        Glide.with(this).load(data?.imgPath).into(image)
        title_d.text = data?.title
        body.text = data?.description

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition()
            } else {
                onBackPressed()
            }
        }
           // onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
