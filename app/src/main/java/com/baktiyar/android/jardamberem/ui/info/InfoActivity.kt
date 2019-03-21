package com.baktiyar.android.jardamberem.ui.info

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Info
import com.baktiyar.android.jardamberem.model.InfoPaginated
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.info_d.DetailedInfoActivity
import com.baktiyar.android.jardamberem.utils.Const.Companion.INFO_DETAILED
import kotlinx.android.synthetic.main.activity_action.*

class InfoActivity : BaseActivity(), InfoContract.View, InfoAdapter.OnItemClickListener {

    var presenter: InfoPresenter? = null
    var adapter: InfoAdapter? = null
    private var limitPage = 10
    private var offset: Int = 0
    private var hasNextPage: Boolean = true
    private var data: ArrayList<Info>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action)
        title = getString(R.string.helpful_info)

        init()
    }

    fun init() {
        presenter = InfoPresenter(this)
        adapter = InfoAdapter(ArrayList(), this)
        presenter?.getInfo(limitPage, offset)
        offset += limitPage

        action_recycler.adapter = adapter

        action_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (data != null && action_recycler.layoutManager.itemCount <= data!!.size && hasNextPage) {
                    presenter?.getInfo(limitPage, offset)
                    offset += limitPage
                }
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (hasNextPage) {
                    presenter?.getInfo(limitPage, offset)
                    offset += limitPage
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

    }


    override fun onError(message: String) {
        pro_bar.visibility = View.GONE
        super.onError(message)
    }

    override fun onSuccessInfo(data: InfoPaginated) {
        pro_bar.visibility = View.GONE
        action_recycler.visibility = View.VISIBLE

        this.data?.addAll(data.results)
        adapter?.addInfoData(data.results)
        if (data.next == null) {
            hasNextPage = false
        }
    }

    override fun onInfoClick(info: Info) {
        val intent = Intent(this, DetailedInfoActivity::class.java)
        intent.putExtra(INFO_DETAILED, info)
        startActivity(intent)
    }

}