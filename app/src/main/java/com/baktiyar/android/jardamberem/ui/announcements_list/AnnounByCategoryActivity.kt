package com.baktiyar.android.jardamberem.ui.announcements_list

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.MenuItem
import android.view.View
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.ui.product.detailed_product.DetailedProductActivity
import com.baktiyar.android.jardamberem.utils.Const
import com.baktiyar.android.jardamberem.utils.Const.Companion.CATEGORY_NAME
import com.baktiyar.android.jardamberem.utils.Settings
import kotlinx.android.synthetic.main.activity_announ_list.*
import kotlinx.android.synthetic.main.toolbar.*

class AnnounByCategoryActivity : AppCompatActivity(), AnnounCategoryContract.View, ByCategoryAdapter.OnItemClickListener {


    private val TOTAL_PAGES: Int = 10
    private var presenter: AnnounCategoryPresenter? = null
    private var issLoading = false
    private var issLastPage = false
    private var limitPage = 10
    private val PAGE_START = 1
    private var currentPage = PAGE_START
    private var adapter: ByCategoryAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announ_list)

        init()
        if (Settings.getSpinnerItemPosition(this) == 1)
            two.isChecked = true

        one.setOnClickListener {
            Settings.setSpinnerItemPosition(this, 0)
            recreate()
        }
        two.setOnClickListener {
            Settings.setSpinnerItemPosition(this, 1)
            recreate()
        }

    }

    fun init() {
        title = intent.getStringExtra(CATEGORY_NAME)


        presenter = AnnounCategoryPresenter(this, applicationContext)
        adapter = ByCategoryAdapter(ArrayList(), this)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recview.layoutManager = staggeredGridLayoutManager
        recview.isNestedScrollingEnabled = false
        recview.itemAnimator = DefaultItemAnimator()
        recview.adapter = adapter
      //  addScrollAdapter(staggeredGridLayoutManager)

        //loadFirstPage()
        presenter?.getDataFirst(100, 0)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(data: Announcements) {
        val intent = Intent(this, DetailedProductActivity::class.java)
        intent.putExtra(Const.GOODS, data)
        startActivity(intent)
    }

    override fun onSuccessFirst(data: ArrayList<Announcements>) {
        adapter?.setDataAll(data)
        pro_bar.visibility = View.GONE
    }



    /*
    private fun addScrollAdapter(staggeredGridLayoutManager: StaggeredGridLayoutManager) {
        recview.addOnScrollListener(object : PaginationScrollListener(staggeredGridLayoutManager) {
            override fun loadMoreItems() {
                issLoading = true
                currentPage++
                loadNextPage()
            }

            override val totalPageCount: Int
                get() = TOTAL_PAGES
            override var isLastPage: Boolean
                get() = issLastPage
                set(value) {}

            override var isLoading: Boolean
                get() = issLoading
                set(value) {}

        })
    }




    private fun loadFirstPage() {
        if (Settings.getSpinnerItemPosition(this) == 1) {
            presenter?.getDataFirst(limitPage, 0)
        } else {
            presenter?.getDataFirst(limitPage, 0)
        }
    }
    private fun loadNextPage() {
        if (Settings.getSpinnerItemPosition(this) == 1) {
            presenter?.getDataNext(limitPage, currentPage * limitPage)
        } else {
            presenter?.getDataNext(limitPage, currentPage * limitPage)
        }
    }

    override fun onAnnounClick(data: Announcements, position: Int) {
        val intent = Intent(this, DetailedProductActivity::class.java)
        intent.putExtra(Const.GOODS, data)
        startActivity(intent)
    }


    override fun onSuccessFirst(data: AnnouncementsPaginated) {
        val model: List<Announcements> = fetchResults(data)
        pro_bar.visibility = View.GONE
        val id = Settings.getCategoryId(this)
        val filteredList = model.filter { it.category == id }
        adapter?.addAll(filteredList)

        if (data.next != null) {
            adapter?.addLoadingFooter()
        } else
            issLastPage = true
    }

    override fun onSuccessNext(data: AnnouncementsPaginated) {
        adapter?.removeLoadingFooter()
        issLoading = false
        pro_bar.visibility = View.GONE

        val model: List<Announcements> = fetchResults(data)
        val id = Settings.getCategoryId(this)
        val filteredList = model.filter { it.category == id }
        adapter?.addAll(filteredList)
        if (data.next == null) {
            issLastPage = true
        } else {
            adapter?.addLoadingFooter()
        }
    }

    private fun fetchResults(response: AnnouncementsPaginated): List<Announcements> {    //3
        return response.results
    }*/

    override fun onError(message: String) {

    }
}
