package com.baktiyar.android.jardamberem.ui.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.StaggeredGridLayoutManager
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.ui.main.adapter.AnnounAdapterNoPaginated
import com.baktiyar.android.jardamberem.ui.product.detailed_product.DetailedProductActivity
import com.baktiyar.android.jardamberem.utils.Const
import kotlinx.android.synthetic.main.activity_action.*

class SearchResultsActivity : AppCompatActivity(), SearchContract.View, AnnounAdapterNoPaginated.OnAnnounClickNoPage {

    var adapter: AnnounAdapterNoPaginated? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action)

        val query = intent.getStringExtra("SEARCH_QUERY")

        init()
    }


    fun init() {
        val presenter = SearchPresenter(this)
        presenter.getSearch(1, 1, 1, 1, 2, intent.getStringExtra("SEARCH_QUERY"))
        initAdapter()
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
    }

    override fun onError(message: String?) {
    }



}

