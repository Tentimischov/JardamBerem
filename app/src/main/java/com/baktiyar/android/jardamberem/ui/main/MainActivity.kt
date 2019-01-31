package com.baktiyar.android.jardamberem.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.AllCategory
import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated
import com.baktiyar.android.jardamberem.model.Urgent
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.ConstantsJava
import com.baktiyar.android.jardamberem.ui.all_urgents.AllUrgentsActivity
import com.baktiyar.android.jardamberem.ui.announcements_list.AnnounByCategoryActivity
import com.baktiyar.android.jardamberem.ui.main.adapter.AnnouncementAdapter
import com.baktiyar.android.jardamberem.ui.main.adapter.CategoryAdapter
import com.baktiyar.android.jardamberem.ui.main.adapter.PaginationScrollListener
import com.baktiyar.android.jardamberem.ui.main.adapter.UrgentAdapter
import com.baktiyar.android.jardamberem.ui.product.detailed_product.DetailedProductActivity
import com.baktiyar.android.jardamberem.ui.product.post_product.NewProductActivity
import com.baktiyar.android.jardamberem.ui.search.SearchResultsActivity
import com.baktiyar.android.jardamberem.ui.urgent_detailed.UrgentDetailedActivity
import com.baktiyar.android.jardamberem.utils.Const
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTION_URGENT
import com.baktiyar.android.jardamberem.utils.Const.Companion.CATEGORY_ID
import com.baktiyar.android.jardamberem.utils.Const.Companion.CATEGORY_NAME
import com.baktiyar.android.jardamberem.utils.Const.Companion.GOODS
import com.baktiyar.android.jardamberem.utils.Const.Companion.URGENTS
import com.baktiyar.android.jardamberem.utils.Const.Companion.hideKeyboard
import com.baktiyar.android.jardamberem.utils.Const.Companion.setVisiblityMenuItem
import com.baktiyar.android.jardamberem.utils.Settings
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity(),
        MainContract.View,
        View.OnClickListener,
        CategoryAdapter.OnItemClickListener,
        UrgentAdapter.OnUrgClickListener,
        AnnouncementAdapter.OnItemClickListener {

    private val TOTAL_PAGES: Int = 100
    private var issLoading = false
    private var issLastPage = false
    private var limitPage = 100
    private val PAGE_START = 1
    private var currentPage = PAGE_START
    private var announcementAdapter: AnnouncementAdapter? = null
    private var caAdapter: CategoryAdapter? = null
    private var urgAdapter: UrgentAdapter? = null
    private var mPresenter: MainPresenter? = null
    private var categoryData: ArrayList<AllCategory>? = null
    private var allUrgent: ArrayList<Urgent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ConstantsJava.setLocale(baseContext, Locale(Settings.getLanguage(baseContext)))
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics());
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        init()
        initSpinner()

    }

    private fun init() {
        mPresenter = MainPresenter(this)
        initClick()
        initCategory()
        initUrgent()
        initAnnouncement()

        mPresenter?.getCategory(Settings.getCityId(this))

        mPresenter?.getUrgent(1000, 0)

    }


    private fun initSpinner() {
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.main_menu_spinner,
                android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        action_spinner.adapter = adapter
        action_spinner.background.setColorFilter(ContextCompat.getColor(this, R.color.red), PorterDuff.Mode.SRC_ATOP);
        action_spinner.gravity = Gravity.CENTER
        action_spinner.setPopupBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.border))
        action_spinner.setSelection(Settings.getSpinnerItemPosition(this))
        action_spinner.onItemSelectedListener = Const.Companion.SpinnerActivity(this, this@MainActivity)
    }

    private fun initAnnouncement() {
        announcementAdapter = AnnouncementAdapter(ArrayList(), this)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        announ_recyclerview.layoutManager = LinearLayoutManager(this)
        announ_recyclerview.isNestedScrollingEnabled = false
        announ_recyclerview.itemAnimator = DefaultItemAnimator()
        announ_recyclerview.adapter = announcementAdapter
        addScrollAdapter(staggeredGridLayoutManager)
        loadFirstPage()
    }


    //Menu Toolbar Items
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)

        val search = menu?.findItem(R.id.search)
        val searchVIew = menu?.findItem(R.id.search)?.actionView as SearchView

        val searchManager =
                getSystemService(Context.SEARCH_SERVICE) as (SearchManager)
        val searchView =
                menu.findItem(R.id.search).actionView as (SearchView)
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName))

        searchVIew.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search?.collapseActionView()
                hideKeyboard(this@MainActivity)
                val intent = Intent(this@MainActivity, SearchResultsActivity::class.java)
                intent.putExtra("SEARCH_QUERY", query)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        search?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                setVisiblityMenuItem(menu, search!!, false)
                addCardView.visibility = View.GONE
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                setVisiblityMenuItem(menu, search!!, true)
                addCardView.visibility = View.VISIBLE
                return true
            }
        })


        return true
    }

    private fun addScrollAdapter(staggeredGridLayoutManager: StaggeredGridLayoutManager) {
        announ_recyclerview.addOnScrollListener(object : PaginationScrollListener(staggeredGridLayoutManager) {
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
        announcementAdapter?.clearList()
        if (Settings.getSpinnerItemPosition(this) == 1) {
            mPresenter?.getAnnounFirst(limitPage, 0)
        } else {
            mPresenter?.getAnnounFirstIsNeededFalse(limitPage, 0)
        }
    }


    private fun loadNextPage() {
        if (Settings.getSpinnerItemPosition(this) == 1) {
            mPresenter?.getAnnounNext(limitPage, currentPage * limitPage)
        } else {
            mPresenter?.getAnnounNextIsNeededFalse(limitPage, currentPage * limitPage)
        }
    }


    override fun onClick(v: View?) {
        if (v?.id == R.id.addCardView) {
            val intent = Intent(this, NewProductActivity::class.java)
            startActivity(intent)
        } else if (v?.id == R.id.all) {
            val intent = Intent(this, AllUrgentsActivity::class.java)
            intent.putExtra(URGENTS, allUrgent)
            startActivity(intent)
        }
    }

    private fun initClick() {
        addCardView.setOnClickListener(this)
        all.setOnClickListener(this)
    }

    override fun onAnnounFirstSuccess(data: AnnouncementsPaginated) {
        pro_bar.visibility = View.GONE
        announcementAdapter?.addAll(fetchResults(data))
        if (data.next != null) {
            announcementAdapter?.addLoadingFooter()
        } else {
            issLastPage = true
        }
    }

    override fun onAnnounNextSuccess(data: AnnouncementsPaginated) {
        announcementAdapter?.removeLoadingFooter()
        issLoading = false
        announcementAdapter?.addAll(fetchResults(data))
        if (data.next != null) {
            announcementAdapter?.addLoadingFooter()
        } else {
            issLastPage = true
        }
    }

    private fun fetchResults(response: AnnouncementsPaginated): List<Announcements> {
        return response.results
    }

    private fun initCategory() {
        caAdapter = CategoryAdapter(ArrayList(), this)
        recview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recview.setHasFixedSize(true)
        recview.itemAnimator = DefaultItemAnimator()
        recview.isNestedScrollingEnabled = false
        recview.adapter = caAdapter
    }

    private fun initUrgent() {
        urgAdapter = UrgentAdapter(ArrayList(), this)
        urg_recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        urg_recyclerview.isNestedScrollingEnabled = false
        urg_recyclerview.adapter = urgAdapter
    }


    override fun onUrgentItemClick(main: Urgent) {
        val intent = Intent(this, UrgentDetailedActivity::class.java)
        intent.putExtra(ACTION_URGENT, main)
        startActivity(intent)
    }

    override fun onCategoryItemClick(data: AllCategory, position: Int) {
        Settings.setCategoryId(this, data.id)
        if (data.category_name != getString(R.string.all)) {
            val intent = Intent(this, AnnounByCategoryActivity::class.java)
            intent.putExtra(CATEGORY_ID, position)
            intent.putExtra(CATEGORY_NAME, data.category_name)
            startActivity(intent)
        }
    }

    override fun onCategorySuccess(data: ArrayList<AllCategory>) {
        caAdapter?.setCategoryData(data)
        categoryData = data

        val stringBuilder = StringBuilder()
        stringBuilder.append(getString(R.string.choose_category) + ",")
        for (i in 0 until data.size - 1)
            stringBuilder.append(data[i].category_name + ",")

        if (data.size > 0)
            stringBuilder.append(data[data.size - 1].category_name)

        Settings.setCategory(this, stringBuilder.toString())
    }

    override fun onUrgentSuccess(data: ArrayList<Urgent>) {
        allUrgent = data
        urgAdapter?.setUrgData(data)
    }

    override fun onError(message: String) {
        pro_bar.visibility = View.GONE
        issLastPage = true
    }

    override fun onAnnouncementItemClick(data: Announcements) {
        val intent = Intent(this, DetailedProductActivity::class.java)
        intent.putExtra(GOODS, data)
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





