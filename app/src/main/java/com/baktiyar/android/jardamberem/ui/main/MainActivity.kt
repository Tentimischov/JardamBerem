package com.baktiyar.android.jardamberem.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.AllCategory
import com.baktiyar.android.jardamberem.model.Urgent
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.ConstantsJava
import com.baktiyar.android.jardamberem.ui.all_urgents.AllUrgentsActivity
import com.baktiyar.android.jardamberem.ui.announcements_list.AnnounByCategoryActivity
import com.baktiyar.android.jardamberem.ui.main.adapter.CategoryAdapter
import com.baktiyar.android.jardamberem.ui.main.adapter.UrgentAdapter
import com.baktiyar.android.jardamberem.ui.main.fragment.MainAnnouncementFragment
import com.baktiyar.android.jardamberem.ui.product.post_product.NewProductActivity
import com.baktiyar.android.jardamberem.ui.search.SearchResultsActivity
import com.baktiyar.android.jardamberem.ui.urgent_detailed.UrgentDetailedActivity
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTION_URGENT
import com.baktiyar.android.jardamberem.utils.Const.Companion.CATEGORY_ID
import com.baktiyar.android.jardamberem.utils.Const.Companion.CATEGORY_NAME
import com.baktiyar.android.jardamberem.utils.Const.Companion.URGENTS
import com.baktiyar.android.jardamberem.utils.Const.Companion.hideKeyboard
import com.baktiyar.android.jardamberem.utils.Const.Companion.setVisiblityMenuItem
import com.baktiyar.android.jardamberem.utils.Settings
import com.baktiyar.android.jardamberem.utils.toToast
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity(),
        MainContract.View,
        View.OnClickListener,
        CategoryAdapter.OnItemClickListener,
        UrgentAdapter.OnUrgClickListener {

    private var caAdapter: CategoryAdapter? = null
    private var urgAdapter: UrgentAdapter? = null
    private var mPresenter: MainPresenter? = null
    private var categoryData: ArrayList<AllCategory>? = null
    private var allUrgent: ArrayList<Urgent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ConstantsJava.setLocale(baseContext, Locale(Settings.getLanguage()))
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics());
        setContentView(R.layout.activity_main)
        title = getString(R.string.app_name)
        init()

    }

    private fun init() {
        mPresenter = MainPresenter(this)
        initClick()
        initCategory()
        initUrgent()
        initAnnouncementFragment()

        mPresenter?.getCategory(Settings.getCityId())
        mPresenter?.getUrgent(1000, 0)

    }



    private fun initAnnouncementFragment() {
        tab_layout.addTab(tab_layout.newTab().setText(R.string.give_help))
        tab_layout.addTab(tab_layout.newTab().setText(R.string.need_help))
        val ft = supportFragmentManager.beginTransaction()

        ft.replace(R.id.frame_layout, MainAnnouncementFragment.newInstance(false))
        ft.commit()

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    switchFragment(MainAnnouncementFragment.newInstance(false))
                } else {
                    switchFragment(MainAnnouncementFragment.newInstance(true))
                }
            }

        })

    }

    fun switchFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, fragment)
        ft.commit()

    }

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
                setVisiblityMenuItem(menu, search, false)
                addCardView.visibility = View.GONE
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                setVisiblityMenuItem(menu, search, true)
                addCardView.visibility = View.VISIBLE
                return true
            }
        })

        return true
    }

    private fun initClick() {
        addCardView.setOnClickListener(this)
        all.setOnClickListener(this)
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
        Settings.setCategoryId(data.id)
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

        Settings.setCategory(stringBuilder.toString())
    }

    override fun onUrgentSuccess(data: ArrayList<Urgent>) {
        allUrgent = data
        urgAdapter?.setUrgData(data)
    }

    override fun onError(message: String) {
        message.toToast()
    }


}





