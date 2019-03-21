package com.baktiyar.android.jardamberem.ui.announcements_list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated
import com.baktiyar.android.jardamberem.ui.main.fragment.MainAnnouncementAdapter
import com.baktiyar.android.jardamberem.ui.product.detailed_product.DetailedProductActivity
import com.baktiyar.android.jardamberem.utils.Const
import com.baktiyar.android.jardamberem.utils.toToast
import kotlinx.android.synthetic.main.fragment_main_announcement.*
import kotlinx.android.synthetic.main.fragment_main_announcement.view.*

class AnnouncementByCategoryFragment : Fragment(), AnnouncementByCategoryContract.View, MainAnnouncementAdapter.OnItemClickListener {
    private var data: ArrayList<Announcements>? = null
    private lateinit var presenter: AnnouncementByCategoryPresenter
    private lateinit var adapter: MainAnnouncementAdapter
    private var isNeeded: Boolean = false
    private val limit: Int = 10
    private var offset: Int = 0
    private var hasNextPage: Boolean = true
    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        data = arrayListOf()
        isNeeded = arguments?.getBoolean(IS_NEEDED)!!
        presenter = AnnouncementByCategoryPresenter(this)
        adapter = MainAnnouncementAdapter(arrayListOf(), this)
        presenter.getAnnouncementByCategory(limit, offset, isNeeded)
        offset += limit

        return inflater.inflate(R.layout.fragment_main_announcement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mView = view

        mView.pro_bar.visibility = View.VISIBLE
        mView.rec_view.visibility = View.GONE

        view.rec_view.isNestedScrollingEnabled = false
        view.rec_view.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        view.rec_view.adapter = adapter
        view.rec_view.setHasFixedSize(true)


        view.rec_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (data != null && view.rec_view.layoutManager.itemCount <= data!!.size && hasNextPage) {
                    presenter.getAnnouncementByCategory(limit, offset, isNeeded)
                    offset += limit
                }
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (hasNextPage) {
                    presenter.getAnnouncementByCategory(limit, offset, isNeeded)
                    offset += limit
                }
                super.onScrolled(recyclerView, dx, dy)
            }

        })

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        val IS_NEEDED: String = "IS_NEEDED"
        fun newInstance(isNeeded: Boolean): AnnouncementByCategoryFragment {
            val fragment = AnnouncementByCategoryFragment()
            val args = Bundle()
            args.putBoolean(IS_NEEDED, isNeeded)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAnnouncementItemClick(data: Announcements) {
        val intent = Intent(context, DetailedProductActivity::class.java)
        intent.putExtra(Const.GOODS, data)
        startActivity(intent)
    }

    override fun onCallClick(number: String) {
        phoneIntent(number)
    }

    private fun phoneIntent(number: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null))
        startActivity(intent)
    }

    override fun onAnnouncementSuccess(data: AnnouncementsPaginated) {
        setData(data)
    }

    private fun setData(data: AnnouncementsPaginated) {
        mView.pro_bar.visibility = View.GONE
        mView.rec_view.visibility = View.VISIBLE

        this.data?.addAll(data.results)
        adapter.addAnnouncementData(data.results)
        adapter.notifyDataSetChanged()
        if (data.next == null) hasNextPage = false
    }

    override fun onError(message: String) {
        pro_bar.visibility = View.GONE
        message.toToast()
    }
}