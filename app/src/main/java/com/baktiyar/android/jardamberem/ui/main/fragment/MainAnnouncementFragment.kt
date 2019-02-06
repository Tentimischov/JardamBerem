package com.baktiyar.android.jardamberem.ui.main.fragment


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
import com.baktiyar.android.jardamberem.ui.product.detailed_product.DetailedProductActivity
import com.baktiyar.android.jardamberem.utils.Const
import kotlinx.android.synthetic.main.fragment_main_announcement.view.*

class MainAnnouncementFragment : Fragment(), MainAnnouncementAdapter.OnItemClickListener, MainAnnouncementContract.View {

    private var data: ArrayList<Announcements>? = null
    private lateinit var presenter: MainAnnouncementPresenter
    private lateinit var adapter: MainAnnouncementAdapter
    private var isGive: Boolean = false
    private val limit: Int = 10
    private var offset: Int = 0
    private var hasNextPage: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        data = arrayListOf()
        isGive = arguments?.getBoolean(POSITION)!!
        presenter = MainAnnouncementPresenter(this)
        adapter = MainAnnouncementAdapter(arrayListOf(), this)

        if (isGive)
            presenter.getAnnouncementIsNeededFalse(limit, offset)
        else
            presenter.getAnnouncementIsNeeded(limit, offset)

        return inflater.inflate(R.layout.fragment_main_announcement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.rec_view.isNestedScrollingEnabled = false
        view.rec_view.adapter = adapter
        view.rec_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (data != null && view.rec_view.layoutManager.itemCount <= data!!.size && hasNextPage) {
                    if (isGive)
                        presenter.getAnnouncementIsNeededFalse(limit, offset)
                    else
                        presenter.getAnnouncementIsNeeded(limit, offset)
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        val POSITION: String = "POSITION"
        fun newInstance(position: Boolean): MainAnnouncementFragment {
            val fragment = MainAnnouncementFragment()
            val args = Bundle()
            args.putBoolean(POSITION, position)
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

    override fun onAnnouncementIsNeededSuccess(data: AnnouncementsPaginated) {
        setData(data)
        if (data.next == null) hasNextPage = false
    }

    private fun setData(data: AnnouncementsPaginated) {
        this.data?.addAll(data.results)
        adapter.addAnnouncementData(data.results)
        if (data.next == null) hasNextPage = false
    }

    override fun onAnnouncementIsNeededFalseSuccess(data: AnnouncementsPaginated) {
        setData(data)
    }

    override fun onAnnouncementError(message: String) {
    }

}