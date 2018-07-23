package com.baktiyar.android.jardamberem.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

abstract class PaginationScrollListener(internal var layoutManager: StaggeredGridLayoutManager)
    : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        var firstVisibleItemPosition:IntArray? = null
        firstVisibleItemPosition = layoutManager.findFirstVisibleItemPositions(firstVisibleItemPosition)

        if (!isLoading && !isLastPage) {
            if (visibleItemCount + firstVisibleItemPosition[0] >= totalItemCount && firstVisibleItemPosition.size >= 0) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()

    abstract val totalPageCount: Int

    abstract var isLastPage: Boolean

    abstract var isLoading: Boolean
}
