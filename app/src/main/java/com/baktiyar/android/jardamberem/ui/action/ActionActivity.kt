package com.baktiyar.android.jardamberem.ui.action

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.baktiyar.android.jardamberem.MyContextWrapper
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.ActionData
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.action_det.ActionDetailed
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTION_DETAILED
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTIVITY_ID
import com.baktiyar.android.jardamberem.utils.Settings
import kotlinx.android.synthetic.main.activity_action.*

@Suppress("UNUSED_PARAMETER")
class ActionActivity : BaseActivity(), ActionAdapterNoPag.OnItemClickListener, ActionContract.View {
    var adapter: ActionAdapter? = null


    private val TOTAL_PAGES: Int = 10

    private var issLoading = false
    private var issLastPage = false
    private var limitPage = 10
    private val PAGE_START = 1
    private var currentPage = PAGE_START
    private var actionAdapter: ActionAdapterNoPag? = null
    private var presenter: ActionPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action)
        title = getString(R.string.action)

        init()
    }



    override fun onItemClickListener(actionData: ActionData) {
        val intent = Intent(this, ActionDetailed::class.java)
        intent.putExtra(ACTIVITY_ID, ACTION_DETAILED)
        intent.putExtra(ACTION_DETAILED, actionData)
        startActivity(intent)
    }

    fun init() {
        presenter = ActionPresenter(this)
        actionAdapter = ActionAdapterNoPag(ArrayList(), this)
        val layoutManager = LinearLayoutManager(this)
        action_recycler.layoutManager = layoutManager
        action_recycler.adapter = actionAdapter
        presenter?.getActionDataFirst(100, 0)
       // addScrollAdapter(layoutManager)
       // loadFirstPage()
    }
    override fun onSuccessFirst(data: ArrayList<ActionData>?) {
        actionAdapter?.setActionData(data!!)
    }

  /*  private fun addScrollAdapter(layoutManager: LinearLayoutManager) {
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
        presenter?.getActionDataFirst(limitPage, 0)
    }

    private fun loadNextPage() {
        presenter?.getActionDataFirst(limitPage, currentPage * limitPage)
    }


    override fun onSuccessFirst(data: ActionDataPaginated?) {
        val model: List<ActionData> = fetchResults(data)
        pro_bar.visibility = View.GONE
        adapter?.addAll(model)

        if (data?.next != null) {
            adapter?.addLoadingFooter()
        } else
            issLastPage = true
    }

    override fun onSuccessNext(data: ActionDataPaginated?) {
        adapter?.removeLoadingFooter()
        issLoading = false
        pro_bar.visibility = View.GONE

        val model: List<ActionData> = fetchResults(data)
        adapter?.addAll(model)
        if (data?.next == null) {
            issLastPage = true
        } else {
            adapter?.addLoadingFooter()
        }
    }

    private fun fetchResults(response: ActionDataPaginated?): List<ActionData> {    //3
        return response?.results!!
    }*/

   /* override fun onAnnounClick(main: ActionData, position: Int) {
        startActivity(Intent(this, ActionDetailed::class.java))

    }*/

}
