package com.baktiyar.android.jardamberem.ui.action

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.ActionData
import com.baktiyar.android.jardamberem.model.ActionDataPaginated
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.action_det.ActionDetailed
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTION_DETAILED
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTIVITY_ID
import kotlinx.android.synthetic.main.activity_action.*

@Suppress("UNUSED_PARAMETER")
class ActionActivity : BaseActivity(), ActionAdapter.OnItemClickListener, ActionContract.View {

    var adapter: ActionAdapter? = null
    private val TOTAL_PAGES: Int = 10
    private var issLoading = false
    private var issLastPage = false
    private var limitPage = 10
    private val PAGE_START = 1
    private var currentPage = PAGE_START
    private var presenter: ActionPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action)
        title = getString(R.string.action)

        init()
    }


    fun init() {
        presenter = ActionPresenter(this)
        adapter = ActionAdapter(ArrayList(), this)
        val layoutManager = LinearLayoutManager(this)
        action_recycler.layoutManager = layoutManager
        action_recycler.adapter = adapter
        //presenter?.getActionDataFirst(100, 0)
        addScrollAdapter(layoutManager)
        loadFirstPage()
    }

    private fun addScrollAdapter(layoutManager: LinearLayoutManager) {
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
        presenter?.getActionData(limitPage, currentPage * limitPage)
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
    }

    override fun onActionCLick(actionData: ActionData, position: Int) {
        val intent = Intent(this, ActionDetailed::class.java)
        intent.putExtra(ACTIVITY_ID, ACTION_DETAILED)
        intent.putExtra(ACTION_DETAILED, actionData)
        startActivity(intent)
    }

    override fun onError(message: String) {
        pro_bar.visibility = View.GONE
        super.onError(message)
    }

}
