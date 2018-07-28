package com.baktiyar.android.jardamberem.ui.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.ui.main.adapter.AnnounAdapterNoPaginated
import com.baktiyar.android.jardamberem.ui.product.detailed_product.DetailedProductActivity
import com.baktiyar.android.jardamberem.utils.Const
import com.baktiyar.android.jardamberem.utils.Settings
import kotlinx.android.synthetic.main.activity_action.*
import kotlinx.android.synthetic.main.toolbar.*

class SearchResultsActivity : AppCompatActivity(), SearchContract.View, AnnounAdapterNoPaginated.OnAnnounClickNoPage {

    var adapter: AnnounAdapterNoPaginated? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action)
        title = getString(R.string.search_product)
        init()
    }

    fun init() {
        initToolbar()
        val presenter = SearchPresenter(this)
        presenter.getSearch(Settings.getCityId(this), intent.getStringExtra("SEARCH_QUERY"))
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
        adapter = AnnounAdapterNoPaginated(ArrayList(), this)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        action_recycler.layoutManager = staggeredGridLayoutManager
        action_recycler.isNestedScrollingEnabled = false
        action_recycler.itemAnimator = DefaultItemAnimator()
        action_recycler.adapter = adapter
    }

    override fun onAnClick(data: Announcements) {
        val intent = Intent(this, DetailedProductActivity::class.java)
        intent.putExtra(Const.GOODS, data)
        startActivity(intent)
    }


    override fun onSuccess(data: ArrayList<Announcements>) {
        adapter?.setAnData(data)
        pro_bar.visibility = View.GONE
        if (data.isEmpty())
            empty.visibility = View.VISIBLE
    }

    override fun onError(message: String?) {
        pro_bar.visibility = View.GONE
    }

}

