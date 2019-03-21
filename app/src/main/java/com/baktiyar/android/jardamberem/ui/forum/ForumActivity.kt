package com.baktiyar.android.jardamberem.ui.forum

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Forum
import com.baktiyar.android.jardamberem.model.ForumPaginated
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.feedback.FeedbackActivity
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTIVITY_ID
import kotlinx.android.synthetic.main.activity_forum.*
import org.jetbrains.anko.toast


class ForumActivity : BaseActivity(), ForumContract.View, ForumAdapter.OnForumClickListener {

    private var adapter: ForumAdapter? = null
    private var presenter: ForumPresenter? = null
    private var limitPage = 15
    private var offset: Int = 0
    private var hasNextPage: Boolean = true
    private var data: ArrayList<Forum>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)
        title = getString(R.string.forum)

        init()

        addForum.setOnClickListener {
            val intent = Intent(this, FeedbackActivity::class.java)
            intent.putExtra(ACTIVITY_ID, 2)
            startActivity(intent)
        }
    }

    fun init() {
        presenter = ForumPresenter(this)
        initAdapter()
    }

    fun initAdapter() {
        adapter = ForumAdapter(ArrayList(), this)
        forum_rec.adapter = adapter
        forum_rec.isNestedScrollingEnabled = false
        presenter?.getForum(limitPage, offset)
        offset += limitPage

        forum_rec.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (data != null && forum_rec.layoutManager.itemCount <= data!!.size && hasNextPage) {
                    presenter?.getForum(limitPage, offset)
                    offset += limitPage
                }
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (hasNextPage) {
                    presenter?.getForum(limitPage, offset)
                    offset += limitPage
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

    }

    override fun onError(message: String) {
        toast(message)
        pro_bar.visibility = View.GONE
    }

    override fun onSendSuccess() {
        toast(getString(R.string.sent))

    }

    override fun onDeleteSuccess(message: String, position: Int) {
        adapter?.deleteForum(position)
    }

    override fun onDeleteError(message: String) {
        toast(getString(R.string.fail))
    }

    override fun onForumDelete(id: Int, position: Int) {
        presenter?.deleteForum(id, position)
    }

    override fun onForumSuccess(data: ForumPaginated) {
        forum_rec.visibility = View.VISIBLE

        this.data?.addAll(data.results)
        adapter?.addForumData(data.results)
        if (data.next == null) {
            hasNextPage = false
            pro_bar.visibility = View.GONE
        }
    }

    override fun onForumError(message: String) {

    }

}
