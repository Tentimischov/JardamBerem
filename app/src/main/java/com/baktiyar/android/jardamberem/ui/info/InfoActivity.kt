package com.baktiyar.android.jardamberem.ui.info

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Info
import com.baktiyar.android.jardamberem.model.InfoPaginated
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.action.PaginationScrollListenerAction
import com.baktiyar.android.jardamberem.ui.action_det.ActionDetailed
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTION_DETAILED
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTIVITY_ID
import com.baktiyar.android.jardamberem.utils.Const.Companion.INFO_ACTIVITY
import com.baktiyar.android.jardamberem.utils.Const.Companion.INFO_DETAILED
import kotlinx.android.synthetic.main.activity_action.*

class InfoActivity : BaseActivity(), InfoContract.View, InfoAdapterNoPag.OnItemClickListener {
    override fun onInfoClick(info: Info) {
        val intent = Intent(this, ActionDetailed::class.java)
        intent.putExtra(ACTIVITY_ID, INFO_ACTIVITY)
        intent.putExtra(INFO_DETAILED, info)
        startActivity(intent)
    }

    override fun onSuccessFirst(data: ArrayList<Info>) {
        adapter?.setInfo(data)
    }


    var presenter: InfoPresenter? = null
    var adapter: InfoAdapterNoPag? = null
    private val TOTAL_PAGES: Int = 10
    private var issLoading = false
    private var issLastPage = false
    private var limitPage = 10
    private val PAGE_START = 1
    private var currentPage = PAGE_START


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action)
        title = getString(R.string.helpful_info)

        init()


    }

    fun init() {
        presenter = InfoPresenter(this)
        adapter = InfoAdapterNoPag(ArrayList(), this)
        val layoutManager = LinearLayoutManager(this)
        action_recycler.layoutManager = layoutManager
        action_recycler.adapter = adapter
        presenter?.getInfoFirst(100, 0)
       // addScrollAdapter(layoutManager)
      //  loadFirstPage()
    }

    /*private fun addScrollAdapter(layoutManager: LinearLayoutManager) {
        action_recycler.addOnScrollListener(object : PaginationScrollListenerAction(layoutManager) {
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
        presenter?.getInfoFirst(limitPage, 0)
    }

    private fun loadNextPage() {
        presenter?.getInfoNext(limitPage, currentPage * limitPage)
    }

    override fun onSuccessFirst(data: InfoPaginated) {
        val model: List<Info> = fetchResults(data)
        pro_bar.visibility = View.GONE
        adapter?.addAll(model)

        if (data?.next != null) {
            adapter?.addLoadingFooter()
        } else
            issLastPage = true
    }

    override fun onSuccessNext(data: InfoPaginated) {
        adapter?.removeLoadingFooter()
        issLoading = false
        pro_bar.visibility = View.GONE

        val model: List<Info> = fetchResults(data)
        adapter?.addAll(model)
        if (data?.next == null) {
            issLastPage = true
        } else {
            adapter?.addLoadingFooter()
        }
    }

    private fun fetchResults(response: InfoPaginated?): List<Info> {    //3
        return response?.results!!
    }

    /*override fun onAnnounClick(main: Info, position: Int) {

    }*/
*/
}