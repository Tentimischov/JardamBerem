package com.baktiyar.android.jardamberem.ui.announcements_list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.ui.main.fragment.ViewPagerAdapter
import com.baktiyar.android.jardamberem.utils.Const.Companion.CATEGORY_NAME
import kotlinx.android.synthetic.main.activity_announ_list.*
import kotlinx.android.synthetic.main.toolbar.*

class AnnouncementByCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announ_list)

        init()

        val giveHelp = AnnouncementByCategoryFragment.newInstance(false)
        val needHelp = AnnouncementByCategoryFragment.newInstance(true)
        all_announcement_list_viewpager.adapter = ViewPagerAdapter(supportFragmentManager, arrayOf(giveHelp, needHelp))
        all_announcement_tab_layout.setupWithViewPager(all_announcement_list_viewpager)
    }

    fun init() {
        title = intent.getStringExtra(CATEGORY_NAME)
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
