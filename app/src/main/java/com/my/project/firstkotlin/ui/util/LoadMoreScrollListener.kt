package com.my.project.firstkotlin.ui.util

import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LoadMoreScrollListener (private val layoutManager: RecyclerView.LayoutManager, private val loadListener: LoadListener) : RecyclerView.OnScrollListener() {

    private var isScrolling = false
    private var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = layoutManager as LinearLayoutManager
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount

        val isLastPosition = firstVisibleItemPosition + visibleItemCount >= totalItemCount
        val isNotAtBeginning = firstVisibleItemPosition >= 0

        val shouldPaginate = isLastPosition && isNotAtBeginning && isScrolling && !isLoading

        if (shouldPaginate){
            loadListener.onLoad()
            isScrolling = false
            isLoading = true
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
            isScrolling = true
    }

    fun setLoaded() {
        isLoading = false
    }

    interface LoadListener {
        fun onLoad ()
    }

}