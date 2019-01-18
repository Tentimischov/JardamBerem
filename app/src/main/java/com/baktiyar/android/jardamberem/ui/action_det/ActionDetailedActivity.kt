package com.baktiyar.android.jardamberem.ui.action_det

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.ActionData
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTION_DETAILED
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_action_d.*
import kotlinx.android.synthetic.main.toolbar.*

class ActionDetailedActivity : AppCompatActivity() {
    var actionData: ActionData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_d)
        title = getString(R.string.action)

        init()
    }

    fun init() {
        initToolbar()
        getDataFromIntent()
        setData()
    }

    private fun setData() {
        if (intent.extras != null) {
            when {
                actionData?.imgPath != null -> Picasso.get().load(actionData?.imgPath).into(image)
                actionData?.imgPath2 != null -> Picasso.get().load(actionData?.imgPath2).into(image)
                else -> Picasso.get().load(actionData?.imgPath3).into(image)
            }
            date.text = actionData?.date?.substring(0, 10)
            title_d.text = actionData?.title
            body.text = actionData?.description

        }
    }

    private fun getDataFromIntent() {
        actionData = intent.getParcelableExtra(ACTION_DETAILED)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}