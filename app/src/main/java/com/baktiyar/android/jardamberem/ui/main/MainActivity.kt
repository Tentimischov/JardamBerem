package com.baktiyar.android.jardamberem.ui.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ArrayAdapter
import com.baktiyar.android.jardamberem.MyContextWrapper
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.AllCategory
import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated
import com.baktiyar.android.jardamberem.model.Urgent
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.ConstantsJava
import com.baktiyar.android.jardamberem.ui.SearchResultsActivity
import com.baktiyar.android.jardamberem.ui.all_urgents.AllUrgentsActivity
import com.baktiyar.android.jardamberem.ui.announcements_list.AnnounByCategoryActivity
import com.baktiyar.android.jardamberem.ui.main.adapter.*
import com.baktiyar.android.jardamberem.ui.product.detailed_product.DetailedProductActivity
import com.baktiyar.android.jardamberem.ui.product.post_product.NewProductActivity
import com.baktiyar.android.jardamberem.ui.urgent_detailed.UrgentDetailed
import com.baktiyar.android.jardamberem.utils.Const
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTION_URGENT
import com.baktiyar.android.jardamberem.utils.Const.Companion.CATEGORY_ID
import com.baktiyar.android.jardamberem.utils.Const.Companion.CATEGORY_NAME
import com.baktiyar.android.jardamberem.utils.Const.Companion.GOODS
import com.baktiyar.android.jardamberem.utils.Const.Companion.URGENTS
import com.baktiyar.android.jardamberem.utils.Const.Companion.hideKeyboard
import com.baktiyar.android.jardamberem.utils.Const.Companion.setVisiblityMenuItem
import com.baktiyar.android.jardamberem.utils.Settings
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity(), MainContract.View,
        View.OnClickListener, CategoryAdapter.OnItemClickListener,
        UrgentAdapter.OnUrgClickListener,
        SearchView.OnQueryTextListener, AnnounAdapterNoPaginated.OnAnnounClickNoPage {
    override fun onAnnounIsNeeded(data: ArrayList<Announcements>) {
        announAdapterS?.setAnData(data)
    }

    override fun onAnnounFirstSuccesss(data: ArrayList<Announcements>) {
        announAdapterS?.setAnData(data)
    }


    private val TOTAL_PAGES: Int = 10
    private var issLoading = false
    private var issLastPage = false
    private var limitPage = 10
    private val PAGE_START = 1
    private var currentPage = PAGE_START
    //  private var announAdapter: AnnounAdapter? = null
    private var caAdapter: CategoryAdapter? = null
    private var urgAdapter: UrgentAdapter? = null
    private var visibleCard: Boolean = true
    private var mPresenter: MainPresenter? = null
    private var categoryData: ArrayList<AllCategory>? = null
    private var isNeeded: Int? = 0
    private var allUrgent: ArrayList<Urgent>? = null
    private var categoryFilterId: Int? = null
    private var announAdapterS: AnnounAdapterNoPaginated? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ConstantsJava.setLocale(baseContext, Locale(Settings.getLanguage(baseContext)))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        init()
        initSpin()
    }

    fun initSpin() {

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


    fun init() {
        mPresenter = MainPresenter(this)
        initClick()
        initCardBut()
        initCa()
        initUrg()
        //initSearch()
        initAnn()

        mPresenter?.getCategory(Settings.getCityId(this))

        mPresenter?.getUrgent(100, 0)
        if (Settings.getSpinnerItemPosition(this) == 0)
            mPresenter?.getAnnounFirstIsNeededFalses(10000, 0)
        else
            mPresenter?.getAnnounFirst(100, 0)
        appBar?.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            //if(Math.abs(verticalOffset) == appBarLayout.totalScrollRange)
            //showKeyBoard()
            //else
            //hideKeyBoard();
        }


    }

    fun getDataForAdapters() {
        categoryData = ArrayList()

        categoryData?.add(AllCategory(1, getString(R.string.all), "http://s1.picswalls.com/wallpapers/2014/06/08/world-cup-2014-background_012630250_41.jpg", 1))
        categoryData?.add(AllCategory(2, "Еда", "http://s1.picswalls.com/wallpapers/2014/06/08/world-cup-2014-background_012630250_41.jpg", 1))
        categoryData?.add(AllCategory(3, "Работа", "http://s1.picswalls.com/wallpapers/2014/06/08/world-cup-2014-background_012630250_41.jpg", 1))
        categoryData?.add(AllCategory(4, "Одежда", "http://s1.picswalls.com/wallpapers/2014/06/08/world-cup-2014-background_012630250_41.jpg", 1))

        caAdapter?.setCategoryData(categoryData!!)

        allUrgent = ArrayList()

        allUrgent?.add(Urgent(1, "Mountains", "description", "18", "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_900,c_fill,g_auto,h_506,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F170407220907-01-iconic-mountains-k2-restricted.jpg"))
        allUrgent?.add(Urgent(2, "Mountains", "description", "18", "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_900,c_fill,g_auto,h_506,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F170407220907-01-iconic-mountains-k2-restricted.jpg"))
        allUrgent?.add(Urgent(3, "Mountains", "description", "18", "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_900,c_fill,g_auto,h_506,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F170407220907-01-iconic-mountains-k2-restricted.jpg"))
        allUrgent?.add(Urgent(4, "Mountains", "description", "18", "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_900,c_fill,g_auto,h_506,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F170407220907-01-iconic-mountains-k2-restricted.jpg"))
        allUrgent?.add(Urgent(5, "Mountains", "description", "18", "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_900,c_fill,g_auto,h_506,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F170407220907-01-iconic-mountains-k2-restricted.jpg"))
        allUrgent?.add(Urgent(6, "Mountains", "description", "18", "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_900,c_fill,g_auto,h_506,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F170407220907-01-iconic-mountains-k2-restricted.jpg"))

        urgAdapter?.setUrgData(allUrgent!!)


    }

    private fun initAnn() {

        announAdapterS = AnnounAdapterNoPaginated(ArrayList(), this)
        // announAdapter = AnnounAdapter(ArrayList(), this)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        announ_recyclerview.layoutManager = staggeredGridLayoutManager
        announ_recyclerview.isNestedScrollingEnabled = false
        announ_recyclerview.itemAnimator = DefaultItemAnimator()
        announ_recyclerview.adapter = announAdapterS
        /*addScrollAdapter(staggeredGridLayoutManager)

        loadFirstPage()*/
    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    //Menu Toolbar Items
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)


        /* val spinner = menu!!.findItem(R.id.spinner).actionView as Spinner
         val adapter = ArrayAdapter.createFromResource(this,
                 R.array.main_menu_spinner,
                 android.R.layout.simple_spinner_item)
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
         spinner.adapter = adapter
         spinner.setSelection(Settings.getSpinnerItemPosition(this))
         spinner.onItemSelectedListener = Const.Companion.SpinnerActivity(this, this@MainActivity)*/


        val search = menu?.findItem(R.id.search)
        val searchVIew = menu?.findItem(R.id.search)?.actionView as SearchView

        searchVIew.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search?.collapseActionView()
                hideKeyboard(this@MainActivity)
                startActivity(Intent(this@MainActivity, SearchResultsActivity::class.java))
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

    /*private fun addScrollAdapter(staggeredGridLayoutManager: StaggeredGridLayoutManager) {
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
        announAdapter?.clearList()
        if (Settings.getSpinnerItemPosition(this) == 1) {
            mPresenter?.getAnnounFirst(limitPage, 0)
        } else {
            mPresenter?.getAnnounFirstIsNeededFalses(limitPage, 0)
        }
    }


    private fun loadNextPage() {
        if (Settings.getSpinnerItemPosition(this) == 1) {
            mPresenter?.getAnnounNext(limitPage, currentPage * limitPage)
        } else {
            mPresenter?.getAnnounNextIsNeededFalses(limitPage, currentPage * limitPage)
        }
    }*/

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

    fun initClick() {
        addCardView.setOnClickListener(this)
        all.setOnClickListener(this)
    }/*

    override fun onAnnounFirstSuccess(data: AnnouncementsPaginated) {
        val model: List<Announcements> = fetchResults(data)
        pro_bar.visibility = View.GONE
        val id = Settings.getCategoryId(this)
        val filteredList = model.filter { it.category == id }
        announAdapter?.addAll(filteredList)

        if (data.next != null) {
            announAdapter?.addLoadingFooter()
        } else
            issLastPage = true
    }

    override fun onAnnounNextSuccess(data: AnnouncementsPaginated) {
        announAdapter?.removeLoadingFooter()
        issLoading = false
        pro_bar.visibility = View.GONE

        val model: List<Announcements> = fetchResults(data)
        val id = Settings.getCategoryId(this)
        val filteredList = model.filter { it.category == id }
        announAdapter?.addAll(filteredList)
        if (data.next == null) {
            issLastPage = true
        } else {
            announAdapter?.addLoadingFooter()
        }
    }

    private fun fetchResults(response: AnnouncementsPaginated): List<Announcements> {    //3
        return response.results
    }

    private fun initSearch() {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            // doMySearch(query)
        }


    }
*/

    private fun initCardBut() {

        scroll.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            if (scrollY >= oldScrollY && visibleCard) {
                hideFabWithObjectAnimator()
                addCardView.isClickable = false
                //addCardView.visibility = View.GONE
                visibleCard = !visibleCard
            } else if (!visibleCard && scrollY <= oldScrollY) {
                visibleCard = !visibleCard
                showFabWithObjectAnimator()
                addCardView.isClickable = true
            }
        }
    }


    fun hideFabWithObjectAnimator() {
        val scaleSet = AnimatorSet()

        val xScaleAnimator = ObjectAnimator.ofFloat(addCardView, View.SCALE_X, 0f)
        val yScaleAnimator = ObjectAnimator.ofFloat(addCardView, View.SCALE_Y, 0f)

        scaleSet.duration = 200
        scaleSet.interpolator = LinearInterpolator()

        scaleSet.playTogether(xScaleAnimator, yScaleAnimator)
        scaleSet.start()
    }


    fun showFabWithObjectAnimator() {
        val scaleSet = AnimatorSet()

        val xScaleAnimator = ObjectAnimator.ofFloat(addCardView, View.SCALE_X, 1f)
        val yScaleAnimator = ObjectAnimator.ofFloat(addCardView, View.SCALE_Y, 1f)

        scaleSet.duration = 400
        scaleSet.interpolator = OvershootInterpolator()

        scaleSet.playTogether(xScaleAnimator, yScaleAnimator)
        scaleSet.start()
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d("thiss", query)
        return true
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d("thatt", newText)
        return true
    }

    private fun initCa() {
        caAdapter = CategoryAdapter(ArrayList(), this)
        recview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recview.setHasFixedSize(true)
        recview.itemAnimator = DefaultItemAnimator()
        recview.isNestedScrollingEnabled = false
        recview.adapter = caAdapter
    }

    private fun initUrg() {
        urgAdapter = UrgentAdapter(ArrayList(), this)
        urg_recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        urg_recyclerview.isNestedScrollingEnabled = false
        urg_recyclerview.adapter = urgAdapter
    }


    override fun onAnClick(data: Announcements) {
        val intent = Intent(this, DetailedProductActivity::class.java)
        intent.putExtra(GOODS, data)
        startActivity(intent)
    }

    override fun onUrgClick(main: Urgent) {
        val intent = Intent(this, UrgentDetailed::class.java)
        intent.putExtra(ACTION_URGENT, main)
        startActivity(intent)
    }

    override fun onCaClick(data: AllCategory, position: Int) {
        Settings.setCategoryId(this, data.id)
        val intent = Intent(this, AnnounByCategoryActivity::class.java)
        intent.putExtra(CATEGORY_ID, position)
        intent.putExtra(CATEGORY_NAME, data.category_name)
        startActivity(intent)
        /* caAdapter?.setSelectedCard()
         currentPage = 0
         announAdapter?.clearList()
         loadFirstPage()*/


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
    }

    override fun attachBaseContext(newBase: Context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            super.attachBaseContext(MyContextWrapper.wrap(newBase, "ky"))
        } else {
            super.attachBaseContext(newBase)
        }
    }


}




