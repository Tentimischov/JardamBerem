package com.baktiyar.android.jardamberem.ui.forum

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Forum
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.feedback.FeedbackActivity
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTIVITY_ID
import kotlinx.android.synthetic.main.activity_forum.*
import org.jetbrains.anko.toast


class ForumActivity : BaseActivity(), ForumContract.View {
    private var adapter: ForumAdapterNoPag? = null

    private var presenter: ForumPresenter? = null

    private var visibleCard: Boolean = true
    private val TOTAL_PAGES: Int = 10
    private var issLoading = false
    private var issLastPage = false
    private var limitPage = 10
    private val PAGE_START = 1
    private var currentPage = PAGE_START
    private var btmSheetBehavior: BottomSheetBehavior<FrameLayout>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)
        title = getString(R.string.forum)

        init()

        addCardView.setOnClickListener {
            val intent = Intent(this, FeedbackActivity::class.java)
            intent.putExtra(ACTIVITY_ID, 2)
            startActivity(intent)
        }

    }

    fun init() {
        initCardBut()
        presenter = ForumPresenter(this)
        initAdapter()
        presenter?.getForumFirst(100, 0)
    }


    override fun onSendSuccess() {
        toast(getString(R.string.sent))

    }


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

    override fun onForumFirstSuccess(data: ArrayList<Forum>) {
        adapter?.setForumData(data)
        pro_bar.visibility = View.GONE
    }

    override fun onError(message: String) {
        pro_bar.visibility = View.GONE
    }


    fun initAdapter() {
        adapter = ForumAdapterNoPag(ArrayList())
        val layoutManager = LinearLayoutManager(this)
        forum_rec.layoutManager = layoutManager
        forum_rec.adapter = adapter
        forum_rec.isNestedScrollingEnabled = false
        //addScrollAdapter(layoutManager)
        //loadFirstPage()
    }
/*

    override fun onForumFirstSuccess(data: ForumPaginated) {
        val model: List<Forum> = fetchResults(data)
        pro_bar.visibility = View.GONE
        adapter?.addAll(model)

        if (data?.next != null) {
            adapter?.addLoadingFooter()
        } else
            issLastPage = true
    }

   */
/* override fun onForumNextSuccess(data: ForumPaginated) {
        adapter?.removeLoadingFooter()
        issLoading = false
        pro_bar.visibility = View.GONE

        val model: List<Forum> = fetchResults(data)
        adapter?.addAll(model)
        if (data?.next == null) {
            issLastPage = true
        } else {
            adapter?.addLoadingFooter()
        }
    }
*//*

    private fun addScrollAdapter(layoutManager: LinearLayoutManager) {
        forum_rec.addOnScrollListener(object : PaginationScrollListenerAction(layoutManager) {
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
        presenter?.getForumFirst(limitPage, 0)
    }

    private fun loadNextPage() {
        presenter?.getForumNext(limitPage, currentPage * limitPage)
    }

    override fun onSendSuccess() {
        toast(getString(R.string.success))
    }


    private fun fetchResults(response: ForumPaginated?): List<Forum> {    //3
        return response?.results!!
    }
*/

}
