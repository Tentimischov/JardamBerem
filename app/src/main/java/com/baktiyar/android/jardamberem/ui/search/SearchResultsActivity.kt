package com.baktiyar.android.jardamberem.ui.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.ui.main.fragment.MainAnnouncementAdapter
import com.baktiyar.android.jardamberem.ui.product.detailed_product.DetailedProductActivity
import com.baktiyar.android.jardamberem.utils.Const
import com.baktiyar.android.jardamberem.utils.Settings
import kotlinx.android.synthetic.main.activity_action.*
import kotlinx.android.synthetic.main.toolbar.*

class SearchResultsActivity : AppCompatActivity(), SearchContract.View, MainAnnouncementAdapter.OnItemClickListener {

    private lateinit var presenter: SearchPresenter
    var adapter: MainAnnouncementAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action)
        title = getString(R.string.search_product)
        init()
    }

    fun init() {
        initToolbar()
        presenter = SearchPresenter(this)
        presenter.getSearch(intent.getStringExtra("SEARCH_QUERY"))
        initAdapter()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
       if (item?.itemId == android.R.id.home) {
           onBackPressed()
       }

        return super.onOptionsItemSelected(item)
    }
    fun initAdapter() {
        adapter = MainAnnouncementAdapter(ArrayList(), this)
        action_recycler.adapter = adapter
    }


    override fun onSuccess(data: ArrayList<Announcements>) {
        setData(data)
        pro_bar.visibility = View.GONE
        if (data.isEmpty())
            empty.visibility = View.VISIBLE
    }
    private fun setData(data: ArrayList<Announcements>) {
        pro_bar.visibility = View.GONE
        action_recycler.visibility = View.VISIBLE
        adapter?.addAnnouncementData(data)
    }

    override fun onError(message: String?) {
        pro_bar.visibility = View.GONE
    }

    override fun onAnnouncementItemClick(data: Announcements) {
        val intent = Intent(applicationContext, DetailedProductActivity::class.java)
        intent.putExtra(Const.GOODS, data)
        startActivity(intent)
    }

    override fun onCallClick(number: String) {
        phoneIntent(number)
    }

    private fun phoneIntent(number: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null))
        startActivity(intent)
    }

}

