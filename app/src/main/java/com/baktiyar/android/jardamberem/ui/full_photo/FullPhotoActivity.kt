package com.baktiyar.android.jardamberem.ui.full_photo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.baktiyar.android.jardamberem.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_full_photo.*
import kotlinx.android.synthetic.main.activity_main.*

class FullPhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_photo)

        val url = intent.getStringExtra("im")
        Picasso.get().load(url).into(im)

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
